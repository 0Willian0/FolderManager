package com.example.foldermanager;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

public class Video extends Activity {

    VideoView videoView;
    Uri video;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.videoview);
        video = Uri.parse(getIntent().getStringExtra("Video"));
        videoView = findViewById(R.id.video);
        videoView.setVideoURI(video);
        MediaController mediaController = new MediaController(this);
        videoView.setMediaController(mediaController);
        mediaController.setAnchorView(videoView);
    }
}
