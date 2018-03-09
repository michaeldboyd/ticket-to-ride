package e.mboyd6.tickettoride.Model;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import e.mboyd6.tickettoride.Views.Fragments.BoardFragment;

/**
 * Created by hunte on 3/8/2018.
 */

public class Autoplayer {
    //****** GETTERS & SETTERS *****//
    private static Autoplayer ourInstance = new Autoplayer();

    public static Autoplayer getInstance() {
        return ourInstance;
    }

    public int step = 0;

    public void autoplay(Activity activity, final Context context, BoardFragment boardFragment) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String stepText = "";
                switch(step) {
                    case 0:
                        // PlayerScoreSet
                        stepText = step + " PlayerScoreSet";
                        break;
                    case 1:

                        stepText = step + " PlayerScoreSet";
                        break;
                    case 2:

                        stepText = step + " PlayerScoreSet";
                        break;
                    case 3:

                        stepText = step + " PlayerScoreSet";
                        break;
                    case 4:

                        stepText = step + " PlayerScoreSet";
                        break;
                    case 5:

                        stepText = step + " PlayerScoreSet";
                        step = -1;
                        break;
                }
                Toast.makeText(context, stepText, Toast.LENGTH_SHORT)
                        .show();
                step++;
            }
        });
    }

    public void autoAutoplay(final Activity activity, final Context context, final BoardFragment boardFragment) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i = 0; i < 6; i++) {
                    autoplay(activity, context, boardFragment);
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread.start();
    }

}
