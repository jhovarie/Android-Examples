package com.example.jhovarie.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import java.util.ArrayList;

/**
 * Created by jhovarie on 01/04/2018.
 */

public class JAGSQLite extends SQLiteOpenHelper {
    private static final String TAG = "JAGSQLite";
    private static final int DATABASE_VERSION = 3;
    private static String DATABASE_NAME = "products.db";
  ///  public static String TABLE_PRODUCTS = "products";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_PRODUCTNAME = "productname";

    private static String tablename1 = null, tablename2 = null, tablename3 = null;
    private static String createtable1 = null, createtable2 = null, createtable3 = null;
    //private boolean isinit = false;

    public JAGSQLite(Context context,String dbname) {
        super(context, dbname, null, DATABASE_VERSION);
        this.DATABASE_NAME  = dbname;
    }

    public void initTables1(String table,String columns[],String types[]) {
        createtable1 = initTables(table, columns, types);
    }
    public void initTables2(String table,String columns[],String types[]) {
        createtable2 = initTables(table, columns, types);
    }
    public void initTables3(String table,String columns[],String types[]) {
        createtable3 = initTables(table, columns, types);
    }

    private String initTables(String table,String columns[],String types[]) {
        String query = "CREATE TABLE "+table+" (_id INTEGER PRIMARY KEY AUTOINCREMENT,";
        for(int i = 0; i < columns.length; i++) {
            query+= columns[i] + " " + types[i];
            if( i + 1 < columns.length) {
                query+=",";
            }
        }
        query+=");";
        return query;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        if(createtable1 != null) {
            db.execSQL(createtable1);
            Log.d(TAG,"create table1");
        }
        if(createtable2 != null) {
            db.execSQL(createtable2);
            Log.d(TAG,"create table2");
        }
        if(createtable3 != null) {
            db.execSQL(createtable3);
            Log.d(TAG,"create table3");
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(tablename1!=null) {
            db.execSQL("DROP TABLE IF EXIST " + tablename1);
        }
        if(tablename2!=null) {
            db.execSQL("DROP TABLE IF EXIST " + tablename2);
        }
        if(tablename3!=null) {
            db.execSQL("DROP TABLE IF EXIST " + tablename3);
        }
        //db.execSQL("DROP TABLE IF EXIST " + TABLE_PRODUCTS);
        onCreate(db);
        Log.d(TAG,"onUpgrade");
    }

    public void insertRecord(String table, String column[],String val[]) {
        ContentValues values = new ContentValues();
        for(int i = 0; i < val.length; i++) {
            values.put(column[i], val[i]);
        }
        SQLiteDatabase db = getWritableDatabase();
        db.insert(table, null, values);
        db.close();
        Log.d(TAG,"insertRecord");
    }

    public void updateTable(String table, String columntoupdate, String oldvalue, String newvalue){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE "+table+ " set "+columntoupdate+" = \""+newvalue+"\" WHERE "+ columntoupdate + "=\"" + oldvalue + "\";");
        Log.d(TAG,"updateTable");
    }

