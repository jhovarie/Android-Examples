package com.example.jhovarie.filebrowser;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private static final int SELECT_PICTURE = 1;
    private String selectedFilePath;

    final String TAG = "MainActivity";
    private int filetype = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void BrowseImage(View v) {
        filetype = 0;
        Log.d(TAG, "browse button is clicked.");
        Intent intent = new Intent();
        intent.setType("image/*"); //if image only
        //intent.setType("video/*"); //if video only
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,
                "Select Picture"), SELECT_PICTURE);
    }

    public void BrowseVideo(View v) {
        filetype = 1;
        Log.d(TAG, "browse button is clicked.");
        Intent intent = new Intent();
        //intent.setType("image/*"); //if image only
        intent.setType("video/*"); //if video only
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,
                "Select Picture"), SELECT_PICTURE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();
                selectedFilePath = getPath(selectedImageUri);

                Log.d(TAG,"File Path = " + selectedFilePath);
                //Toast.makeText(MainActivity.this,"File Path = " + selectedFilePath,Toast.LENGTH_LONG).show();
                if(filetype == 0) { //preview image
                    startActivity(new Intent(MainActivity.this, PreviewImage.class).putExtra("imagepath", selectedFilePath));
                } else if(filetype == 1) {
                    //alert dialog 2 option http://stackoverflow.com/questions/2115758/how-do-i-display-an-alert-dialog-on-android
                    //alert dialog 3 or more option http://stackoverflow.com/questions/4671428/how-can-i-add-a-third-button-to-an-android-alert-dialog

                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("Title");
                    builder.setItems(new CharSequence[]
                                    {"VidieoView", "MediaPlayer", "Extract Frame","Cancel"},
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // The 'which' argument contains the index position
                                    // of the selected item
                                    switch (which) {
                                        case 0:
                                            //play using video view
                                            startActivity(new Intent(getApplication(), PreviewVideo.class).putExtra("videopath", selectedFilePath));
                                            break;
                                        case 1:
                                            //play using Media player
                                            startActivity(new Intent(getApplication(), PreviewVideo2.class).putExtra("videopath", selectedFilePath));
                                            break;
                                        case 2:

                                            int duration = MediaPlayer.create(getApplication(), Uri.fromFile(new File(selectedFilePath))).getDuration();
                                           // int midframe = duration / 2;
                                            //1 miliseconds have 1000 microseconds..
                                            int millis = duration;
                                            int microseconds = millis * 1000;
                                            Log.d(TAG,"video duration = "+duration);

                                            MediaMetadataRetriever retriever = new MediaMetadataRetriever();
                                            retriever.setDataSource(selectedFilePath);
                                            Bitmap bitmap=retriever.getFrameAtTime(microseconds ,MediaMetadataRetriever.OPTION_CLOSEST_SYNC);
                                            GlobalVar.setBitmap(bitmap);
                                            startActivity(new Intent(MainActivity.this, PreviewImage.class).putExtra("imagepath", "wala"));
                                            break;
                                        case 3:
                                            //cancel
                                            break;
                                    }
                                }
                            });
                    builder.create().show();
                }
            }
        }
    }

    /**
     * helper to retrieve the path of an image URI
     */
    private String getPath(Uri uri) {
        // just some safety built in
        if( uri == null ) {
            // TODO perform some logging or show user feedback
            return null;
        }
        // try to retrieve the image from the media store first
        // this will only work for images selected from gallery
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        if( cursor != null ){
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            String path = cursor.getString(column_index);
            cursor.close();
            return path;
        }
        // this is our fallback here
        return uri.getPath();
    }
}