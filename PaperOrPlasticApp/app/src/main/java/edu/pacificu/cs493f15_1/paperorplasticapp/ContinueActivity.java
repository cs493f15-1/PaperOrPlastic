package edu.pacificu.cs493f15_1.paperorplasticapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by jone8832 on 10/26/2015.
 */
public class ContinueActivity extends Activity implements View.OnClickListener
{
    //buttons
    private Button  mButtonLists,
                    mButtonSettings,
                    mButtonAbout;
    @Override
    protected void onCreate (Bundle savedInstanceState)
    {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_continue);

        //on click listener for buttons (connect to the view)
        mButtonLists = (Button) findViewById (R.id.bContLists);
        mButtonLists.setOnClickListener (this);
        mButtonSettings = (Button) findViewById (R.id.bContSettings);
        mButtonSettings.setOnClickListener (this);
        mButtonAbout = (Button) findViewById (R.id.bContAbout);
        mButtonAbout.setOnClickListener (this);
    }


    public void onClick (View view)
    {
        Intent intent;

        if (mButtonLists == view)
        {
           //will start a new activity using the intents
            intent = new Intent (this, ListsActivity.class);
           startActivity (intent);
        }

        if (mButtonSettings == view)
        {
            //will start a new activity using the intents
            intent = new Intent (this, SettingsActivity.class);
            startActivity (intent);
        }
        if (mButtonAbout == view)
        {
            //will start a new activity using the intents
            intent = new Intent (this, AboutActivity.class);
            startActivity (intent);

        }
    }
}

