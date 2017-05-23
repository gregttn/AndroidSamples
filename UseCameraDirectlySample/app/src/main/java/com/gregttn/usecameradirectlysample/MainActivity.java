package com.gregttn.usecameradirectlysample;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.io.File;

import static com.gregttn.usecameradirectlysample.SaveImageAsyncTask.*;

public class MainActivity extends AppCompatActivity implements Camera.PictureCallback, SaveImageAsyncTaskDelegate {
    private static final int REQUEST_CAMERA_PERMISSION = 92;
    private static final int REQUEST_STORAGE_PERMISSION = 93;

    private Camera camera;
    private CameraPreview cameraPreview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(hasCamera()) {
            requestPermission(Manifest.permission.CAMERA, REQUEST_CAMERA_PERMISSION);
            requestPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, REQUEST_STORAGE_PERMISSION);
            setupCameraPreview();
        } else {
            Toast.makeText(this, "No camera detected!", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onDestroy() {
        releaseCamera();
        super.onDestroy();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        boolean isGranted = grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED;

        switch (requestCode) {
            case REQUEST_CAMERA_PERMISSION:
                new CameraPermissionCallback().apply(isGranted);
                break;
            case REQUEST_STORAGE_PERMISSION:
                new StoragePermissionCallback().apply(isGranted);
                break;
            default: return;
        }
    }

    private class CameraPermissionCallback {
        void apply(Boolean isGranted) {
            if (isGranted) {
                setupCameraPreview();
            }

            String message = new StringBuilder("Permission ")
                    .append("CAMERA")
                    .append(" was ")
                    .append(isGranted ? "GRANTED!" : "DENIED!")
                    .toString();

            Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
        }
    }

    private class StoragePermissionCallback {
        void apply(Boolean isGranted) {
            triggerTakePhotoButton(isGranted);

            String message = new StringBuilder("Permission ")
                    .append("STORAGE")
                    .append(" was ")
                    .append(isGranted ? "GRANTED!" : "DENIED!")
                    .toString();

            Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onPictureTaken(byte[] data, Camera camera) {
        SaveImageAsyncTask saveImageAsyncTask = new SaveImageAsyncTask(this);
        saveImageAsyncTask.execute(data);

        cameraPreview.restartPreview();
    }

    @Override
    public void onImageSaved(boolean success, File photoFile) {
        if (success) {
            Uri photoUri = FileProvider.getUriForFile(this, "com.gregttn.usecameradirectlysample.fileprovider", photoFile);
            requestPhotoPreview(photoUri);
        } else {
            Toast.makeText(this, "Failed to save photo", Toast.LENGTH_LONG).show();
        }
    }

    private void requestPermission(String permission, int id) {
        int isGranted = ContextCompat.checkSelfPermission(this, permission);

        if (isGranted!= PackageManager.PERMISSION_GRANTED) {
            String[] permissions = {permission};
            ActivityCompat.requestPermissions(this, permissions, id);
        }
    }

    private void triggerTakePhotoButton(boolean enabled) {
        Button takePhotoButton = (Button) findViewById(R.id.take_picture_btn);
        takePhotoButton.setEnabled(enabled);
    }

    private void setupCameraPreview() {
        if(openCamera()) {
            cameraPreview = new CameraPreview(this, camera);

            FrameLayout previewContainer = (FrameLayout) findViewById(R.id.camera_preview);
            previewContainer.addView(cameraPreview);
        }
    }

    private boolean openCamera() {
        try {
            camera = Camera.open();
            return camera != null;
        } catch(Exception e) {
            Log.e(getClass().getSimpleName(), "Couldn't open camera", e);
        }

        return false;
    }

    private void releaseCamera() {
        if (camera != null)  {
            camera.stopPreview();
            camera.release();
            camera = null;
        }
    }

    private boolean hasCamera() {
        return getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA);
    }

    public void onTakePictureClicked(View view) {
        camera.takePicture(null, null, this);
    }

    private void requestPhotoPreview(Uri photoUri) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(photoUri, "image/jpeg");
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        startActivity(intent);
    }
}
