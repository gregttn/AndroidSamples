package com.gregttn.usecameradirectlysample;

import android.os.AsyncTask;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

public class SaveImageAsyncTask extends AsyncTask<byte[], Void, Boolean> {
    private final static String JPEG_EXTENSTION = ".jpg";
    private final SaveImageAsyncTaskDelegate delegate;

    private File photoFile = null;

    public SaveImageAsyncTask(SaveImageAsyncTaskDelegate delegate) {
        this.delegate = delegate;
    }

    @Override
    protected Boolean doInBackground(byte[]... params) {
        try {
            File photoDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            photoFile = new File(photoDirectory, createPictureFilename());

            FileOutputStream outputStream = new FileOutputStream(photoFile);
            outputStream.write(params[0]);
            outputStream.close();

            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    protected void onPostExecute(Boolean result) {
        delegate.onImageSaved(result, photoFile);
    }

    private String createPictureFilename() {
        return UUID.randomUUID().toString() + JPEG_EXTENSTION;
    }

    public interface SaveImageAsyncTaskDelegate {
        void onImageSaved(boolean success, File photoFile);
    }
}
