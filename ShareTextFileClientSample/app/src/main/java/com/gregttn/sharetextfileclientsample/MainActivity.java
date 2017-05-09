package com.gregttn.sharetextfileclientsample;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.provider.OpenableColumns;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onRequestFileButtonClicked(View view) {
        Intent requestIntent = new Intent(Intent.ACTION_PICK);
        requestIntent.setType("text/plain");

        startActivityForResult(requestIntent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent returnIntent) {
        if (resultCode != RESULT_OK) return;

        try {
            Uri fileUri = returnIntent.getData();

            updateFileInfo(fileUri);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Could not open the file", Toast.LENGTH_SHORT).show();
        }
    }

    private String readContent(Uri fileUri) throws IOException {
        ParcelFileDescriptor parcelFileDescriptor = getContentResolver().openFileDescriptor(fileUri, "r");
        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        BufferedReader in =  new BufferedReader(new InputStreamReader(new FileInputStream(fileDescriptor)));

        StringBuilder content = new StringBuilder("");
        String line;
        while((line = in.readLine()) != null) {
            content.append(line);
        }

        return content.toString();
    }

    private void updateFileInfo(Uri fileUri) throws IOException {
        FileInfo fileInfo = queryFileInfo(fileUri);
        String fileContent = readContent(fileUri);
        TextView filenameTextView = (TextView) findViewById(R.id.filename_text_view);
        filenameTextView.setText(fileInfo.filename);

        TextView mimeTypeTextView = (TextView) findViewById(R.id.mime_type_text_view);
        mimeTypeTextView.setText(fileInfo.mimeType);

        TextView sizeTextView = (TextView) findViewById(R.id.size_text_view);
        sizeTextView.setText(String.valueOf(fileInfo.size));

        TextView contentTextView = (TextView) findViewById(R.id.file_content_text_view);
        contentTextView.setText(fileContent);
    }

    private FileInfo queryFileInfo(Uri fileUri) {
        Cursor cursor = getContentResolver().query(fileUri, null, null, null, null);
        cursor.moveToFirst();

        int filenameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
        int sizeIndex = cursor.getColumnIndex(OpenableColumns.SIZE);

        return new FileInfo(
                cursor.getString(filenameIndex),
                getContentResolver().getType(fileUri),
                cursor.getLong(sizeIndex)
        );
    }

    class FileInfo {
        private final String filename;
        private final String mimeType;
        private final Long size;

        FileInfo(String filename, String mimeType, Long size) {
            this.filename = filename;
            this.mimeType = mimeType;
            this.size = size;
        }
    }
}
