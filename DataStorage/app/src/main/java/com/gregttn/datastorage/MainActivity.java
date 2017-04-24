package com.gregttn.datastorage;

import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.gregttn.datastorage.stores.DataStores;
import com.gregttn.datastorage.stores.StorageType;

public class MainActivity extends AppCompatActivity {
    private StorageType storageType = StorageType.SHARED_PREFERENCES;
    private DataStores dataStores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dataStores = new DataStores(this);

        RadioGroup dataStoreRadioGroup = (RadioGroup) findViewById(R.id.store_selection_radio_group);
        dataStoreRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                storageType = storageTypeForRadioButtonId(checkedId);
                updateSpaceStatisticsFragment();
            }
        });

        RadioButton externalStorageRadioButton = (RadioButton) findViewById(R.id.external_file_radio_button);
        externalStorageRadioButton.setEnabled(dataStores.isExternalStorageAccessible());

        updateSpaceStatisticsFragment();
    }

    @Override
    protected void onDestroy() {
        dataStores.close();
        super.onDestroy();
    }

    public void saveButtonClicked(View view) {
        EditText enteredText = (EditText) findViewById(R.id.save_text);

        dataStores.get(storageType).save(enteredText.getText().toString());

        Toast.makeText(this, R.string.saved_toast, Toast.LENGTH_SHORT).show();
    }

    public void retrieveButtonClicked(View view) {
        TextView retrievedView = (TextView) findViewById(R.id.retrieved_text_view);

        String retrievedData = dataStores.get(storageType).retrieve();
        retrievedView.setText(retrievedData);
    }

    private StorageType storageTypeForRadioButtonId(int checkedButtonId) {
        switch (checkedButtonId) {
            case R.id.shared_preferences_radio_button: return StorageType.SHARED_PREFERENCES;
            case R.id.internal_file_radio_button: return StorageType.INTERNAL_FILE;
            case R.id.external_file_radio_button: return StorageType.EXTERNAL_FILE;
            case R.id.database_radio_button: return StorageType.DATABASE;
            default: return StorageType.SHARED_PREFERENCES;
        }
    }

    private void updateSpaceStatisticsFragment() {
        SpaceStatistics spaceStatistics = (SpaceStatistics) getSupportFragmentManager().findFragmentById(R.id.fragment_space_statistics);
        spaceStatistics.updateDisplay(storageType, dataStores.getDiskSpace(storageType, this));
    }
}
