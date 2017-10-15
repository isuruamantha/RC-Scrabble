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
import rapticon.tk.scrabble.helper.DatabaseHandler;
import rapticon.tk.scrabble.helper.OkDialogHelper;

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
    private DatabaseHandler databaseHandler;

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
        databaseHandler = new DatabaseHandler(mActivity);

        settingsImageView.setVisibility(View.GONE);
        materialSearchBar.setPlaceHolder("Enter your word");
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
        isFound = false;

        isFound = databaseHandler.isWordFound(text.toString().toUpperCase());

        if (isFound) {
            okDialogHelper = new OkDialogHelper(mActivity, true, "Acceptable");
            okDialogHelper.show();
            dialog.dismiss();
        } else {
            okDialogHelper = new OkDialogHelper(mActivity, true, "Unacceptable");
            okDialogHelper.show();
            dialog.dismiss();
        }
    }
}
