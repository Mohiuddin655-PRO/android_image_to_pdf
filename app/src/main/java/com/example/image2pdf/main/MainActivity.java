package com.example.image2pdf.main;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.image2pdf.R;
import com.example.image2pdf.databinding.ActivityMainBinding;
import com.example.image2pdf.editor.EditPhotoActivity;
import com.example.image2pdf.utils.Utils;
import com.yalantis.ucrop.UCrop;

public class MainActivity extends AppCompatActivity implements OnItemResponseListener {

    private static final int RC_CHOOSE_PHOTO = 1001;
    private static final int RC_CROP_PHOTO = 1002;
    private static final int RC_EDIT_PHOTO = 1003;
    private static final int RC_CAPTURE_PHOTO = 1004;

    private ActivityMainBinding mBinding;
    private ImageChoosingAdapter mAdapter;
    private Uri mOldUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityMainBinding.inflate(getLayoutInflater());
        this.setContentView(mBinding.getRoot());
        init();
        listener();
    }

    private void init() {
        mAdapter = new ImageChoosingAdapter(this);
        mBinding.rvPhotos.setAdapter(mAdapter);
    }

    private void listener() {
        mBinding.actionChoose.setOnClickListener(view ->
                Utils.chooseMultiplePhoto(this, RC_CHOOSE_PHOTO));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && resultCode == RESULT_OK) {
            if (requestCode == RC_CHOOSE_PHOTO) {
                choseData(data);
            } else if (requestCode == RC_CROP_PHOTO) {
                croppedData(data);
            } else if (requestCode == RC_EDIT_PHOTO) {
                editedData(data);
            } else if (requestCode == RC_CAPTURE_PHOTO) {
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                Uri uri = Utils.getImageUri(this, photo);
                addPhoto(uri);
            }
        }
    }

    private void choseData(@NonNull Intent data) {
        ClipData clipData = data.getClipData();
        if (data.getData() != null) {
            addPhoto(data.getData());
        } else if (clipData != null) {
            for (int i = 0; i < clipData.getItemCount(); i++) {
                Uri uri = clipData.getItemAt(i).getUri();
                if (uri != null) {
                    addPhoto(uri);
                }
            }
        }
    }

    private void croppedData(@NonNull Intent data) {
        Uri uri = UCrop.getOutput(data);
        if (uri != null) {
            mAdapter.updateAt(mOldUri, uri);
        }
    }

    private void editedData(@NonNull Intent data) {
        mAdapter.updateAt(mOldUri, data.getData());
    }

    private void addPhoto(@NonNull Uri uri) {
        mAdapter.insertAt(uri, 0);
        mBinding.rvPhotos.smoothScrollToPosition(0);
    }

    @Override
    public void onItemCropRequest(@NonNull Uri uri, int position) {
        mOldUri = uri;
        Utils.cropImage(this, RC_CROP_PHOTO, uri);
    }

    @Override
    public void onItemEditRequest(@NonNull Uri uri, int position) {
        mOldUri = uri;
        Intent intent = new Intent(this, EditPhotoActivity.class);
        intent.putExtra("data", uri);
        startActivityForResult(intent, RC_EDIT_PHOTO);
    }

    @Override
    public void onItemRemoveRequest(@NonNull Uri uri, int position) {
        mOldUri = uri;
        mAdapter.removeAt(uri);
    }

    @Override
    public void onItemChangeResponse(int itemCount) {
        if (itemCount > 0) {
            mBinding.actionChoose.setVisibility(View.GONE);
            mBinding.rvPhotos.setVisibility(View.VISIBLE);
            mBinding.actionConvert.setVisibility(View.VISIBLE);
        } else {
            mBinding.actionConvert.setVisibility(View.GONE);
            mBinding.rvPhotos.setVisibility(View.GONE);
            mBinding.actionChoose.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_choose_image, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_camera:
                Utils.choosePhoto(this, RC_CAPTURE_PHOTO);
                return true;
            case R.id.menu_gallery:
                Utils.chooseMultiplePhoto(this, RC_CHOOSE_PHOTO);
                return true;
            default:
                return false;
        }
    }

}