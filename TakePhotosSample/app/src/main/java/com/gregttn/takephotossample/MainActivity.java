package com.gregttn.takephotossample;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    private static final Integer REQUEST_PHOTO_FLAG = 345;

    private Uri photoUri;

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

            try {
                File photoFile = createImageFile();

                if(photoFile != null) {
                    photoUri = FileProvider.getUriForFile(this, "com.gregttn.takephotossample.fileprovider", photoFile);

                    requestPhoto.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                }
            } catch (IOException e) {
                Log.w(getClass().getName(), "Cannot create file to save the photo", e);
                Toast.makeText(this, "Photo file could not be created!", Toast.LENGTH_LONG).show();
            }

            startActivityForResult(requestPhoto, REQUEST_PHOTO_FLAG);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_PHOTO_FLAG && resultCode == RESULT_OK) {
            ImageView imageView = (ImageView) findViewById(R.id.preview_image_view);

            if(data == null) {
                imageView.setImageURI(photoUri);
            } else {
                Bundle extras = data.getExtras();
                Bitmap image = (Bitmap) extras.get("data");

                imageView.setImageBitmap(image);
            }
        }
    }

    private File createImageFile() throws IOException {
        String uuid = UUID.randomUUID().toString();
        String filename = "jpeg_" + uuid;

        return File.createTempFile(
                filename,
                ".jpg",
                getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        );
    }
}
