package com.gregttn.systempermissionssample;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_PERMISSION = 92;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (REQUEST_PERMISSION != requestCode) {
            return;
        }

        boolean isGranted = grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED;

        String message = new StringBuilder("Permission ")
                .append(permissions[0])
                .append(" was ")
                .append(isGranted ? "GRANTED!" : "DENIED!")
                .toString();

        userMessage(message);
    }

    public void onCheckButtonClicked(View view) {
        String permission = getPermissionToCheck();
        boolean isAllowed = checkPermission(permission);

        String message = isAllowed ? "Permission GRANTED" : "Permission DENIED";
        userMessage(message);
    }

    public void onRequestButtonClicked(View view) {
        String permission = getPermissionToCheck();

        if (checkPermission(permission)) {
            userMessage("Permission already GRANTED");
        } else {
            String[] permissions = {permission};
            ActivityCompat.requestPermissions(this, permissions, REQUEST_PERMISSION);
        }
    }

    private String getPermissionToCheck() {
        int selectedRadioButton = ((RadioGroup)findViewById(R.id.permissions_radio_group)).getCheckedRadioButtonId();

        switch (selectedRadioButton) {
            case R.id.read_calendar_radio_button:
                return Manifest.permission.READ_CALENDAR;
            case R.id.write_to_calendar_radio_button:
                return Manifest.permission.WRITE_CALENDAR;
            case R.id.read_contact_radio_button:
                return Manifest.permission.READ_CONTACTS;
            case R.id.write_contact_radio_button:
                return Manifest.permission.WRITE_CONTACTS;
            case R.id.use_camera_radio_button:
                return Manifest.permission.CAMERA;
            default:
                throw new RuntimeException("Unknown permission!");
        }
    }

    private boolean checkPermission(String permission) {
        return ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED;
    }

    private void userMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
