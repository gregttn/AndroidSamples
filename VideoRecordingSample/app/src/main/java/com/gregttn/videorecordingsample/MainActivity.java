package com.gregttn.videorecordingsample;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity {
    private static final int VIDEO_INTENT_ID = 109;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        boolean hasCamera = getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA);

        Button recordVideoButton = (Button) findViewById(R.id.record_video_button);
        recordVideoButton.setEnabled(hasCamera);
    }

    public void onRecordVideoButtonClicked(View view) {
        requestVideoRecording();
    }

    private void requestVideoRecording() {
        Intent videoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);

        if(videoIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(videoIntent, VIDEO_INTENT_ID);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == VIDEO_INTENT_ID && resultCode == RESULT_OK) {
            Uri videoUri = data.getData();

            startVideo(videoUri);
        }
    }

    private void startVideo(Uri videoUri) {
        VideoView videoPreview = (VideoView) findViewById(R.id.video_preview_view);
        videoPreview.setVideoURI(videoUri);
        videoPreview.start();
    }
}
