/**************************************************************************************************
 *   File:     KitchenListSettingsActivity.java
 *   Author:   Abigail Jones
 *   Date:     10/28/15
 *   Class:    Capstone/Software Engineering
 *   Project:  PaperOrPlastic Application
 *   Purpose:  This activity will be the activity that is opened when the user chooses to
 *             look at the ktichen list settings. This can happen through the settings button on the
 *             continue activity or through the settings tab when the ktichen list button is pressed
 *             from the continue activity.
 ***************************************************************************************************/

package edu.pacificu.cs493f15_1.paperorplasticapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

import edu.pacificu.cs493f15_1.paperorplasticjava.KitchenLists;



/***************************************************************************************************
 *   Class:         KitchenListSettingsActivity
 *   Description:   Creates KitchenListSettingsActivity class that controls what occurs when the
 *                  user reaches the kitchen list settings page. Specifically, handles what happens
 *                  when the user specifies whether the kitchen list button should be displayed on
 *                  the continue activity.
 *                  creates intents that take users to those specific pages.
 *   Parameters:    N/A
 *   Returned:      N/A
 **************************************************************************************************/

public class KitchenListSettingsActivity extends FragmentActivity implements View.OnClickListener
{
    final float SLIDE_RIGHT_ITEM = 5;
    final float SLIDE_LEFT_ITEM = -145;

    private ListView mListOfListView;
    private KitchenLists mKLists;
    private KitchenListAdapter mListAdapter;
    private DeleteListDialogListener mDeleteListListener;
    private Button mbBack;
    private Button mbEdit;
    private boolean mbIsOnEdit;
    private FragmentManager fm;
    int position = 0;
    Button delete;
    //private Button mButtonShowGroceryList;

    @Override
    protected void onCreate (Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kitchen_list_settings);
        mbIsOnEdit = false;

