package rapticon.tk.scrabble.service;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import rapticon.tk.scrabble.R;
import rapticon.tk.scrabble.model.TimerModel;


public class SharedPreference {

    private static SharedPreferences sharedPref;
    private static SharedPreferences.Editor editor;

    /**
     * add the data to the sharedpref
     */

    public static void setDataWordList(Activity mActivity, ArrayList<String> wordsList) {
        sharedPref = mActivity.getPreferences(Context.MODE_PRIVATE);
        editor = sharedPref.edit();
        Set<String> set = new HashSet<String>();
        set.addAll(wordsList);
        editor.putStringSet(mActivity.getString(R.string.sharedPref), set);
        editor.commit();
    }

    /**
     * Retreive the data from the sharedpref
     */
    public static ArrayList<String> getWordList(Activity mActivity) {
        Set<String> set = sharedPref.getStringSet(mActivity.getString(R.string.sharedPref), null);
        ArrayList<String> list = new ArrayList<>();
        list.addAll(set);
        return list;
    }

    public static void setDatabaseCreation(Activity mActivity, Boolean isDatabaseCreated) {
        sharedPref = mActivity.getPreferences(Context.MODE_PRIVATE);
        editor = sharedPref.edit();
        editor.putString(mActivity.getString(R.string.database), "created");
        editor.commit();
    }

    @NonNull
    public static Boolean getDatabaseCreation(Activity mActivity) {
        String val = sharedPref.getString(mActivity.getString(R.string.database), "s");
//        return sharedPref.getString(mActivity.getString(R.string.database), "s");
        return true;
    }

    public static void setTimerState(Activity mActivity, TimerModel timer) {
        sharedPref = mActivity.getPreferences(Context.MODE_PRIVATE);
        editor = sharedPref.edit();
        Gson gson = new Gson();
        String json = gson.toJson(timer);
        editor.putString(mActivity.getString(R.string.timerObject), json);
        editor.commit();
    }

    public static TimerModel getTimer(Activity mActivity) {
        sharedPref = mActivity.getPreferences(Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPref.getString(mActivity.getString(R.string.timerObject), null);
        TimerModel timer = gson.fromJson(json, TimerModel.class);
        return timer;
    }
}
