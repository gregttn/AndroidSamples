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

            startActivity(chooserIntent);
        }
    }
}
