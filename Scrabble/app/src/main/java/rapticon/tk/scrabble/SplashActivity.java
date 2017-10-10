package rapticon.tk.scrabble;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;

import java.util.ArrayList;
import java.util.List;

import rapticon.tk.scrabble.helper.DataHelper;
import rapticon.tk.scrabble.helper.DatabaseHandler;
import rapticon.tk.scrabble.model.DataModel;
import rapticon.tk.scrabble.service.SharedPreference;

public class SplashActivity extends FragmentActivity {

    private Activity mActivity;
    private DatabaseHandler db;
    private final int SPLASH_DISPLAY_LENGTH = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_background);

        defineLayout();
        initializeLayout();
        setListeners();

    }

    /**
     * To define all the layouts
     */
    private void defineLayout() {

    }


    /**
     * To initiliaze Data
     */
    private void initializeLayout() {
        mActivity = this;
        saveWordList(mActivity);
//        addToDabase();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                Intent mainIntent = new Intent(mActivity, MainActivity.class);
                startActivity(mainIntent);
                finish();
            }
        }, SPLASH_DISPLAY_LENGTH);

    }

    /**
     * To set the listneres
     */
    private void setListeners() {

    }

    private void saveWordList(Activity mActivity) {
        ArrayList arrayList = DataHelper.GetData(mActivity);
        SharedPreference.setDataWordList(mActivity, arrayList);
        SharedPreference.getWordList(mActivity);
    }

    private void addToDabase() {
        db = new DatabaseHandler(this);
        if (SharedPreference.getDatabaseCreation(mActivity))
            db.addValuesToDatabase(DataHelper.GetData(mActivity));
        List<DataModel> contacts = db.getAllValues();
    }

}
