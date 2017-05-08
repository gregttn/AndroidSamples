package com.gregttn.sharetextfilesample;

import android.content.Context;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

public class ContentPersister {
    private final String NOTES_DIR = "notes";

    private final Context context;

    public ContentPersister(Context context) {
        this.context = context;
    }

    public void save(String filename, String content) throws IOException {
        File filesDir = context.getFilesDir();
        File notesDir = new File(filesDir, NOTES_DIR);

        if (!notesDir.exists()) notesDir.mkdirs();

        File note = new File(notesDir, filename);

        if (!note.exists() && !note.createNewFile()) {
            throw new IOException("Failed to create " + filename);
        }

        try (Writer writer = new FileWriter(note)) {
            writer.write(content);
        }
    }

    public File[] getNotes() {
        File filesDir = context.getFilesDir();
        File notesDir = new File(filesDir, NOTES_DIR);

        return notesDir.listFiles();
    }

}
