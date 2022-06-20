package com.inlocal.restaurantapp.ui.imagepicker.view;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.inlocal.restaurantapp.R;
import com.inlocal.restaurantapp.base.BaseActivity;
import com.inlocal.restaurantapp.databinding.ActivityImageChooserBinding;
import com.inlocal.restaurantapp.ui.addpost.view.AddPostActivity;
import com.inlocal.restaurantapp.ui.imagepicker.model.ImagePickerModel;
import com.inlocal.restaurantapp.ui.uploadstory.view.UploadStoryActivity;
import com.inlocal.restaurantapp.util.NavUtil;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.util.ArrayList;

public class ImageChooserActivity extends BaseActivity<ActivityImageChooserBinding> implements ImagePickerAdapter.ImageSelected {
    ArrayList<String> allFolder = new ArrayList<>();
    private ArrayList<ImagePickerModel> mImages = new ArrayList<>();
    private ImagePickerAdapter mImagePickerAdapter;
    private int selectedPos = 0, mpage = 0;
    private String selectedImage = "";
    private String type = "";

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
                Bundle bundle = new Bundle();
                bundle.putString("path", file.getAbsolutePath());
                bundle.putString("type", type);
                /*if(type.equalsIgnoreCase("edit_story")){
                    Intent intent = new Intent();
                    intent.putExtras(bundle);
                    setResult(RESULT_OK, intent);
                    finish();
                }else {*/
                    NavUtil.ForActivity.navTo(ImageChooserActivity.this, AddPostActivity.class, false, bundle);
                //}


            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {

                Exception error = result.getError();
            }
        }
    }

    @Override
    protected int layoutRes() {
        return R.layout.activity_image_chooser;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent() != null && getIntent().hasExtra("type")) {
            type = getIntent().getStringExtra("type");
            if (type.equalsIgnoreCase("story")) {
                binding.txtTitle.setText("Add Story");
            }else if (type.equalsIgnoreCase("edit_story")) {
                binding.txtTitle.setText("Edit Story");
            } else {
                binding.txtTitle.setText("Add Post");
            }
        }
        allFolder.add("Gallery");
        getImagesFolder();
        binding.recyclerItem.setNestedScrollingEnabled(false);
        mImagePickerAdapter = new ImagePickerAdapter(mImages, this);
        binding.recyclerItem.setAdapter(mImagePickerAdapter);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, allFolder);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinner.setAdapter(dataAdapter);
        binding.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mImages.clear();
                selectedPos = position;
                mpage = 0;
                getImages(selectedPos);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        binding.imgCamera.setOnClickListener(v -> onBackPressed());
        binding.imgClose.setOnClickListener(v -> onBackPressed());
        binding.imgNext.setOnClickListener(v -> {
          //  if (type.equalsIgnoreCase("post")) {

            if (type.equalsIgnoreCase("edit_story")) {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString("path", selectedImage);
                bundle.putString("type", type);
                intent.putExtras(bundle);
                setResult(RESULT_OK, intent);
                finish();
            } else if (type.equalsIgnoreCase("story")) {
                Bundle bundle = new Bundle();
                bundle.putString("path", selectedImage);
                bundle.putString("type", type);
                NavUtil.ForActivity.navTo(ImageChooserActivity.this, UploadStoryActivity.class, false, bundle);
            } else {
                CropImage.activity(Uri.fromFile(new File(selectedImage)))
                        .setAllowFlipping(true)
                        .setAllowRotation(true)
                        .setAllowCounterRotation(true)
                        .setCropShape(CropImageView.CropShape.RECTANGLE)
                        .setAspectRatio(1, 1)
                        .setInitialCropWindowPaddingRatio(0)
                        .start(ImageChooserActivity.this);
            }
            /*} else {
                Bundle bundle = new Bundle();
                bundle.putString("path", selectedImage);
                bundle.putString("type", type);
                NavUtil.ForActivity.navTo(this, UploadStoryActivity.class, false, bundle);
            }*/
        });


        binding.rvNestedScrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                View view = (View) binding.rvNestedScrollView.getChildAt(binding.rvNestedScrollView.getChildCount() - 1);

                int diff = (view.getBottom() - (binding.rvNestedScrollView.getHeight() + binding.rvNestedScrollView
                        .getScrollY()));

                if (diff == 0) {
                    getImages(selectedPos);
                    Log.e("run", "run");
                    // your pagination code
                }
            }
        });
    }

    private void getImages(int pos) {
        //counter = 0;
        mImagePickerAdapter.notifyDataSetChanged();
        if (pos == 0) {
            getAllImagesAndVideos();
            mImagePickerAdapter.notifyDataSetChanged();
            return;
        }
        if (binding.spinner.getSelectedItem() != null) {
            getFilesFromFolder(binding.spinner.getSelectedItem().toString());
        }
    }

    private void getImagesFolder() {
        String[] projection = new String[]{MediaStore.Images.Media.DATA,
                MediaStore.Video.Media.DATA,
                MediaStore.Images.Media._ID,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
                MediaStore.Images.Media.DATE_TAKEN};
        Uri images = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        final String orderBy = MediaStore.Images.Media.DATE_TAKEN;
        Cursor cur = getContentResolver().query(images, projection,
                null,
                null,
                orderBy + " DESC"
        );
        assert cur != null;
        if (cur.moveToFirst()) {
            String bucket, date;
            int bucketColumn = cur.getColumnIndex(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);
            int dateColumn = cur.getColumnIndex(MediaStore.Images.Media.DATE_TAKEN);

            do {
                bucket = cur.getString(bucketColumn);
                date = cur.getString(dateColumn);
                if (!allFolder.contains(bucket)) {
                    if (bucket != null) {
                        if (!bucket.toString().equals("cache")) {
                            allFolder.add(bucket);
                        }
                    }
                }
            } while (cur.moveToNext());
        }
        cur.close();
    }

    private void getFilesFromFolder(String folderName) {
        showLoading();
        String[] columns = {MediaStore.Files.FileColumns._ID,
                MediaStore.Files.FileColumns.DATA,
                MediaStore.Files.FileColumns.DATE_ADDED,
                MediaStore.Files.FileColumns.MEDIA_TYPE,
                MediaStore.Files.FileColumns.MIME_TYPE,
                MediaStore.Files.FileColumns.TITLE,
                MediaStore.Files.FileColumns.SIZE,
                MediaStore.Files.FileColumns.DURATION,
                MediaStore.Files.FileColumns.BUCKET_DISPLAY_NAME,

        };
        String selection = "";
//        if (from.equals("trip")) {
        selection = MediaStore.Files.FileColumns.MEDIA_TYPE + "="
                + MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE;

        /*} else if (from.equals("story")) {
            selection = MediaStore.Files.FileColumns.MEDIA_TYPE + "="
                    + MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO;
        } else {
            selection = MediaStore.Files.FileColumns.MEDIA_TYPE + "="
                    + MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE
                    + " OR "
                    + MediaStore.Files.FileColumns.MEDIA_TYPE + "="
                    + MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO;
        }*/

        Uri images = MediaStore.Files.getContentUri("external");
        Cursor imagecursor = getContentResolver().query(images,
                columns,
                selection,
                null,
                MediaStore.Files.FileColumns.DATE_ADDED + " DESC" // Sort order.
        );
        assert imagecursor != null;
        int image_column_index = imagecursor.getColumnIndex(MediaStore.Files.FileColumns._ID);
        int startPos = 0;
        if (mpage == 0) {
            startPos = mpage;
            mpage = mpage + 10;
        } else if (mpage < imagecursor.getCount()) {
            startPos = mpage;
            mpage = mpage + 10;
        } else {
            startPos = mpage;
            mpage = imagecursor.getCount();
        }
        for (int i = startPos; i < mpage; i++) {
            imagecursor.moveToPosition(i);
            int bucketColumn = imagecursor.getColumnIndex(MediaStore.Files.FileColumns.BUCKET_DISPLAY_NAME);
            int id = imagecursor.getInt(image_column_index);
            int dataColumnIndex = imagecursor.getColumnIndex(MediaStore.Files.FileColumns.DATA);
            int date = imagecursor.getColumnIndex(MediaStore.Files.FileColumns.DATE_ADDED);
            int duration = imagecursor.getColumnIndex(MediaStore.Files.FileColumns.DURATION);
            String nameOfFolder = imagecursor.getString(bucketColumn);
            if (nameOfFolder != null) {
                if (nameOfFolder.equals(folderName)) {
                    String path = imagecursor.getString(dataColumnIndex);
                    long durationoffile = imagecursor.getLong(duration);
                    String dateTaken = imagecursor.getString(date);
                    String extension = MimeTypeMap.getFileExtensionFromUrl(path.replace(" ", "/"));
                    String typeOfFile = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
                    int size = imagecursor.getColumnIndex(MediaStore.Files.FileColumns.SIZE);
                    String sizeOfMedia = imagecursor.getString(size);
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
                        mImages.add(new ImagePickerModel(path, dateTaken, typeOfFile, null, sizeOfMedia, 0, false));
                    } else {
                        mImages.add(new ImagePickerModel(path, dateTaken, typeOfFile, null, sizeOfMedia, durationoffile, false));
                    }
                }
            }
        }
        imagecursor.close();
        mImagePickerAdapter.notifyDataSetChanged();
        hideLoading();
    }

    private void getAllImagesAndVideos() {
        showLoading();
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
                + MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE;
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
        int startPos = 0;
        if (mpage == 0) {
            startPos = mpage;
            mpage = mpage + 10;
        } else if (mpage < imagecursor.getCount()) {
            startPos = mpage;
            mpage = mpage + 10;
        } else {
            startPos = mpage;
            mpage = imagecursor.getCount();
        }
        for (int i = startPos; i < mpage; i++) {
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
                String sizeOfMedia = imagecursor.getString(size);
                String dateTaken = imagecursor.getString(date);
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
                    mImages.add(new ImagePickerModel(path, dateTaken, typeOfFile, null, sizeOfMedia, 0, false));
                    if (selectedImage.equals("")) {
                        selectedImage = path;
                        binding.imgImage.setImageUrl(selectedImage);
                    }
                } else {
                    mImages.add(new ImagePickerModel(path, dateTaken, typeOfFile, null, sizeOfMedia, durationoffile, false));
                }
            }
        }
        imagecursor.close();
        hideLoading();
    }

    @Override
    public void selected(boolean isAdded) {

    }

    @Override
    public void onlyOneSelected(int pos) {
        binding.rvNestedScrollView.scrollTo(0, 0);
        selectedImage = mImagePickerAdapter.mImages.get(pos).getFilePath();
        binding.imgImage.setImageUrl(selectedImage);
    }

    @Override
    public void trimVideo(int pos) {

    }
}