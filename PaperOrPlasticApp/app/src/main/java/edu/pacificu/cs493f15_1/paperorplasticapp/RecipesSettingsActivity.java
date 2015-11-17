package edu.pacificu.cs493f15_1.paperorplasticapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by jone8832 on 10/26/2015.
 */
public class RecipesSettingsActivity extends Activity implements View.OnClickListener
{
    private Button mButtonShowRecipesList;

    @Override
    protected void onCreate (Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes_settings);

        mButtonShowRecipesList = (Button) findViewById (R.id.bShowRecipesList);
        mButtonShowRecipesList.setOnClickListener(this);
    }

    public void onClick (View view)
    {
        if (mButtonShowRecipesList == view)
        {
            if (ContinueActivity.bRecipesButtonStatusFromSettings.getVisibility() == View.VISIBLE)
            {
                ContinueActivity.bRecipesButtonStatusFromSettings.setVisibility(View.GONE);
            }
            else if (ContinueActivity.bRecipesButtonStatusFromSettings.getVisibility() == View.GONE)
            {
                ContinueActivity.bRecipesButtonStatusFromSettings.setVisibility(View.VISIBLE);
            }
        }
    }
}
