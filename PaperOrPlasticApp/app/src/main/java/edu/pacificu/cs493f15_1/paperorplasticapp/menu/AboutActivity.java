/**************************************************************************************************
 *   File:     AboutActivity.java
 *   Author:   Abigail Jones
 *   Date:     10/28/15
 *   Class:    Capstone/Software Engineering
 *   Project:  PaperOrPlastic Application
 *   Purpose:  This activity will be the activity that opens when the about button is pressed
 *             from the continue activity.
 ***************************************************************************************************/

package edu.pacificu.cs493f15_1.paperorplasticapp.menu;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;

import com.nineoldandroids.animation.TypeEvaluator;

import org.w3c.dom.Text;

import java.lang.reflect.Type;

import edu.pacificu.cs493f15_1.paperorplasticapp.BaseActivity;
import edu.pacificu.cs493f15_1.paperorplasticapp.R;

/***************************************************************************************************
 *   Class:         AboutActivity
 *   Description:   Creates AboutActivity that takes user to about activity once about button
 *                  is pressed
 *   Parameters:    N/A
 *   Returned:      N/A
 ***************************************************************************************************/
public class AboutActivity extends BaseActivity
{

    private TextView titleText, departmentText, universityText, yearText, authorsText;

    @Override
    protected void onCreate (Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        Typeface laneUpperFont = Typeface.createFromAsset(getAssets(), "fonts/laneWUnderLine.ttf");
        Typeface laneNarrowFont = Typeface.createFromAsset(getAssets(), "fonts/LANENAR.ttf");

        titleText = (TextView) findViewById(R.id.aboutTitleText);
        departmentText = (TextView) findViewById(R.id.aboutDepartmentText);
        universityText = (TextView) findViewById(R.id.aboutUniversityText);
        yearText = (TextView) findViewById(R.id.aboutYearText);
        authorsText = (TextView) findViewById(R.id.aboutAuthorsText);

        titleText.setTypeface(laneUpperFont, Typeface.BOLD);
        departmentText.setTypeface(laneUpperFont, Typeface.BOLD);
        universityText.setTypeface(laneUpperFont, Typeface.BOLD);
        yearText.setTypeface(laneUpperFont, Typeface.BOLD);

        authorsText.setTypeface(laneNarrowFont, Typeface.BOLD);
    }
}
