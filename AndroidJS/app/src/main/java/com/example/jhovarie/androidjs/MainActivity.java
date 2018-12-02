package com.example.jhovarie.androidjs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        setContentView(R.layout.mainmenu);
    }

   public void gotoNativeAndWebview(View v) {
        startActivity(new Intent(this, NativeAndWebViewExample.class));
   }

   public void gotoNativeToWeb(View v) {
       startActivity(new Intent(this, NativeToWeb.class));
   }

   public void gotoWebToNative(View v) {
       startActivity(new Intent(this, WebToNative.class));
   }


   public void gotoPhaser(View v) {
        startActivity(new Intent(this,Phaser3.class ));
   }
}
