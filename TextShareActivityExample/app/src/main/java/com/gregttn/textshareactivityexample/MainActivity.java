package com.gregttn.textshareactivityexample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();

        if (Intent.ACTION_SEND.equals(intent.getAction())) {
            String shareContent = intent.getStringExtra(Intent.EXTRA_TEXT);

            TextView shareContentTextView = (TextView) findViewById(R.id.share_content_text_view);
            shareContentTextView.setText(shareContent);
        }
    }

    public void onButtonClicked(View view) {
        int viewId = view.getId();
        int result = viewId == R.id.share_button ? RESULT_OK : RESULT_CANCELED;

        Intent resultIntent = new Intent("com.gregttn.RESULT_ACTION");
        setResult(result, resultIntent);

        finish();
    }
}
