package edu.pacificu.cs493f15_1.paperorplasticapp.POPList;

import android.os.Handler;
import android.view.View;

import java.util.Timer;
import java.util.TimerTask;


/**
 * Created by sull0678 on 11/17/2015.
 */
public class OnCheckListener implements View.OnClickListener
{
    public Boolean mbWaiting;
    public Timer timer = new Timer();
    public TimerTask mTimerTask;
    public Handler mHandlerUI = new Handler();



    public OnCheckListener() {
        super();
        mbWaiting = false;
    }

    @Override
    public void onClick (View v)
    {
    }

}
