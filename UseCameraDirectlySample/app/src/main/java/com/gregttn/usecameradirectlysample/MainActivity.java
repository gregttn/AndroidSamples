package com.gregttn.usecameradirectlysample;

import android.Manifest;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_PERMISSION = 92;
    private Camera camera;
    private CameraPreview cameraPreview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(hasCamera()) {
            requestCameraPermission();
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
        if (REQUEST_PERMISSION != requestCode) {
            return;
        }

        boolean isGranted = grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED;

        if (isGranted) {
            setupCameraPreview();
        }

        String message = new StringBuilder("Permission ")
                .append(permissions[0])
                .append(" was ")
                .append(isGranted ? "GRANTED!" : "DENIED!")
                .toString();

        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void requestCameraPermission() {
        int cameraPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);

        if (cameraPermission != PackageManager.PERMISSION_GRANTED) {
            String[] permissions = {Manifest.permission.CAMERA};
            ActivityCompat.requestPermissions(this, permissions, REQUEST_PERMISSION);
        }
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
            camera.release();
            camera = null;
        }
    }

    private boolean hasCamera() {
        return getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA);
    }

    public void onTakePictureClicked(View view) {
    }
}
