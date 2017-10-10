package rapticon.tk.scrabble.view;


import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.mancj.materialsearchbar.MaterialSearchBar;

import java.util.ArrayList;

import rapticon.tk.scrabble.R;
import rapticon.tk.scrabble.helper.OkDialogHelper;
import rapticon.tk.scrabble.service.SharedPreference;

/**
 * Created by Isuru Amantha on 10/1/2017.
 */

public class WordChecker extends Fragment {

    private MaterialSearchBar materialSearchBar;
    private Activity mActivity;
    private ArrayList<String> wordsList;
    private boolean isFound = false;
    private OkDialogHelper okDialogHelper;

    private Toolbar toolbar;
    private ImageView settingsImageView;
    private Button searchButton;
    private ProgressDialog dialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.word_checker_fragment, container, false);

        defineLayout(view);
        initializeLayout();
        setListeners();

        return view;
    }


    /**
     * To define all the layouts
     */
    private void defineLayout(View view) {
        materialSearchBar = (MaterialSearchBar) view.findViewById(R.id.searchBar);

        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        settingsImageView = (ImageView) toolbar.findViewById(R.id.settings);
        searchButton = (Button) view.findViewById(R.id.search);

    }

    /**
     * To initiliaze Data
     */
    private void initializeLayout() {
        mActivity = getActivity();

        settingsImageView.setVisibility(View.GONE);
        materialSearchBar.setPlaceHolder("Enter your scrabble");
    }

    /**
     * To set the listneres
     */
    private void setListeners() {
        materialSearchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {

            }

            @Override
            public void onSearchConfirmed(CharSequence text) {
                validateWords(text);
            }

            @Override
            public void onButtonClicked(int buttonCode) {

            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateWords(materialSearchBar.getText().toString());
            }
        });

    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Word Checker");
    }

    private void validateWords(CharSequence text) {
        dialog = ProgressDialog.show(mActivity, "", "Loading. Please wait...", true);
        wordsList = SharedPreference.getWordList(mActivity);
        isFound = false;

        for (int i = 0; i < wordsList.size(); i++) {
            if (text.toString().toUpperCase().equals(wordsList.get(i)))
                isFound = true;
        }

        if (isFound) {
            okDialogHelper = new OkDialogHelper(mActivity, true, "Word Found");
            okDialogHelper.show();
            dialog.dismiss();
        } else {
            okDialogHelper = new OkDialogHelper(mActivity, true, "Word Not Found");
            okDialogHelper.show();
            dialog.dismiss();
        }
    }
}
