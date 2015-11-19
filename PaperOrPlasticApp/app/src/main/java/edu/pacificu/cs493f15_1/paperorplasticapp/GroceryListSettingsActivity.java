package edu.pacificu.cs493f15_1.paperorplasticapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;



/**
 * Created by jone8832 on 10/26/2015.
 */
public class GroceryListSettingsActivity extends Activity implements View.OnClickListener
{

    private Button mButtonShowGroceryList;

    @Override
    protected void onCreate (Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocery_list_settings);

        mButtonShowGroceryList = (Button) findViewById (R.id.bShowGroceryList);
        mButtonShowGroceryList.setOnClickListener(this);
    }

    public void onClick (View view)
    {
        if (mButtonShowGroceryList == view)
        {
            if (ContinueActivity.bGListButtonStatusFromSettings.getVisibility() == View.VISIBLE)
            {
                ContinueActivity.bGListButtonStatusFromSettings.setVisibility(View.GONE);
            }
            else if (ContinueActivity.bGListButtonStatusFromSettings.getVisibility() == View.GONE)
            {
                ContinueActivity.bGListButtonStatusFromSettings.setVisibility(View.VISIBLE);
            }
        }
    }
}
