package edu.pacificu.cs493f15_1.paperorplasticapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by jone8832 on 10/26/2015.
 */
public class KitchenListSettingsActivity extends Activity implements View.OnClickListener
{
    private Button mButtonShowKitchenList;

    @Override
    protected void onCreate (Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kitchen_list_settings);

        mButtonShowKitchenList = (Button) findViewById (R.id.bShowKitchenList);
        mButtonShowKitchenList.setOnClickListener(this);
    }

    public void onClick (View view)
    {
        if (mButtonShowKitchenList == view)
        {
            if (ContinueActivity.bKListButtonStatusFromSettings.getVisibility() == View.VISIBLE)
            {
                ContinueActivity.bKListButtonStatusFromSettings.setVisibility(View.GONE);
            }
            else if (ContinueActivity.bKListButtonStatusFromSettings.getVisibility() == View.GONE)
            {
                ContinueActivity.bKListButtonStatusFromSettings.setVisibility(View.VISIBLE);
            }
        }


    }
}
