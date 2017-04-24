package com.gregttn.datastorage;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gregttn.datastorage.stores.DiskSpace;
import com.gregttn.datastorage.stores.StorageType;

import static com.gregttn.datastorage.stores.StorageType.DATABASE;
import static com.gregttn.datastorage.stores.StorageType.SHARED_PREFERENCES;

public class SpaceStatistics extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_space_statistics, container, false);
    }

    public void updateDisplay(StorageType storageType, DiskSpace space) {
        if (SHARED_PREFERENCES.equals(storageType) || DATABASE.equals(storageType)) {
            getView().setVisibility(View.INVISIBLE);
            return;
        }

        getView().setVisibility(View.VISIBLE);

        TextView freeSpaceTextView = (TextView) getView().findViewById(R.id.free_space_figure_text_view);
        freeSpaceTextView.setText(space.getFreeSpace() + " bytes");

        TextView totalSpaceTextView = (TextView) getView().findViewById(R.id.total_space_figure_text_view);
        totalSpaceTextView.setText(space.getTotalSpace() + " bytes");
    }
}
