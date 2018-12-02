package com.example.jhovarie.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by jhovarie on 01/04/2018.
 */

public class SQLiteFromNet extends Activity {

    EditText etInput;
    TextView txtText;
    Button btnAdd, btnDelete;
    MyDBHandler dbHandler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sqlitefromnet);
        btnAdd = (Button)findViewById(R.id.btnAdd);
        btnDelete = (Button)findViewById(R.id.btnDelete);
        etInput = (EditText)findViewById(R.id.etInput);
        txtText = (TextView)findViewById(R.id.txtText);

        dbHandler = new MyDBHandler(this, null, null, 1);
        try {
            printDatabase();
        }catch (Exception e){
            Log.i("exxxx", e.toString());
        }
    }

    public void printDatabase() {
        String dbString = dbHandler.databaseToString();
        txtText.setText(dbString);
        etInput.setText("");
    }

    //Add a product to the database
    public void addButtonClicked(View view){
        Log.i("exxxx", "CLİCKED ADD BUTTON");
        String product = etInput.getText().toString();
        Products p = new Products(product);
        dbHandler.addProduct(p);
        printDatabase();
    }

    //Delete a product to the database
    public void deleteButtonClicked(View view){
        Log.i("exxxx", "CLİCKED DELETE BUTTON");
        String inputText = etInput.getText().toString();
        dbHandler.deleteProduct(inputText);
        printDatabase();
    }
}


