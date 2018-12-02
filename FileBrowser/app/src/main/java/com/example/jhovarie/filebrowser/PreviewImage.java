package com.example.jhovarie.filebrowser;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

/**
 * Created by jhovarie on 14/01/2017.
 */

public class PreviewImage extends Activity {
    final String TAG = "PreviewImage";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String filepath = getIntent().getStringExtra("imagepath");
        Log.d(TAG, "filepath "+filepath);
        setContentView(R.layout.previewimage);

        ImageView imageView = (ImageView)findViewById(R.id.imageView);
        Bitmap bmp = BitmapFactory.decodeFile(filepath);
        if(bmp != null) {
            imageView.setImageBitmap(bmp);
        } else {
            Bitmap bmp2 = GlobalVar.getExtractedFrame();
            imageView.setImageBitmap(bmp2);
            Log.d(TAG,"null bitmap then used bitmap extracted frame");
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplication(), MainActivity.class));
        finish();
    }
}
