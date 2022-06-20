package com.inlocal.restaurantapp.ui.imagepicker.view;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.hardware.Camera;
import android.os.Build;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.List;

public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {

    private static final String TAG = "CameraPreview";

    private Context mContext;
    private SurfaceHolder mHolder;
    private Camera mCamera;
    private List<Camera.Size> mSupportedPreviewSizes;
    private Camera.Size mPreviewSize;
    private boolean meteringAreaSupported = false;

    public CameraPreview(Context context, Camera camera) {
        super(context);
        mContext = context;
        mCamera = camera;

        // supported preview sizes
        mSupportedPreviewSizes = mCamera.getParameters().getSupportedPreviewSizes();
        for (Camera.Size str : mSupportedPreviewSizes)
            Log.e(TAG, str.width + "/" + str.height);

        // Install a SurfaceHolder.Callback so we get notified when the
        // underlying surface is created and destroyed.
        mHolder = getHolder();
        mHolder.addCallback(this);
        // deprecated setting, but required on Android versions prior to 3.0
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    public void surfaceCreated(SurfaceHolder holder) {
        // empty. surfaceChanged will take care of stuff
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        // empty. Take care of releasing the Camera preview in your activity.
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
        Log.e(TAG, "surfaceChanged => w=" + w + ", h=" + h);
        // If your preview can change or rotate, take care of those events here.
        // Make sure to stop the preview before resizing or reformatting it.
        if (mHolder.getSurface() == null) {
            // preview surface does not exist
            return;
        }

        // stop preview before making changes
        try {
            mCamera.stopPreview();
        } catch (Exception e) {
            // ignore: tried to stop a non-existent preview
        }

        // set preview size and make any resize, rotate or reformatting changes here
        // start preview with new settings
        try {
            Camera.Parameters parameters = mCamera.getParameters();
            /*if (mContext.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)){
                parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
            }*/
            parameters.setPreviewSize(mPreviewSize.width, mPreviewSize.height);
            List<String> focusModes = parameters.getSupportedFocusModes();
            if (focusModes.contains(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE)){
                parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
                parameters.setSceneMode( Camera.Parameters.SCENE_MODE_AUTO );
            }
            mCamera.setParameters(parameters);
            mCamera.setDisplayOrientation(90);
            mCamera.setPreviewDisplay(mHolder);
            mCamera.startPreview();

            if (parameters.getMaxNumMeteringAreas() > 0) {
                this.meteringAreaSupported = true;
            }

        } catch (Exception e) {
            Log.d(TAG, "Error starting camera preview: " + e.getMessage());
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int width = resolveSize(getSuggestedMinimumWidth(), widthMeasureSpec);
        int height = resolveSize(getSuggestedMinimumHeight(), heightMeasureSpec);
        setMeasuredDimension(width, height);

        int rotation = ((Activity) mContext).getWindowManager().getDefaultDisplay().getRotation();
        if ((Surface.ROTATION_0 == rotation || Surface.ROTATION_180 == rotation)) {//portrait
            mPreviewSize = getOptimalPreviewSize(mSupportedPreviewSizes, height, width);
        } else
            mPreviewSize = getOptimalPreviewSize(mSupportedPreviewSizes, width, height);//landscape

        if (mPreviewSize == null) return;
        float ratio;
        if (mPreviewSize.height >= mPreviewSize.width) {
            ratio = (float) mPreviewSize.height / (float) mPreviewSize.width;
        } else ratio = (float) mPreviewSize.width / (float) mPreviewSize.height;


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N && ((Activity) mContext).isInMultiWindowMode()) {
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT ||
                    !(Surface.ROTATION_0 == rotation || Surface.ROTATION_180 == rotation)) {
                setMeasuredDimension(width, (int) (width / ratio));
            } else {
                setMeasuredDimension((int) (height / ratio), height);
            }
        } else {
            if ((Surface.ROTATION_0 == rotation || Surface.ROTATION_180 == rotation)) {
                Log.e("---", "onMeasure: " + height + " - " + width * ratio);
                //2264 - 2400.0 pix c -- yes
                //2240 - 2560.0 samsung -- yes
                //1582 - 1440.0 pix 2 -- no
                //1864 - 2048.0 sam tab -- yes
                //848 - 789.4737 iball -- no
                //1640 - 1600.0 nexus 7 -- no
                //1093 - 1066.6667 lenovo -- no
                //if width * ratio is > height, need to minus toolbar height
                if ((width * ratio) < height)
                    setMeasuredDimension(width, (int) (width * ratio));
                else
                    setMeasuredDimension(width, (int) (width * ratio) - 50);
            } else {
                setMeasuredDimension((int) (height * ratio), height);
            }
        }

        /*final int width = resolveSize(getSuggestedMinimumWidth(), widthMeasureSpec);
        final int height = resolveSize(getSuggestedMinimumHeight(), heightMeasureSpec);
        float ratio=0;
        if (mSupportedPreviewSizes != null) {
            mPreviewSize = getOptimalPreviewSize(mSupportedPreviewSizes, width, height);
        }
        if (mPreviewSize != null) {
            if (mPreviewSize.height >= mPreviewSize.width)
                ratio = (float) mPreviewSize.height / (float) mPreviewSize.width;
            else
                ratio = (float) mPreviewSize.width / (float) mPreviewSize.height;
        }

        float camHeight = (int) (width * ratio);
        float newCamHeight;
        float newHeightRatio;


        if (camHeight < height) {
            newHeightRatio = (float) height / (float) mPreviewSize.height;
            newCamHeight = (newHeightRatio * camHeight);
            Log.e(TAG, camHeight + " " + height + " " + mPreviewSize.height + " " + newHeightRatio + " " + newCamHeight);
            setMeasuredDimension((int) (width * newHeightRatio), (int) newCamHeight);
            Log.e(TAG, mPreviewSize.width + " | " + mPreviewSize.height + " | ratio - " + ratio + " | H_ratio - " + newHeightRatio + " | A_width - " + (width * newHeightRatio) + " | A_height - " + newCamHeight);
        } else {
            newCamHeight = camHeight;
            setMeasuredDimension(width, (int) newCamHeight);
            Log.e(TAG, mPreviewSize.width + " | " + mPreviewSize.height + " | ratio - " + ratio + " | A_width - " + (width) + " | A_height - " + newCamHeight);
        }*/




        /*if (mSupportedPreviewSizes != null) {
            mPreviewSize = getOptimalPreviewSize(mSupportedPreviewSizes, width, height);
        }
        if (mPreviewSize!=null) {
            float ratio;
            if(mPreviewSize.height >= mPreviewSize.width)
                ratio = (float) mPreviewSize.height / (float) mPreviewSize.width;
            else
                ratio = (float) mPreviewSize.width / (float) mPreviewSize.height;
            setMeasuredDimension(width, (int) (width * ratio));
        }*/
    }

    private Camera.Size getOptimalPreviewSize(List<Camera.Size> sizes, int w, int h) {
        final double ASPECT_TOLERANCE = 0.1;
        double targetRatio = (double) h / w;

        if (sizes == null)
            return null;

        Camera.Size optimalSize = null;
        double minDiff = Double.MAX_VALUE;

        int targetHeight = h;

        for (Camera.Size size : sizes) {
            double ratio = (double) size.height / size.width;
            if (Math.abs(ratio - targetRatio) > ASPECT_TOLERANCE)
                continue;

            if (Math.abs(size.height - targetHeight) < minDiff) {
                optimalSize = size;
                minDiff = Math.abs(size.height - targetHeight);
            }
        }

        if (optimalSize == null) {
            minDiff = Double.MAX_VALUE;
            for (Camera.Size size : sizes) {
                if (Math.abs(size.height - targetHeight) < minDiff) {
                    optimalSize = size;
                    minDiff = Math.abs(size.height - targetHeight);
                }
            }
        }

        return optimalSize;
    }

    /**
     * Convert touch position x:y to {@link Camera.Area} position -1000:-1000 to 1000:1000.
     *//*
    private Rect calculateTapArea(float x, float y, float coefficient) {
        int areaSize = Float.valueOf(focusAreaSize * coefficient).intValue();

        int left = clamp((int) x - areaSize / 2, 0, getSurfaceView().getWidth() - areaSize);
        int top = clamp((int) y - areaSize / 2, 0, getSurfaceView().getHeight() - areaSize);

        RectF rectF = new RectF(left, top, left + areaSize, top + areaSize);
        matrix.mapRect(rectF);

        return new Rect(Math.round(rectF.left), Math.round(rectF.top), Math.round(rectF.right), Math.round(rectF.bottom));
    }

    private int clamp(int x, int min, int max) {
        if (x > max) {
            return max;
        }
        if (x < min) {
            return min;
        }
        return x;
    }*/
}