    public void updateTable(String table, String columntoupdate, String newvalue, String wherecolumn,String wherevalue){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE "+table+ " set "+columntoupdate+" = \""+newvalue+"\" WHERE "+ wherecolumn + "=\"" + wherevalue + "\";");
        Log.d(TAG,"updateTable");
    }

    public void deleteRow(String table, String column, String value) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + table + " WHERE " + column + "=\"" + value + "\";");
        Log.d(TAG,"deleteProduct");
    }

    //Print out the database as a string
    public String databaseToString(String table){
        String dbString = "";
        SQLiteDatabase db =  getWritableDatabase();
        String query = "SELECT * FROM " + table + " WHERE 1";

        //Cursor point to a location in your result
        Cursor c = db.rawQuery(query, null);
        //Move to first row in your result
        c.moveToFirst();

        //Position after the last row means the end of the results
        while (!c.isAfterLast()) {
            if (c.getString(c.getColumnIndex("productname")) != null) {
                dbString += c.getString(c.getColumnIndex("productname"));
                dbString += "\n";
            }
            c.moveToNext();
        }
        db.close();
        return dbString;
    }

    //Print out the database as a string
    public String[] selectRowWhereEqual(String table, String column, String value){
        ArrayList<String> str = new ArrayList();
        //String dbString = "";
        SQLiteDatabase db =  getWritableDatabase();
        String query = "SELECT * FROM " + table + " WHERE "+column+" = \""+value+"\" ORDER BY _id DESC limit 1 ";

        //Cursor point to a location in your result
        Cursor c = db.rawQuery(query, null);
        //Move to first row in your result
        c.moveToFirst();

        //Position after the last row means the end of the results
        while (!c.isAfterLast()) {
            if (c.getString(c.getColumnIndex(column)) != null) {
                //dbString += c.getString(c.getColumnIndex(column));
                str.add(c.getString(c.getColumnIndex(column)));
                //dbString += "\n";
            }
            c.moveToNext();
        }
        db.close();
        String str2[] = new String[str.size()];
        for(int i = 0; i < str.size(); i++) {
            str2[i] = str.get(i);
        }
        return str2;
    }
    
      public String[] selectRowWhereEqual(String table, String column, String value, String returncolumn[]){
        ArrayList<String> str = new ArrayList();
        //String dbString = "";
        SQLiteDatabase db =  getWritableDatabase();
        String query = "SELECT * FROM " + table + " WHERE "+column+" = \""+value+"\" ORDER BY _id DESC limit 1 ";

        //Cursor point to a location in your result
        Cursor c = db.rawQuery(query, null);
        //Move to first row in your result
        c.moveToFirst();

        //Position after the last row means the end of the results
        while (!c.isAfterLast()) {
            if (c.getString(c.getColumnIndex(column)) != null) {
                //dbString += c.getString(c.getColumnIndex(column));
                for(int i = 0; i < returncolumn.length;i++) {
                    str.add(c.getString(c.getColumnIndex(returncolumn[i])));
                }
                //str.add(c.getString(c.getColumnIndex(column)));
                //dbString += "\n";
            }
            c.moveToNext();
        }
        db.close();
        String str2[] = new String[str.size()];
        for(int i = 0; i < str.size(); i++) {
            str2[i] = str.get(i);
        }
        return str2;
    }

    //Print out the database as a string
    public String[] selectAllColumn(String table, String column){
        ArrayList<String> str = new ArrayList<>();
        String dbString = "";
        SQLiteDatabase db =  getWritableDatabase();
        String query = "SELECT * FROM " + table + " WHERE 1 ORDER BY _id DESC";
        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();
        while (!c.isAfterLast()) {
            if (c.getString(c.getColumnIndex(column)) != null) {
                str.add(c.getString(c.getColumnIndex(column)));
            }
            c.moveToNext();
        }
        db.close();

        String str2[] = new String[str.size()];
        for(int i = 0; i < str.size(); i++) {
            str2[i] = str.get(i);
        }
        return str2;
    }

    //Print out the database as a string
    public String[] selectSearchAllColumn(String table, String column,String find){
        ArrayList<String> str = new ArrayList<>();
        String dbString = "";
        SQLiteDatabase db =  getWritableDatabase();
        String query = "SELECT * FROM " + table + " WHERE "+column+" like \"%"+find+"%\" ORDER BY _id DESC";
        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();
        while (!c.isAfterLast()) {
            if (c.getString(c.getColumnIndex(column)) != null) {
                str.add(c.getString(c.getColumnIndex(column)));
            }
            c.moveToNext();
        }
        db.close();
        String str2[] = new String[str.size()];
        for(int i = 0; i < str.size(); i++) {
            str2[i] = str.get(i);
        }
        return str2;
    }

}
