package com.gregttn.sharetextfilesample;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.content.FileProvider;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.File;

public class FileSelectionActivity extends Activity {
    private final ContentPersister contentPersister = new ContentPersister(this);
    private File[] noteFiles;
    private Intent resultIntent;
    private ListView filesListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.resultIntent = new Intent("com.gregttn.sharetextfilesample.ACTION_RETURN_FILE");
        this.noteFiles = contentPersister.getNotes();

        setResult(Activity.RESULT_CANCELED, null);
        setContentView(R.layout.activity_file_selection);

        filesListView = (ListView) findViewById(R.id.files_list_view);

        String[] filenames = new String[noteFiles.length];

        for(int index = 0; index < filenames.length; index++) {
            filenames[index] = noteFiles[index].getName();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, filenames);
        filesListView.setAdapter(adapter);

        filesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                File requestedFile = noteFiles[position];

                Uri fileUri = FileProvider.getUriForFile(FileSelectionActivity.this,
                        "com.gregttn.sharetextfilesample.fileprovider",
                        requestedFile);

                if (fileUri != null) {
                    resultIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    resultIntent.setDataAndType(fileUri, getContentResolver().getType(fileUri));
                    FileSelectionActivity.this.setResult(Activity.RESULT_OK, resultIntent);
                } else {
                    resultIntent.setDataAndType(null, "");
                    FileSelectionActivity.this.setResult(RESULT_CANCELED, resultIntent);
                }

                FileSelectionActivity.this.finish();
            }
        });
    }
}
