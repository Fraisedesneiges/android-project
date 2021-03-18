package com.example.matthieugedeon.android_project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.database.DatabaseErrorHandler;

public class DataBaseHelper extends SQLiteOpenHelper {

    private static final String NAME = "UserTable";
    private static final String COL0 = "ID";
    private static final String COL1 = "username";
    private static final String COL2 = "password";
    private static final String COL3 = "wallets";


    public DataBaseHelper(Context context) {
        super(context, NAME, null, 1);
    }


    //If the structure of the DB changes, we need to update it accordingly (with old version i and new version j)
    //https://stackoverflow.com/questions/13159210/onupgrade-sqlite-database-in-android
    @Override
    public void onUpgrade(SQLiteDatabase myDB, int i, int j) {
        String dropQuery = "DROP TABLE IF EXISTS " + NAME;
        myDB.execSQL(dropQuery);
    }

    //When we create our database -> we initialise it with its columns
    public void onCreate(SQLiteDatabase myDB) {
        String creationQuery = "CREATE TABLE " + NAME + " (" + COL0 + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL1 + " TEXT NOT NULL UNIQUE, " + COL2 + " TEXT NOT NULL UNIQUE, " + COL3 + " TEXT)";
        myDB.execSQL(creationQuery);
    }

    public boolean addUser(String un, String pw)                    //maybe use a JSON instead idk
    {
        SQLiteDatabase myDB = this.getWritableDatabase();           //Opening or initializing the db
        ContentValues cv = new ContentValues();
        cv.put(COL1, un);
        cv.put(COL2, pw);

        long result = myDB.insert(NAME, null, cv);   //result becomes -1 if the insert does not go smoothly

        if (result == -1)
            return false;
        return true;
    }

    public Cursor getUserData(String un)
    {
        SQLiteDatabase myDB = this.getWritableDatabase();
        String query = "SELECT * FROM " + NAME + " WHERE " + COL1 + " = " + un;
        Cursor c = myDB.rawQuery(query, null);
        return c;
    }

}
