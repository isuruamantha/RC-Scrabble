package rapticon.tk.scrabble.helper;

import android.app.Activity;
import android.content.res.AssetManager;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by Isuru Amantha on 10/2/2017.
 */

public class DataHelper {

    //    To return the data from a text file
    public static ArrayList GetData(Activity activity) {

        String ret = "";
        AssetManager am = activity.getAssets();
        ArrayList<String> wordsList = new ArrayList<>();

        try {
            InputStream inputStream = am.open("words.txt");

            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ((receiveString = bufferedReader.readLine()) != null) {
                    wordsList.add(receiveString);
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

        return wordsList;
    }

}
