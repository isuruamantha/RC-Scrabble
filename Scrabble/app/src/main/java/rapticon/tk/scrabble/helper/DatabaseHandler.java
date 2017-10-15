package rapticon.tk.scrabble.helper;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;

import rapticon.tk.scrabble.service.SharedPreference;

/**
 * Created by Isuru Amantha on 10/10/2017.
 */

public class DatabaseHandler extends SQLiteAssetHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "database.db";
    // Contacts table name
    private static final String TABLE_NAME = "words";
    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "word";
    private Activity mActivity;

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    public void addValuesToDatabase(ArrayList<String> strings) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

//        for (int i = 0; i < strings.size(); i++) {
        for (int i = 0; i < 11; i++) {
            SharedPreference.setDatabaseCreation(mActivity, true);
            values.put(KEY_ID, i);
            values.put(KEY_NAME, strings.get(i));
            db.insert(TABLE_NAME, null, values);
            values.clear();
        }

        db.close();
    }

    /**
     * @return
     */
    public boolean isWordFound(String word) {

        SQLiteDatabase db = getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_NAME + " WHERE word = '" + word + "'";
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            return true;
        }
        return false;
    }
}
