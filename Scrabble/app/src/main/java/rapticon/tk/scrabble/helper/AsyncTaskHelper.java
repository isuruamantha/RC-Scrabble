package rapticon.tk.scrabble.helper;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;

import java.io.IOException;

import rapticon.tk.scrabble.exception.NoInternetException;
import rapticon.tk.scrabble.exception.ServiceUnreachableException;
import rapticon.tk.scrabble.util.ServiceStatus;


public class AsyncTaskHelper extends AsyncTask<String, Void, Integer> {

    private OnBackgroundTaskListener mOnBackgroundTaskListener;
    private Activity mActivity;
    // private ProgressDialogHelper progressDialogHelper;

    public AsyncTaskHelper(Activity mActivity) {
        this.mActivity = mActivity;
    }

    /**
     * set loading text
     *
     * @param text dialog label
     */
    public void setProgressDialogText(String text) {

        /*if (progressDialogHelper != null) {
            //  progressDialogHelper.setText(text);
        }*/

    }

    /**
     * @param listener
     */
    public void setOnBackgroundListener(OnBackgroundTaskListener listener) {
        this.mOnBackgroundTaskListener = listener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        //  progressDialogHelper.showDialog();
        mOnBackgroundTaskListener.onPreExecute();
    }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
        // progressDialogHelper.closeDialog();

        if (integer == ServiceStatus.OK.getStatus()) {

            mOnBackgroundTaskListener.onPostExecute();

        } else if (integer == ServiceStatus.JSON_EXCEPTION.getStatus()) {
            
        } else if (integer == ServiceStatus.NULL_EX.getStatus()) {

        } else if (integer == ServiceStatus.SERVICE_DOWN.getStatus()) {

        } else if (integer == ServiceStatus.NO_INTERNET.getStatus()) {

        } else {

        }


    }

    @Override
    protected Integer doInBackground(String... strings) {
        try {

            mOnBackgroundTaskListener.onBackground(strings);

            return ServiceStatus.OK.getStatus();
        } catch (JSONException jsonEx) {
            Log.e("JSON_EXCEPTION", jsonEx.toString());
            return ServiceStatus.JSON_EXCEPTION.getStatus();
        } catch (ServiceUnreachableException noServiceEx) {
            Log.e("ServiceException", noServiceEx.toString());
            return ServiceStatus.SERVICE_DOWN.getStatus();
        } catch (NoInternetException nulEx) {
            Log.e("NoInternetException", nulEx.toString());
            return ServiceStatus.NO_INTERNET.getStatus();
        } catch (IOException ioEx) {
            Log.e("IOException", ioEx.toString());
            return ServiceStatus.IO_EXCEPTION.getStatus();
        }
    }

    public interface OnBackgroundTaskListener {

        void onBackground(String... strings) throws IOException, JSONException;

        void onPreExecute();

        void onPostExecute();
    }
}
