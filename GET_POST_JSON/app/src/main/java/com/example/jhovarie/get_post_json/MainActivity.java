package com.example.jhovarie.get_post_json;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;


public class MainActivity extends AppCompatActivity {
    final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //https://jhovarie.000webhostapp.com/index.php
    }

    public void GET_PARSE_JSON(View v) {
        Log.d(TAG, "GET_PARSE_JSON");
        //new startGETPARSEJSON().execute("https://jhovarie.000webhostapp.com/jsonnoarray.json");
        new startGETPARSEJSON().execute("https://jhovarie.000webhostapp.com/jsonwitharray.json");
    }

    public void POST_PARSE_JSON(View v) {
        Log.d(TAG, "POST_PARSE_JSON");
        new AsyncTask<String, Integer, String>() {
            protected void onPreExecute() {
                Log.d(TAG, "Connecting..");
            }

            @Override
            protected String doInBackground(String... params) {
                StringBuilder str = new StringBuilder();
                try {
                   // URLConnection connection = new URL("https://jhovarie.000webhostapp.com/jsonnoarray.json").openConnection();
                    URLConnection connection = new URL(params[0]).openConnection();
                    connection.setDoOutput(true);
                    String content =
                                   "id=" + URLEncoder.encode("username") +
                                    "&num=" + URLEncoder.encode("password") +
                                    "&remember=" + URLEncoder.encode("on") +
                                    "&output=" + URLEncoder.encode("xml");
                    connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                    connection.setRequestProperty("Content-Length", String.valueOf(content.getBytes().length));
                    OutputStream output = connection.getOutputStream();
                    output.write(content.getBytes());
                    output.close();

                    String line;
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    while ((line = reader.readLine()) != null) {
                        //System.out.println(line);
                        str.append(line);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                return str.toString();
            }
            protected void onPostExecute(String result) {
                Log.d(TAG, "Result -----------------------------------------");
                Log.d(TAG, result);
                Log.d(TAG, "start to parse json");
                //no array
                /*
                try {
                    JSONObject jObject = new JSONObject(result);
                    String name_a = jObject.getString("a");
                    String name_b =  jObject.getString("b");
                    String name_c =  jObject.getString("c");
                    Log.d(TAG,"a = "+name_a);
                    Log.d(TAG,"b = "+name_b);
                    Log.d(TAG,"c = "+name_c);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                */

                //json with array
                //copied from http://www.androidhive.info/2012/01/android-json-parsing-tutorial/
                try {
                    JSONObject jsonObj = new JSONObject(result);

                    // Getting JSON Array node
                    JSONArray contacts = jsonObj.getJSONArray("contacts");

                    // looping through All Contacts
                    for (int i = 0; i < contacts.length(); i++) {
                        JSONObject c = contacts.getJSONObject(i);

                        String id = c.getString("id");
                        String name = c.getString("name");
                        String email = c.getString("email");
                        String address = c.getString("address");
                        String gender = c.getString("gender");

                        // Phone node is JSON Object
                        JSONObject phone = c.getJSONObject("phone");
                        String mobile = phone.getString("mobile");
                        String home = phone.getString("home");
                        String office = phone.getString("office");

                        // tmp hash map for single contact
                        HashMap<String, String> contact = new HashMap<>();
                        // adding each child node to HashMap key => value
                        contact.put("id", id);
                        contact.put("name", name);
                        contact.put("email", email);
                        contact.put("mobile", mobile);

                        Log.d(TAG, "id = " + contact.get("id"));
                        Log.d(TAG, "name = " + contact.get("name"));
                        Log.d(TAG, "email = " + contact.get("email"));
                        Log.d(TAG, "mobile " + contact.get("mobile"));
                        // adding contact to contact list
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }.execute("https://jhovarie.000webhostapp.com/jsonwitharray.json");
       // }.execute("https://jhovarie.000webhostapp.com/jsonnoarray.json");

    }

    private class startGETPARSEJSON extends AsyncTask<String, Integer, String> {
        protected void onPreExecute() {
            Log.d(TAG, "Connecting..");
        }

        protected String doInBackground(String... params) {
            StringBuilder str = new StringBuilder();
            URL url;
            HttpURLConnection urlConnection = null;
            try {
                //url = new URL("https://jhovarie.000webhostapp.com/index.php");
                url = new URL(params[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = urlConnection.getInputStream();
                InputStreamReader isw = new InputStreamReader(in);

                int data = isw.read();
                while (data != -1) {
                    char current = (char) data;
                    data = isw.read();
                    str.append(current);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }
            return str.toString();
        }


        protected void onPostExecute(String result) {
            Log.d(TAG, "Result -----------------------------------------");
            Log.d(TAG, result);
            Log.d(TAG, "start to parse json");
            //json no array
            /*
            try {
                JSONObject jObject = new JSONObject(result);
                String name_a = jObject.getString("a");
                String name_b =  jObject.getString("b");
                String name_c =  jObject.getString("c");
                Log.d(TAG,"a = "+name_a);
                Log.d(TAG,"b = "+name_b);
                Log.d(TAG,"c = "+name_c);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            */

            //json with array
            //copied from http://www.androidhive.info/2012/01/android-json-parsing-tutorial/
            try {
                JSONObject jsonObj = new JSONObject(result);

                // Getting JSON Array node
                JSONArray contacts = jsonObj.getJSONArray("contacts");

                // looping through All Contacts
                for (int i = 0; i < contacts.length(); i++) {
                    JSONObject c = contacts.getJSONObject(i);

                    String id = c.getString("id");
                    String name = c.getString("name");
                    String email = c.getString("email");
                    String address = c.getString("address");
                    String gender = c.getString("gender");

                    // Phone node is JSON Object
                    JSONObject phone = c.getJSONObject("phone");
                    String mobile = phone.getString("mobile");
                    String home = phone.getString("home");
                    String office = phone.getString("office");

                    // tmp hash map for single contact
                    HashMap<String, String> contact = new HashMap<>();
                    // adding each child node to HashMap key => value
                    contact.put("id", id);
                    contact.put("name", name);
                    contact.put("email", email);
                    contact.put("mobile", mobile);

                    Log.d(TAG, "id = " + contact.get("id"));
                    Log.d(TAG, "name = " + contact.get("name"));
                    Log.d(TAG, "email = " + contact.get("email"));
                    Log.d(TAG, "mobile " + contact.get("mobile"));
                    // adding contact to contact list
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

}
