package rapticon.tk.scrabble.model;

/**
 * Created by Isuru Amantha on 10/10/2017.
 */

public class TimerModel {

    long countDownDown;
    long countDownUp;
    String activeSide;
    boolean isFirstTime;

    public long getCountDownDown() {
        return countDownDown;
    }

    public void setCountDownDown(long countDownDown) {
        this.countDownDown = countDownDown;
    }

    public long getCountDownUp() {
        return countDownUp;
    }

    public void setCountDownUp(long countDownUp) {
        this.countDownUp = countDownUp;
    }

    public String getActiveSide() {
        return activeSide;
    }

    public void setActiveSide(String activeSide) {
        this.activeSide = activeSide;
    }

    public boolean isFirstTime() {
        return isFirstTime;
    }

    public void setFirstTime(boolean firstTime) {
        isFirstTime = firstTime;
    }
}
