/**************************************************************************************************
 * File:     NutritionActivity.java
 * Author:   Abigail Jones
 * Date:     10/28/15
 * Class:    Capstone/Software Engineering
 * Project:  PaperOrPlastic Application
 * Purpose:  This activity will be the activity that is opened when the user selects the
 * nutrition button from the continue activity
 ***************************************************************************************************/

package edu.pacificu.cs493f15_1.paperorplasticapp.nutrition;


import edu.pacificu.cs493f15_1.paperorplasticapp.R;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


/***************************************************************************************************
 *   Class:         NutritionActivity
 *   Description:   Creates NutritionActivity class that controls what occurs when the user
 *                  selects the nutrition option from the continue activity.
 *   Parameters:    N/A
 *   Returned:      N/A
 **************************************************************************************************/
public class NutritionActivity extends Activity implements OnClickListener
{
  private Button bScanButton;
  private TextView formatText, contentText;

  /********************************************************************************************
   * Function name: onCreate
   *
   * Description:   Initializes all needed setup for objects in page
   *
   * Parameters:    savedInstanceState  - a bundle object
   *
   * Returns:       none
   ******************************************************************************************/
  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_nutrition);

    bScanButton = (Button) findViewById(R.id.scan_button);
    formatText = (TextView) findViewById(R.id.scan_format);
    contentText = (TextView) findViewById(R.id.scan_content);

    bScanButton.setOnClickListener(this);


  }

  /***********************************************************************************************
   *   Method:      onClick
   *
   *   Description: Called when a click has been captured.
   *
   *   Parameters:  view - the view that has been clicked
   *
   *   Returned:    N/A
   ***********************************************************************************************/
  public void onClick(View view)
  {

    if (view == bScanButton)
    {
      IntentIntegrator scanIntegrator = new IntentIntegrator(this);

      scanIntegrator.initiateScan();

    }

  }

  public void onActivityResult(int requestCode, int resultCode, Intent intent)
  {
    IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);

    if (scanningResult != null)
    {
      String scanContent = scanningResult.getContents();
      String scanFormat = scanningResult.getFormatName();

      formatText.setText("FORMAT: " + scanFormat);
      contentText.setText("CONTENT: " + scanContent);


    }
    else
    {
      Toast toast = Toast.makeText(getApplicationContext(), "No scan data received!",
          Toast.LENGTH_SHORT);
      toast.show();
    }
  }
}