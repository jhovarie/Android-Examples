package com.example.jhovarie.myapplication;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

/*
To prevent your layout from recreation go to your manifest
in your activity add  android:configChanges="orientation|screenSize"
*/

public class MainActivity extends Activity {
    final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toast.makeText(this,"Activity Created", Toast.LENGTH_LONG).show();
        Log.d(TAG,"onCreate");
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Toast.makeText(this,"Landscape", Toast.LENGTH_LONG).show();
        }
        if(newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            Toast.makeText(this,"Portrait", Toast.LENGTH_LONG).show();
        }
    }
}
