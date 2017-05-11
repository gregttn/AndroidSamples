package com.gregttn.takephotossample;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_PHOTO_FLAG && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap image = (Bitmap) extras.get("data");

            ImageView imageView = (ImageView) findViewById(R.id.preview_image_view);
            imageView.setImageBitmap(image);
        }
    }
}
