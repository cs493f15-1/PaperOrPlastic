package edu.pacificu.cs493f15_1.paperorplasticapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by jone8832 on 10/26/2015.
 */
public class CouponsSettingsActivity extends Activity implements View.OnClickListener
{
    private Button mButtonShowCouponsList;

    @Override
    protected void onCreate (Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupons_settings);

        mButtonShowCouponsList = (Button) findViewById (R.id.bShowCouponsList);
        mButtonShowCouponsList.setOnClickListener(this);
    }

    public void onClick (View view)
    {
        if (mButtonShowCouponsList == view)
        {
            if (ContinueActivity.bCouponsButtonStatusFromSettings.getVisibility() == View.VISIBLE)
            {
                ContinueActivity.bCouponsButtonStatusFromSettings.setVisibility(View.GONE);
            }
            else if (ContinueActivity.bCouponsButtonStatusFromSettings.getVisibility() == View.GONE)
            {
                ContinueActivity.bCouponsButtonStatusFromSettings.setVisibility(View.VISIBLE);
            }
        }
    }
}
