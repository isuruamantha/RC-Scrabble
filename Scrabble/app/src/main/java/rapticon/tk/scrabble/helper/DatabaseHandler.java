package rapticon.tk.scrabble.helper;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import rapticon.tk.scrabble.model.DataModel;
import rapticon.tk.scrabble.service.SharedPreference;

/**
 * Created by Isuru Amantha on 10/10/2017.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "wordsManager";
    // Contacts table name
    private static final String TABLE_NAME = "words";
    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "value";
    private Activity mActivity;

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mActivity = (Activity) context;
    }

    public DatabaseHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        // Create tables again
        onCreate(db);
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

    // Getting All Contacts
    public List<DataModel> getAllValues() {
        List<DataModel> contactList = new ArrayList<DataModel>();
        String selectQuery = "SELECT  * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                DataModel dataModel = new DataModel();
                dataModel.setValue((cursor.getString(0)));
                contactList.add(dataModel);
            } while (cursor.moveToNext());
        }

        // return contact list
        return contactList;
    }
}
