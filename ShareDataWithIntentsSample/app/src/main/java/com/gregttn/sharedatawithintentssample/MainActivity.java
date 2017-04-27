package com.gregttn.sharedatawithintentssample;

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
    }

    public void onShareButtonClicked(View view) {
        TextView textToShareTextView = (TextView) findViewById(R.id.textToShare);
        String textToShare = textToShareTextView.getText().toString();

        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/*");
        shareIntent.putExtra(Intent.EXTRA_TEXT, textToShare);

        startActivity(shareIntent);
    }
}
