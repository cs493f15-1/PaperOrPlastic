package edu.pacificu.cs493f15_1.paperorplasticapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by jone8832 on 10/26/2015.
 */
public class NutritionSettingsActivity extends Activity implements View.OnClickListener
{
    private Button mButtonShowNutritionList;

    @Override
    protected void onCreate (Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nutrition_settings);

        mButtonShowNutritionList = (Button) findViewById (R.id.bShowNutritionList);
        mButtonShowNutritionList.setOnClickListener(this);
    }

    public void onClick (View view)
    {
        if (mButtonShowNutritionList == view)
        {
            if (ContinueActivity.bNutritionButtonStatusFromSettings.getVisibility() == View.VISIBLE)
            {
                ContinueActivity.bNutritionButtonStatusFromSettings.setVisibility(View.GONE);
            }
            else if (ContinueActivity.bNutritionButtonStatusFromSettings.getVisibility() == View.GONE)
            {
                ContinueActivity.bNutritionButtonStatusFromSettings.setVisibility(View.VISIBLE);
            }
        }
    }
}
