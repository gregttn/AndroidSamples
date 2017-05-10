package com.gregttn.takephotossample;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final Integer REQUEST_PHOTO_FLAG = 345;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        boolean hasCamera = getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA);

        if (!hasCamera) {
            Button takePhotoButton = (Button) findViewById(R.id.take_photo_button);
            takePhotoButton.setEnabled(false);

            Toast.makeText(this, "CAMERA is not available on this device!", Toast.LENGTH_LONG).show();
        }
    }

    public void onTakePictureClicked(View view) {
        Intent requestPhoto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        boolean canTakePicture = requestPhoto.resolveActivity(getPackageManager()) != null;

        if (canTakePicture) {
            startActivityForResult(requestPhoto, REQUEST_PHOTO_FLAG);
        }
    }
}
