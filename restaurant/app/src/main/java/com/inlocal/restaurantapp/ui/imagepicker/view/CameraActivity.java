package com.inlocal.restaurantapp.ui.imagepicker.view;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.hardware.Camera;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import com.inlocal.restaurantapp.R;
import com.inlocal.restaurantapp.base.BaseActivity;
import com.inlocal.restaurantapp.databinding.ActivityCameraBinding;
import com.inlocal.restaurantapp.ui.addpost.view.AddPostActivity;
import com.inlocal.restaurantapp.ui.uploadstory.view.UploadStoryActivity;
import com.inlocal.restaurantapp.util.Constants;
import com.inlocal.restaurantapp.util.NavUtil;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE;
import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO;

public class CameraActivity extends BaseActivity<ActivityCameraBinding> {
    private Camera mCamera;
    private CameraPreview mPreview;
    private String type = "post";
    private boolean isFlashOn = false, isEdit = false;
    private String flashCameraId;
    private CameraManager mCameraManager;
    private File pictureFile;
    private static int currentCameraId = Camera.CameraInfo.CAMERA_FACING_BACK;
    private Camera.PictureCallback mPicture = new Camera.PictureCallback() {

        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            binding.imgClick.setEnabled(false);
            hideLoading();
            pictureFile = getOutputMediaFile(MEDIA_TYPE_IMAGE);
            if (pictureFile == null) {
                return;
            }

            try {
                FileOutputStream fos = new FileOutputStream(pictureFile);
                fos.write(data);
                fos.close();
                Bundle bundle = new Bundle();
                bundle.putString("path", pictureFile.getAbsolutePath());
                bundle.putString("type", type);

                /*CropImage.activity(Uri.fromFile(new File(pictureFile.getAbsolutePath())))
                        .setAllowFlipping(true)
                        .setAllowRotation(true)
                        .setAllowCounterRotation(true)
                        .setCropShape(CropImageView.CropShape.RECTANGLE)
                        .setAspectRatio(1, 1)
                        .setInitialCropWindowPaddingRatio(0)
                        .start(CameraActivity.this);*/

                if (type.equalsIgnoreCase("post")) {
                    CropImage.activity(Uri.fromFile(new File(pictureFile.getAbsolutePath())))
                            .setAllowFlipping(true)
                            .setAllowRotation(true)
                            .setAllowCounterRotation(true)
                            .setCropShape(CropImageView.CropShape.RECTANGLE)
                            .setAspectRatio(1, 1)
                            .setInitialCropWindowPaddingRatio(0)
                            .start(CameraActivity.this);
                }  else if(type.equalsIgnoreCase("edit_story")) {
                    Intent intent = new Intent();
                    intent.putExtras(bundle);
                    setResult(RESULT_OK, intent);
                    finish();
                }else {
                    NavUtil.ForActivity.navTo(CameraActivity.this, UploadStoryActivity.class, false, bundle);
                }
            } catch (FileNotFoundException e) {
                Log.e("TAG", "File not found: " + e.getMessage());
            } catch (IOException e) {
                Log.e("TAG", "Error accessing file: " + e.getMessage());

            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && data != null) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == Activity.RESULT_OK) {
                Uri resultUri = result.getUri();
                Log.e("resultUri", resultUri.getPath());
                File file = new File(resultUri.getPath());
                if (file.exists()) {
                    Log.e("resultUri", true + "");
                } else {
                    Log.e("resultUri", false + "");
                }
                pictureFile = file;
                Bundle bundle = new Bundle();
                bundle.putString("path", pictureFile.getAbsolutePath());
                //if(type.equalsIgnoreCase("post")) {
                if (!isEdit) {
                    bundle.putString("type", type);
                    if (type.equalsIgnoreCase("post")) {
                        NavUtil.ForActivity.navTo(CameraActivity.this, AddPostActivity.class, false, bundle);
                    } else {
                        NavUtil.ForActivity.navTo(CameraActivity.this, UploadStoryActivity.class, false, bundle);
                    }
                } else {
                    Intent intent = new Intent();
                    intent.putExtras(bundle);
                    setResult(RESULT_OK, intent);
                    finish();
                }
                //}

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }else if (requestCode == 1200 && data!=null) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("path", data.getStringExtra("path"));
            Intent intent = new Intent();
            intent.putExtras(bundle);
            setResult(RESULT_OK, intent);
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        binding.imgClick.setEnabled(true);
    }

    @Override
    protected int layoutRes() {
        return R.layout.activity_camera;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mCamera != null) {
            mCamera.release();
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boolean isFlashAvailable = getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FRONT);

        loadCamera(false);

        if (getIntent().hasExtra("type")) {
            type=getIntent().getStringExtra("type");
            if (getIntent().getStringExtra("type").equalsIgnoreCase("edit_post")) {
                isEdit = true;
                binding.cardPost.setEnabled(false);
                binding.cardPost.setClickable(false);
                binding.cardStory.setVisibility(View.GONE);
                binding.cardPost.setVisibility(View.VISIBLE);
            } else if (getIntent().getStringExtra("type").equalsIgnoreCase("edit_story")) {
                isEdit = true;
                binding.cardStory.setEnabled(false);
                binding.cardStory.setClickable(false);
                binding.cardPost.setVisibility(View.GONE);
                binding.cardStory.setVisibility(View.VISIBLE);
            } else {
                isEdit = false;
            }
        } else {
            isEdit = false;
        }

        binding.ivFlash.setVisibility(isFlashAvailable ? View.VISIBLE : View.GONE);
        mCameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        try {
            flashCameraId = mCameraManager.getCameraIdList()[0];
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }

        binding.ivFlash.setOnClickListener(v -> {
            isFlashOn = !isFlashOn;
            binding.ivFlash.setImageResource(isFlashOn ? R.drawable.ic_power_on : R.drawable.ic_flash);
            switchFlashLight(isFlashOn);
        });
        getFirstImage();
        binding.imgClose.setOnClickListener(v -> onBackPressed());
        binding.imgGallery.setOnClickListener(v -> {
           if(!type.equalsIgnoreCase("edit_story")) {
               Bundle bundle = new Bundle();
               bundle.putString("type", type);
               NavUtil.ForActivity.navTo(this, ImageChooserActivity.class, false, bundle);
           }else {
               Bundle bundle = new Bundle();
               bundle.putString("type", type);
               Log.e("type__________", type);
               NavUtil.ForActivity.navToForResult(this, ImageChooserActivity.class, 1200, bundle);
           }
        });
        binding.cardPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = "post";
                binding.txtPost.setTextColor(getResources().getColor(R.color.white));
                binding.txtStory.setTextColor(getResources().getColor(R.color.black));
                binding.cardPost.setCardBackgroundColor(getResources().getColor(R.color.primary));
                binding.cardStory.setCardBackgroundColor(getResources().getColor(R.color.lightGrey));
            }
        });
        binding.cardStory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = "story";
                binding.txtStory.setTextColor(getResources().getColor(R.color.white));
                binding.txtPost.setTextColor(getResources().getColor(R.color.black));
                binding.cardStory.setCardBackgroundColor(getResources().getColor(R.color.primary));
                binding.cardPost.setCardBackgroundColor(getResources().getColor(R.color.lightGrey));
            }
        });

        binding.imgClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadCamera(true);
            }
        });

        binding.cartCaptured.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mCamera.takePicture(null, null, mPicture);
            }
        });
    }

    public static Camera getCameraInstance(boolean changeCamera) {
        Camera c = null;
        try {
            if (changeCamera) {
                if (currentCameraId == Camera.CameraInfo.CAMERA_FACING_BACK) {
                    currentCameraId = Camera.CameraInfo.CAMERA_FACING_FRONT;
                } else {
                    currentCameraId = Camera.CameraInfo.CAMERA_FACING_BACK;
                }
            }
            c = Camera.open(currentCameraId); // attempt to get a Camera instance
            //c = Camera.open(); // attempt to get a Camera instance
        } catch (Exception e) {
            currentCameraId = Camera.CameraInfo.CAMERA_FACING_BACK;
            c = Camera.open(currentCameraId);
            e.printStackTrace();
        }
        return c; // returns null if camera is unavailable
    }

    private void getFirstImage() {
        String[] columns = {MediaStore.Files.FileColumns._ID,
                MediaStore.Files.FileColumns.DATA,
                MediaStore.Files.FileColumns.DATE_ADDED,
                MediaStore.Files.FileColumns.MEDIA_TYPE,
                MediaStore.Files.FileColumns.MIME_TYPE,
                MediaStore.Files.FileColumns.TITLE,
                MediaStore.Files.FileColumns.SIZE,
                MediaStore.Files.FileColumns.DURATION,
                MediaStore.Files.FileColumns.BUCKET_DISPLAY_NAME

        };
        String selection = "";
//        if (from.equals("trip")) {
        selection = MediaStore.Files.FileColumns.MEDIA_TYPE + "="
                + MEDIA_TYPE_IMAGE;
       /* } else if (from.equals("story")) {
            selection = MediaStore.Files.FileColumns.MEDIA_TYPE + "="
                    + MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO;
        } else {
            selection = MediaStore.Files.FileColumns.MEDIA_TYPE + "="
                    + MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE
                    + " OR "
                    + MediaStore.Files.FileColumns.MEDIA_TYPE + "="
                    + MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO;
        }*/

        Uri queryUri = MediaStore.Files.getContentUri("external");
        Cursor imagecursor = getContentResolver().query(queryUri,
                columns,
                selection,
                null,
                MediaStore.Files.FileColumns.DATE_ADDED + " DESC" // Sort order.
        );

        assert imagecursor != null;
        int image_column_index = imagecursor.getColumnIndex(MediaStore.Files.FileColumns._ID);
        for (int i = 0; i < imagecursor.getCount(); i++) {
            imagecursor.moveToPosition(i);
            int id = imagecursor.getInt(image_column_index);
            int dataColumnIndex = imagecursor.getColumnIndex(MediaStore.Files.FileColumns.DATA);
            int date = imagecursor.getColumnIndex(MediaStore.Files.FileColumns.DATE_ADDED);
            int duration = imagecursor.getColumnIndex(MediaStore.Files.FileColumns.DURATION);
            String path = imagecursor.getString(dataColumnIndex);
            long durationoffile = imagecursor.getLong(duration);
            int size = imagecursor.getColumnIndex(MediaStore.Files.FileColumns.SIZE);
            int bucketColumn = imagecursor.getColumnIndex(MediaStore.Files.FileColumns.BUCKET_DISPLAY_NAME);
            String nameOfFolder = imagecursor.getString(bucketColumn);
            if (nameOfFolder != null && !nameOfFolder.equals("cache")) {
                String extension = MimeTypeMap.getFileExtensionFromUrl(path.replace(" ", "/"));
                String typeOfFile = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
                if (typeOfFile != null) {
                    if (typeOfFile.contains("image")) {
                        typeOfFile = "image";
                    } else if (typeOfFile.contains("video")) {
                        typeOfFile = "video";
                    }
                } else {
                    typeOfFile = "image";
                }
                if (typeOfFile.equals("image")) {
                    binding.imgGallery.setImageUrl(path);
                }
                break;
            }
        }
        imagecursor.close();
    }

    /**
     * Create a File for saving an image or video
     */
    private static File getOutputMediaFile(int type) {
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "InLocal");
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.e("InLocal", "failed to create directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "IMG_" + timeStamp + ".jpg");
        } else if (type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "VID_" + timeStamp + ".mp4");
        } else {
            return null;
        }

        return mediaFile;
    }

    private void loadCamera(boolean changeCamera) {
        if (mCamera != null) {
            mCamera.release();
        }
        mCamera = getCameraInstance(changeCamera);
        mCamera.setDisplayOrientation(90);
        Camera.Parameters parameters = mCamera.getParameters();
        if (currentCameraId == Camera.CameraInfo.CAMERA_FACING_BACK) {
            parameters.setRotation(90);
        } else {
            parameters.setRotation(270);
        }
        mCamera.setParameters(parameters);
        mPreview = new CameraPreview(CameraActivity.this, mCamera);
        binding.cameraPreview.addView(mPreview);
    }

    public void switchFlashLight(boolean status) {
        try {
            Camera.Parameters p = mCamera.getParameters();

            if (status) {
                p.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                mCamera.setParameters(p);
            } else {
                p.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                mCamera.setParameters(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}