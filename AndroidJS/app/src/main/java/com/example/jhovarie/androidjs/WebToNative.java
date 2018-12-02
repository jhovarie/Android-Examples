package com.example.jhovarie.androidjs;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by jhovarie on 24/03/2018.
 */

public class WebToNative extends Activity {
    WebView webview;
    EditText fnameEditText;
    EditText lnameEditText;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        setContentView(R.layout.webtonative);
        //setContentView(R.layout.weblayout);

        //WebView wb = (WebView)findViewById(R.id.mainwebview);
        //wb.loadUrl("http://localhost/");

        fnameEditText = (EditText)this.findViewById(R.id.fnameEditText);
        lnameEditText = (EditText)this.findViewById(R.id.lnameEditText);

        webview = (WebView)this.findViewById(R.id.webView);


    }

    @Override
    public void onStart() {
        super.onStart();
        WebSettings webSettings = webview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webview.loadUrl("file:///android_asset/webtonative.html");
        webview.addJavascriptInterface(new WebViewInterface(this), "Android");
    }


    public class WebViewInterface {
        Context mContext;

        /** Instantiate the interface and set the context */
        WebViewInterface(Context c) {
            mContext = c;
        }

        /*
         * Uncomment if compiling for Android 4.2
         * @JavascriptInterface
         */
        @JavascriptInterface
        public void updateValues(String namesJsonString) {
            Log.d(getPackageName(), "Sent from webview: " + namesJsonString);
            try {

                JSONObject namesJson = new JSONObject(namesJsonString);
                final String keyname = namesJson.getString("keyname");

                // When invoked from Javascript this is executed on a thread other than the UI thread
                // Since we want to update the native UI controls we must create a runnable for the main UI thread.
                runOnUiThread(new Runnable() {
                    public void run() {
                        fnameEditText.setText(keyname);
                    }
                });

            } catch (JSONException e) {
                Log.e(getPackageName(), "Failed to create JSON object from web view data");
            }

        }

    }
}
