package com.example.jhovarie.filebrowser;

import android.app.Activity;
import android.graphics.PixelFormat;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import java.io.File;

/**
 * Created by jhovarie on 14/01/2017.
 */

public class PreviewVideo2 extends Activity {

    private SurfaceView surfaceView;
    MediaPlayer mediaPlayer = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.previewvideo2);

        surfaceView = (SurfaceView) findViewById(R.id.surfaceView1);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Do something after 100ms
                try{
                    if (mediaPlayer != null) {
                        mediaPlayer.reset();
                        mediaPlayer.release();
                    }else{
                        String filepath = getIntent().getStringExtra("videopath");

                        getWindow().setFormat(PixelFormat.UNKNOWN);
                        //MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.wildlife);
                        MediaPlayer mediaPlayer = MediaPlayer.create(PreviewVideo2.this, Uri.fromFile(new File(filepath)));
                        SurfaceHolder surfaceHolder = surfaceView.getHolder();

                        surfaceHolder.setFixedSize(176, 144);
                        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
                        mediaPlayer.setDisplay(surfaceHolder);
                        mediaPlayer.start();}
                }catch (Exception e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }, 100);
    }

}