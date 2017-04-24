package com.gregttn.datastorage.stores;


public class DiskSpace {
    private final long freeSpace;
    private final long totalSpace;

    public DiskSpace(long freeSpace, long totalSpace) {
        this.freeSpace = freeSpace;
        this.totalSpace = totalSpace;
    }

    public long getTotalSpace() {
        return totalSpace;
    }

    public long getFreeSpace() {
        return freeSpace;
    }
}
