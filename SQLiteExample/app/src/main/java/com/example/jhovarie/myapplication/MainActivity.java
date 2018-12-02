package com.example.jhovarie.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;


public class MainActivity extends Activity {
    String TAG = "MainActivity";
    JAGSQLite jagsql;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mymain);

        jagsql = new JAGSQLite(this,"products.db");
        String cols[] = {"productname"};
        String keys[] = {"TEXT"};
        jagsql.initTables1("products",cols,keys);
       // jagsql.init("products.db","products");

    }

    public void INSERT(View v) {
       // jagsql.addProduct("kamote");
        jagsql.insertRecord("products", new String[]{"productname"},new String[]{"milk"});
    }

    public void UPDATE(View v) {
        //jagsql.updateProduct("king","queen");
        jagsql.updateTable("products","productname","milk",  "milo");
    }

    public void DELETE(View v) {
        //jagsql.deleteProduct("kamote");
        jagsql.deleteRow("products","productname","milk");
    }

    public void SELECT(View v) {
        String str[] = jagsql.selectRowWhereEqual("products","productname","milk");
        for(int i = 0; i < str.length; i++) {
            Log.d(TAG,str[i]);
        }
       // Log.d(TAG,str);
    }

    public void SELECTALL(View v) {
       // String str = jagsql.databaseToString();
        String str2[] = jagsql.selectAllColumn("products","productname");
        for(int i = 0; i < str2.length; i++) {
            Log.d(TAG,str2[i]);
        }
    }

    public void SEARCH(View v) {
        String str2[] = jagsql.selectSearchAllColumn("products","productname","m");
        for(int i = 0; i < str2.length; i++) {
            Log.d(TAG,str2[i]);
        }
    }
}
