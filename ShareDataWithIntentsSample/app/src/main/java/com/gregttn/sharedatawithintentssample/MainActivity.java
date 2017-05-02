package com.gregttn.sharedatawithintentssample;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final int SEND_REQUEST = 91;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onShareButtonClicked(View view) {
        TextView textToShareTextView = (TextView) findViewById(R.id.textToShare);
        String textToShare = textToShareTextView.getText().toString();

        sendShareIntent(textToShare);
    }

    private void sendShareIntent(String content) {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, content);

        List<ResolveInfo> activities = getPackageManager().queryIntentActivities(shareIntent, PackageManager.MATCH_DEFAULT_ONLY);

        if (activities.isEmpty()) {
            Toast.makeText(this, R.string.no_activity_errior_message, Toast.LENGTH_SHORT).show();
        } else {
            String chooserTitle = getString(R.string.app_chooser_title);
            Intent chooserIntent = Intent.createChooser(shareIntent, chooserTitle);

            startActivityForResult(chooserIntent, SEND_REQUEST);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SEND_REQUEST) {
            String message = "Share result: ";

            switch (resultCode) {
                case RESULT_OK:
                    message += "OK";
                    break;
                case RESULT_CANCELED:
                    message += "CANCELLED";
                    break;

                default:
                    message += "UNKNOWN";
                    break;
            }

            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }
    }
}
