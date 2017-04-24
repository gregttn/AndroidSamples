package com.gregttn.datastorage.stores;

public interface DataStore {
    void save(String data);
    String retrieve();
    void close();
}
