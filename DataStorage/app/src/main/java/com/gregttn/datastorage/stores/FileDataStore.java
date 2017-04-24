package com.gregttn.datastorage.stores;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class FileDataStore implements DataStore {
    private final File filesDir;
    private final String filename;

    public FileDataStore(File filesDir, String filename) {
        this.filesDir = filesDir;
        this.filename = filename;
    }

    @Override
    public void save(String data) {
        File file = new File(filesDir, filename);

        try(BufferedWriter writer= new BufferedWriter(new FileWriter(file))) {
            writer.write(data);
        } catch(Exception e) {
            throw new RuntimeException("Could not write to " + filename, e);
        }
    }

    @Override
    public String retrieve() {
        File file = new File(filesDir, filename);

        try(BufferedReader reader = new BufferedReader(new FileReader(file))) {
            return reader.readLine();
        } catch(Exception e) {
            throw new RuntimeException("Could not read " + filename, e);
        }
    }

    @Override
    public void close() {}
}
