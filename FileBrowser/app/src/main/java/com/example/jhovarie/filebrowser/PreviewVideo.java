package com.example.jhovarie.filebrowser;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.VideoView;

/**
 * Created by jhovarie on 14/01/2017.
 */

public class PreviewVideo extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.previewvideo);

        String filepath = getIntent().getStringExtra("videopath");

        VideoView videoView = (VideoView)findViewById(R.id.videoView);
        videoView.setVideoPath(filepath);
        videoView.start();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplication(), MainActivity.class));
    }
}
