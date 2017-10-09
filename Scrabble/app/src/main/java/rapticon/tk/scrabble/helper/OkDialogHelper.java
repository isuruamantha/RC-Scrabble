package rapticon.tk.scrabble.helper;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rey.material.widget.Button;

import rapticon.tk.scrabble.R;

/**
 * Created by Isuru Amantha on 4/4/2017.
 */

public class OkDialogHelper extends Dialog {

    LinearLayout proceedLinearLayout;
    LinearLayout okLinearLayout;

    private View cancelView;
    private View proceedView;
    private View okView;

    private Button cancelButton;
    private Button proceedButton;
    private Button okButton;

    private Activity mActivity;
    private boolean isConfirmationView;
    private String displayingText;
    private TextView displayTextView;


    public OkDialogHelper(Context context, boolean isConfirmView, String displaText) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.setContentView(R.layout.ok_dialog_helper);

        mActivity = (Activity) context;
        isConfirmationView = isConfirmView;
        displayingText = displaText;

        /*Declare Layouts*/
        proceedLinearLayout = (LinearLayout) findViewById(R.id.proceedLinearLayout);
        okLinearLayout = (LinearLayout) findViewById(R.id.okViewLinearLayout);

        cancelView = findViewById(R.id.cancelButton);
        proceedView = findViewById(R.id.submitButton);
        okView = findViewById(R.id.okayButton);
        displayTextView = (TextView) findViewById(R.id.displayText);

        /*Initiliaze the layouts*/
        intiliazeLayouts();

        /*Set listners*/
        setListners();
    }

    private void intiliazeLayouts() {
        cancelButton = (Button) cancelView.findViewById(R.id.button);
        proceedButton = (Button) proceedView.findViewById(R.id.button);
        okButton = (Button) okView.findViewById(R.id.button);

        /*Set texts*/
//        cancelButton.setText(R.string.cancel);
//        proceedButton.setText(R.string.proceed);
        okButton.setText(R.string.ok);

        /*Set colors to buttons*/


        displayTextView.setText(displayingText);

        if (isConfirmationView == false) {
            proceedLinearLayout.setVisibility(View.GONE);
            okLinearLayout.setVisibility(View.VISIBLE);
        }

    }

    private void setListners() {
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    public Button getCancelButton() {
        return cancelButton;
    }

    public Button getProceedButton() {
        return proceedButton;
    }

    public Button getOkButton() {
        return okButton;
    }
}
