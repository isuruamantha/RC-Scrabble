package rapticon.tk.scrabble.view;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import cn.iwgang.countdownview.CountdownView;
import cn.iwgang.countdownview.DynamicConfig;
import rapticon.tk.scrabble.R;
import rapticon.tk.scrabble.helper.TimerHelper;
import rapticon.tk.scrabble.service.SharedPreference;

/**
 * Created by Isuru Amantha on 10/1/2017.
 */

public class Timer extends Fragment {

    private Activity mActivity;

    private CountdownView countdownViewUp;
    private CountdownView countdownViewDown;
    private Button startButton;

    private LinearLayout linearLayoutUp;
    private LinearLayout linearLayoutDown;
    private boolean isFirstTime = true;
    private boolean isFirstTimeUpperCountdown = true;
    private long countDownDown = 0;
    private long countDownUp = 0;
    private String activeSide = "down";

    private Toolbar toolbar;
    private ImageView settingsImageView;
    private long initialTimerValue = 1800000;
    private SharedPreference sharedPreference;
    private TimerHelper timerHelper;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.timer_fragment, container, false);

        defineLayout(view);
        initializeLayout();
        setListeners();

        return view;
    }

    /**
     * To define all the layouts
     *
     * @param view
     */
    private void defineLayout(View view) {
        countdownViewUp = (CountdownView) view.findViewById(R.id.countdown1);
        countdownViewDown = (CountdownView) view.findViewById(R.id.countdown2);

        startButton = (Button) view.findViewById(R.id.pauseButton);
        linearLayoutUp = (LinearLayout) view.findViewById(R.id.linerLayoutUp);
        linearLayoutDown = (LinearLayout) view.findViewById(R.id.linerLayoutDown);

        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        settingsImageView = (ImageView) toolbar.findViewById(R.id.settings);

    }

    /**
     * To initiliaze Data
     */
    private void initializeLayout() {
        mActivity = this.getActivity();
        linearLayoutUp.setClickable(false);
        sharedPreference = new SharedPreference();
        timerHelper = TimerHelper.getINSTANCE();

        linearLayoutDown.setClickable(false);
        linearLayoutUp.setClickable(false);
        settingsImageView.setVisibility(View.VISIBLE);

        Resources res = mActivity.getResources();
        final int newColor = res.getColor(R.color.white);
        settingsImageView.setColorFilter(newColor, PorterDuff.Mode.SRC_ATOP);

    }

    /**
     * To set the listneres
     */
    private void setListeners() {

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (startButton.getText().toString().equals("Pause")) {
                    countDownUp = countdownViewUp.getRemainTime();
                    countDownDown = countdownViewDown.getRemainTime();
                    countdownViewUp.pause();
                    countdownViewDown.pause();
                    startButton.setText("Start");
                } else {
                    startButton.setText("Pause");
                    if (isFirstTime) {
                        countdownViewDown.start(initialTimerValue); // Millisecond
                        redTimer(countdownViewDown);
                        linearLayoutDown.setClickable(true);
                        isFirstTime = false;
                        activeSide = "down";
                    } else {
                        if (activeSide.equals("down")) {
                            countdownViewDown.start(countDownDown); // Millisecond
                        } else {
                            countdownViewUp.start(countDownUp); // Millisecond
                        }

                    }

                }
            }
        });

        linearLayoutDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!isFirstTime) {
                    if (isFirstTimeUpperCountdown) {
                        countDownDown = countdownViewDown.getRemainTime();
                        countdownViewDown.stop();
                        countdownViewUp.start(initialTimerValue);
                        linearLayoutDown.setClickable(false);
                        linearLayoutUp.setClickable(true);
                        isFirstTimeUpperCountdown = false;
                        activeSide = "up";
                        redTimer(countdownViewUp);
                        blackTimer(countdownViewDown);
                    } else {
                        countDownDown = countdownViewDown.getRemainTime();
                        countdownViewDown.stop();
                        countdownViewUp.start(countDownUp);
                        linearLayoutDown.setClickable(false);
                        linearLayoutUp.setClickable(true);
                        activeSide = "up";
                        redTimer(countdownViewUp);
                        blackTimer(countdownViewDown);
                    }
                }
            }
        });

        linearLayoutUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countDownUp = countdownViewUp.getRemainTime();
                countdownViewUp.stop();
                countdownViewDown.start(countDownDown);
                linearLayoutDown.setClickable(true);
                linearLayoutUp.setClickable(false);
                activeSide = "down";
                redTimer(countdownViewDown);
                blackTimer(countdownViewUp);
            }
        });

        settingsImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
                builder.setTitle("Set the time in minutes");
                final EditText input = new EditText(mActivity);
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                builder.setView(input);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        initialTimerValue = (Long.parseLong(input.getText().toString().equals("") ? "0" : input.getText().toString())) * 60000;
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
            }
        });
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Timer");

    }

    @Override
    public void onPause() {
        super.onPause();
//        TimerModel timer = new TimerModel();
//        timer.setCountDownDown(countdownViewDown.getRemainTime());
//        timer.setCountDownUp(countdownViewUp.getRemainTime());
//        timer.setActiveSide(activeSide);
//        timer.setFirstTime(false);
//        timerHelper.timerModel = timer;
    }

    @Override
    public void onResume() {
        super.onResume();
//        TimerModel timer = new TimerModel();
//        timer = timerHelper.timerModel;
//        if (timer != null) {
//            isFirstTime = timer.isFirstTime();
//            if (timer.getActiveSide().equals("down")) {
//                linearLayoutDown.setClickable(true);
//                countdownViewDown.start(timer.getCountDownDown()); // Millisecond
//            } else {
//                linearLayoutUp.setClickable(true);
//                countdownViewUp.start(timer.getCountDownUp()); // Millisecond
//            }
//        }

    }

    /**
     * To color the timer into red
     *
     * @param countdownView
     */
    public void redTimer(CountdownView countdownView) {
        DynamicConfig dynamicConfig = new DynamicConfig.Builder().setTimeTextColor(mActivity.getResources().getColor(R.color.red)).build();
        DynamicConfig dynamicConfigf = new DynamicConfig.Builder().setSuffixTextColor(mActivity.getResources().getColor(R.color.red)).build();
        countdownView.dynamicShow(dynamicConfig);
        countdownView.dynamicShow(dynamicConfigf);
    }

    /**
     * To color the timer into black
     *
     * @param countdownView
     */
    public void blackTimer(CountdownView countdownView) {
        DynamicConfig dynamicConfig = new DynamicConfig.Builder().setTimeTextColor(mActivity.getResources().getColor(R.color.black)).build();
        DynamicConfig dynamicConfigf = new DynamicConfig.Builder().setSuffixTextColor(mActivity.getResources().getColor(R.color.black)).build();
        countdownView.dynamicShow(dynamicConfig);
        countdownView.dynamicShow(dynamicConfigf);
    }

}
