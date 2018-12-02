package com.example.jhovarie.androidjs;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by jhovarie on 24/03/2018.
 */

public class NativeToWeb extends Activity {
    WebView webview;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nativetoweb);

        webview = (WebView)this.findViewById(R.id.mainwebview);
        editText = (EditText)this.findViewById(R.id.mykeyval);
    }

    @SuppressLint({"SetJavaScriptEnabled", "JavascriptInterface"})
    @Override
    public void onStart() {
        super.onStart();
        WebSettings webSettings = webview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webview.loadUrl("file:///android_asset/nativetoweb.html");

        //this will update native from js
        //webview.addJavascriptInterface(new WebViewInterface(this), "Android");

    }

    public void updateweb(View v) {
        JSONObject valuesJson = new JSONObject();
        try {
            valuesJson.put("keyname", editText.getText().toString());
            webview.loadUrl( "javascript:setValues(" + valuesJson.toString() + ")" );
            //NOTE setNames is a javascript function inside your HTML
        } catch (JSONException e) {
            Log.e(getPackageName(), "Failed to create JSON object for web view");
        }
    }

}
