package com.example.jhovarie.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainmenu);
    }

    public void gotoLinearLayout(View v) {
        startActivity(new Intent(this,MyLinearLayout.class));
    }

    public void gotoRelativeLayout(View v) {
        startActivity(new Intent(this,MyRelativeLayout.class));
    }

}
