package com.gregttn.datastorage.stores;

import android.content.Context;
import android.os.Environment;

import com.gregttn.datastorage.R;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class DataStores {
    private final Map<StorageType, DataStore> dataStores = new HashMap<>();

    public DataStores(Context context) {
        dataStores.put(StorageType.SHARED_PREFERENCES, new SharedPreferencesDataStore(context));
        dataStores.put(StorageType.INTERNAL_FILE, new FileDataStore(context.getFilesDir(), context.getString(R.string.storage_file)));
        dataStores.put(StorageType.DATABASE, new Database(context));

        if (isExternalStorageAccessible()) {
            File externalDir = context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
            String filename = context.getString(R.string.external_storage_file);

            dataStores.put(StorageType.EXTERNAL_FILE, new FileDataStore(externalDir, filename));
        }
    }

    public DataStore get(StorageType storageType) {
        return dataStores.get(storageType);
    }

    public final boolean isExternalStorageAccessible() {
        String state = Environment.getExternalStorageState();

        return Environment.MEDIA_MOUNTED.equals(state);
    }

    public DiskSpace getDiskSpace(StorageType storageType, Context context) {
        switch (storageType) {
            case INTERNAL_FILE:
                File filesDir = context.getFilesDir();
                return new DiskSpace(filesDir.getFreeSpace(), filesDir.getTotalSpace());
            case EXTERNAL_FILE:
                File externalFilesDir = context.getFilesDir();
                return new DiskSpace(externalFilesDir.getFreeSpace(), externalFilesDir.getTotalSpace());
            default:
                return new DiskSpace(0L, 0L);
        }
    }

    public void close() {
        for( DataStore dataStore : dataStores.values()) {
            dataStore.close();
        }
    }
}
