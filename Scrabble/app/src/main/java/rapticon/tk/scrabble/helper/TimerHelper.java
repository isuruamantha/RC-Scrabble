package rapticon.tk.scrabble.helper;

import rapticon.tk.scrabble.model.TimerModel;

/**
 * Created by user on 4/21/2017.
 */

public class TimerHelper {

    private static final TimerHelper INSTANCE = new TimerHelper();

    public TimerModel timerModel;

    private TimerHelper() {
    }

    public static TimerHelper getINSTANCE() {
        return INSTANCE;
    }
}