        mbEdit = (Button) findViewById (R.id.bEdit);
        mbEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int size = mKLists.getSize();
                if (size > 0) {
                    if (!mbIsOnEdit) {
                        mbIsOnEdit = true;
                        for (int i = 0; i < size; i++) {
                            showDeleteButton(i);
                        }
                    } else {

                        //showDeleteButton also gets rid of the delete button so we might not need this check
                        //TODO might need to show again if tab is changed
                        mbIsOnEdit = false;
                        for (int i = 0; i < size; i++) {
                            hideDeleteButton(i);
                        }
                    }
                }
            }
        });

        /*Do we need a back button?*/
        mbBack = (Button) findViewById (R.id.bBack);
        mbBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //go back to activity that called this page (possible pages are settings
                // or grocery list page
                String caller = getIntent().getStringExtra("Caller");
                Intent intent;
                if (caller.equals("SettingsActivity")) {
                    intent = new Intent(KitchenListSettingsActivity.this, SettingsActivity.class);
                } else {
                    intent = new Intent(KitchenListSettingsActivity.this, KitchenListActivity.class);
                }

                startActivity(intent);
            }
        });

        //set up list view
        mListOfListView = (ListView) findViewById(R.id.listViewOfLists);

        mListAdapter = new KitchenListAdapter(mListOfListView.getContext(),
                R.layout.listview_list_row_settings, mKLists.getArrayOfLists());

        mListOfListView.setAdapter(mListAdapter);


        mListOfListView.setOnTouchListener(new OnSwipeTouchListener(this, mListOfListView) {
            @Override
            public void onSwipeRight(int pos) {
                //Toast.makeText (GroceryListActivity.this, "right", Toast.LENGTH_LONG).show();

                if (!mbIsOnEdit) {
                    hideDeleteButton(pos);
                }

            }

            @Override
            public void onSwipeLeft(int pos) {
                //Toast.makeText (GroceryListActivity.this, "left", Toast.LENGTH_LONG).show();
                if (!mbIsOnEdit) {
                    showDeleteButton(pos);
                }
            }
        });

    }

    public void onClick (View view)
    {
    }

    /********************************************************************************************
     * Function name: readKListsFromKitchenFile
     *
     * Description: Reads from the KITCHEN_FILE_NAME the current KitchenLists
     *
     * Parameters: kLists - the KitchenLists the file should be read into
     *
     * Returns: None
     ******************************************************************************************/
    private void readKListsFromKitchenFile (KitchenLists kLists)
    {
        FileInputStream kitchenInput;
        Scanner listsInput;

        try {
            kitchenInput = openFileInput(KitchenLists.KITCHEN_FILE_NAME);

            listsInput = new Scanner(kitchenInput);
            kLists.readListsFromFile(listsInput);
            listsInput.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < kLists.getSize(); ++i) {
           Log.d("List" + i, kLists.getKListName(i));
        }
    }

    /********************************************************************************************
     * Function name: writeKListsToKitchenFile
     *
     * Description: Writes the current mGLists to KITCHEN_FILE_NAME to store the information stored in mKLists
     *
     * Parameters: None
     *
     * Returns: None
     ******************************************************************************************/
    private void writeGListsToGroceryFile ()
    {
        FileOutputStream kitchenOutput = null;
        PrintWriter listsOutput = null;

        try
        {
            //clear file
            kitchenOutput = openFileOutput(KitchenLists.KITCHEN_FILE_NAME, Context.MODE_PRIVATE);

            listsOutput = new PrintWriter(kitchenOutput);

            mKLists.writeListsToFile(listsOutput);
            listsOutput.flush();
            listsOutput.close();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /********************************************************************************************
     * Function name: showDeleteButton
     *
     * Description:
     *
     * Parameters:
     *
     * Returns:
     ******************************************************************************************/
    private boolean showDeleteButton(final int pos) {
        position = pos;
        View child = mListOfListView.getChildAt(pos - mListOfListView.getFirstVisiblePosition());
        if (child != null) {

            delete = (Button) child.findViewById(R.id.bDelete);
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    mDeleteListListener = new DeleteListDialogListener()
                    {
                        @Override
                        public void onDeleted ()
                        {
                            deleteList();
                        }
                    };
                    fm = getSupportFragmentManager();
                    DeleteKitchenListDFragment deleteListFragment = new DeleteKitchenListDFragment();
                    deleteListFragment.show(fm, "Yeah");

                }
            });
            if (delete != null)
            {
                if (delete.getVisibility() == View.INVISIBLE) {
                    Animation deleteAnimation =
                            AnimationUtils.loadAnimation(this,
                                    R.anim.slide_out_left);

                    delete.startAnimation(deleteAnimation);
                    delete.setVisibility(View.VISIBLE);

                    slideItemView(child, SLIDE_LEFT_ITEM);
                }
            }
            return true;
        }
        return false;
    }

    /********************************************************************************************
     * Function name: hideDeleteButton
     *
     * Description:
     *
     * Parameters:
     *
     * Returns:
     ******************************************************************************************/
    private boolean hideDeleteButton(final int pos) {
        position = pos;
        View child = mListOfListView.getChildAt(pos - mListOfListView.getFirstVisiblePosition());
        if (child != null) {

            delete = (Button) child.findViewById(R.id.bDelete);
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                }
            });
            if (delete != null)
            {
                if (delete.getVisibility() == View.VISIBLE) {
                    Animation deleteAnimation =
                            AnimationUtils.loadAnimation(this,
                                    R.anim.slide_in_right);

                    delete.startAnimation(deleteAnimation);

                    delete.setVisibility(View.INVISIBLE);

                    slideItemView(child, SLIDE_RIGHT_ITEM);

                }
            }
            return true;
        }
        return false;
    }


    private void slideItemView (View child, float translationAmount)
    {
        TextView listName = (TextView) child.findViewById(R.id.listName);
        listName.setTranslationX(translationAmount);

    }


    /********************************************************************************************
     * Function name: onResume
     *
     * Description:   When the activity is resumed reads in KitchenLists from KITCHEN_FILE_NAME and
     *                updates mKLists with the information.
     *
     * Parameters:    none
     *
     * Returns:       none
     ******************************************************************************************/
    @Override
    protected void onResume ()
    {
        super.onResume();

        Context context = getApplicationContext();
        File groceryFile = context.getFileStreamPath(KitchenLists.KITCHEN_FILE_NAME);

        if (groceryFile.exists())
        {
            mKLists.clearLists();
            readKListsFromKitchenFile(mKLists);
        }
    }

    /********************************************************************************************
     * Function name: onPause
     *
     * Description:   When the activity is paused writes the KitchenLists to kitchenList.txt
     *
     * Parameters:    none
     *
     * Returns:       none
     ******************************************************************************************/
    @Override
    protected void onPause ()
    {
        super.onPause();

        writeGListsToGroceryFile();
        mKLists.clearLists();

    }

    /********************************************************************************************
     * Function name: deleteList
     *
     * Description:   When the activity is paused writes the KitchenLists to kitchenList.txt
     *
     * Parameters:    none
     *
     * Returns:       none
     ******************************************************************************************/
    public void deleteList ()
    {
        mKLists.deleteList(position);
        mListAdapter.notifyDataSetChanged();
    }

    /********************************************************************************************
     * Function name:
     *
     * Description:   When the activity is paused writes the GroceryLists to groceryList.txt
     *
     * Parameters:    none
     *
     * Returns:       none
     ******************************************************************************************/
    public DeleteListDialogListener getDeleteDialogListener()
    {
        return mDeleteListListener;
    }

}
