package com.gregttn.usecameradirectlysample;

import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Camera camera;
    private CameraPreview cameraPreview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(hasCamera()) {
            if(openCamera()) {
                cameraPreview = new CameraPreview(this, camera);

                FrameLayout previewContainer = (FrameLayout) findViewById(R.id.camera_preview);
                previewContainer.addView(cameraPreview);
            }
        } else {
            Toast.makeText(this, "No camera detected!", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onDestroy() {
        releaseCamera();
        super.onDestroy();
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
}
