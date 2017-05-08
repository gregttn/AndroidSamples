package com.gregttn.sharetextfilesample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private final ContentPersister contentPersister = new ContentPersister(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onSaveButtonClicked(View view) {
        EditText filenameEditText = (EditText) findViewById(R.id.file_name_text_view);
        EditText contentEditText = (EditText) findViewById(R.id.content_text_view);
        String filename = filenameEditText.getText().toString();
        String content = contentEditText.getText().toString();

        try {
            contentPersister.save(filename, content);
            Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Saving failed", Toast.LENGTH_SHORT).show();
        }
    }
}
