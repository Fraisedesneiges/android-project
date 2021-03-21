package com.example.matthieugedeon.android_project.classes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.database.DatabaseErrorHandler;
import android.util.Log;
import android.widget.Toast;

import java.util.NavigableMap;

public class DataBaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "UserDB";
    private static final String NAME = "UserTable";
    private static final String COL0 = "ID";
    private static final String COL1 = "username";
    private static final String COL2 = "password";
    private static final String COL3 = "wallets";


    public DataBaseHelper(Context context) {
        super(context, DB_NAME, null, 1);
        SQLiteDatabase myDB = this.getWritableDatabase();
    }


    //If the structure of the DB changes, we need to update it accordingly (with old version i and new version j)
    //https://stackoverflow.com/questions/13159210/onupgrade-sqlite-database-in-android
    @Override
    public void onUpgrade(SQLiteDatabase myDB, int i, int j) {
        String dropQuery = "DROP TABLE IF EXISTS " + NAME;
        myDB.execSQL(dropQuery);
        onCreate(myDB);
    }

    //When we create our database -> we initialise it with its columns
    public void onCreate(SQLiteDatabase myDB) {
        String creationQuery = "CREATE TABLE " + NAME + " (" + COL0 + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL1 + " TEXT NOT NULL UNIQUE, " + COL2 + " TEXT NOT NULL UNIQUE, " + COL3 + " TEXT)";
        Log.i("Creation query", creationQuery);
        myDB.execSQL(creationQuery);
    }

    //Adds a non existing user to the database
    public boolean addUser(String un, String pw)
    {
        Log.i("addUser", "reached");
        SQLiteDatabase myDB = this.getWritableDatabase();           //Opening or initializing the db
        ContentValues cv = new ContentValues();
        cv.put(COL1, un);
        cv.put(COL2, pw);
        String query = "SELECT * FROM " + NAME + " WHERE " + COL1 + " = '" + un + "'";
        Cursor cursor = myDB.rawQuery(query, null);
        if (cursor.getCount() <= 0) {
            long result = myDB.insert(NAME, null, cv);   //result becomes -1 if the insert does not go smoothly

            if (result == -1) {
                Log.i("DATABASE", "ERROR");
                return false;
            }
            Log.i("DATABASE", "OK");
            return true;
        }
        Log.i("ALREADY A USER", "EXITING");
        cursor.close();
        return false;
    }

    //Returns the wallets of the user
    public Cursor getUserData(String un) {
        SQLiteDatabase myDB = this.getWritableDatabase();
        String query = "SELECT wallets FROM " + NAME + " WHERE " + COL1 + " = '" + un + "'";
        Cursor c = myDB.rawQuery(query, null);
        return c;
    }

    //Checks if the user exists
    public boolean userCheck(String un, String pw) {
        SQLiteDatabase myDB = this.getWritableDatabase();
        String query = "SELECT * FROM " + NAME + " WHERE " + COL1 + " = '" + un + "' AND " + COL2 + " = '" + pw + "'";
        Cursor cursor = myDB.rawQuery(query, null);
        if (cursor.getCount() <= 0) {
            Log.i("NO SUCH USER", "EXITING");
            cursor.close();
            return false;
        }
        return true;
    }

    //Updates the Wallet for the user
    public void updateWallet(String un, String wl)
    {
        SQLiteDatabase myDB = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL3, wl);
        myDB.update(NAME, cv, COL1 + " = '" + un + "'", new String[]{});
        Log.i("WALLET", "UPDATED");
    }
}
