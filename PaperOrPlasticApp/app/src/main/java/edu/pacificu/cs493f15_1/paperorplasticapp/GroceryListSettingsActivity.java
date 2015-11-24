package edu.pacificu.cs493f15_1.paperorplasticapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

import edu.pacificu.cs493f15_1.paperorplasticjava.GroceryList;
import edu.pacificu.cs493f15_1.paperorplasticjava.GroceryLists;
import edu.pacificu.cs493f15_1.paperorplasticjava.ListItem;


/**
 * Created by jone8832 on 10/26/2015.
 */
public class GroceryListSettingsActivity extends FragmentActivity implements View.OnClickListener
{
    final float SLIDE_RIGHT_ITEM = 5;
    final float SLIDE_LEFT_ITEM = -145;

    private ListView mListOfListView;
    private GroceryLists mGLists;
    private GroceryListAdapter mListAdapter;
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
        setContentView(R.layout.activity_grocery_list_settings);
        mbIsOnEdit = false;

        //mButtonShowGroceryList = (Button) findViewById (R.id.bShowGroceryList);
        //mButtonShowGroceryList.setOnClickListener(this);

        mbEdit = (Button) findViewById (R.id.bEdit);
        mbEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int size = mGLists.getSize();
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
                    intent = new Intent(GroceryListSettingsActivity.this, SettingsActivity.class);
                } else {
                    intent = new Intent(GroceryListSettingsActivity.this, GroceryListActivity.class);
                }

                startActivity(intent);
            }
        });




        //get grocery lists
       // mGLists = .getLists();
        //instead for now
        mGLists = new GroceryLists ();
        /*ListItem item = new ListItem("item");
        mGLists.addList("blagh");
        mGLists.getList(0).addItem(item);*/

        Context context = getApplicationContext();
        File groceryFile = context.getFileStreamPath(GroceryLists.GROCERY_FILE_NAME);

        if (groceryFile.exists()) {
            readGListsFromGroceryFile(mGLists);
        }

        //set up list view
        mListOfListView = (ListView) findViewById(R.id.listViewOfLists);

        mListAdapter = new GroceryListAdapter(mListOfListView.getContext(),
                R.layout.listview_list_row_settings, mGLists.getArrayOfLists());

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
        /*if (mButtonShowGroceryList == view)
        {
            if (ContinueActivity.bGListButtonStatusFromSettings.getVisibility() == View.VISIBLE)
            {
                ContinueActivity.bGListButtonStatusFromSettings.setVisibility(View.GONE);
            }
            else if (ContinueActivity.bGListButtonStatusFromSettings.getVisibility() == View.GONE)
            {
                ContinueActivity.bGListButtonStatusFromSettings.setVisibility(View.VISIBLE);
            }
        }*/
    }

    /********************************************************************************************
     * Function name: readGListsFromGroceryFile
     *
     * Description: Reads from the GROCERY_FILE_NAME the current GroceryLists
     *
     * Parameters: None
     *
     * Returns: None
     ******************************************************************************************/
    private void readGListsFromGroceryFile (GroceryLists gLists)
    {
        FileInputStream groceryInput;
        Scanner listsInput;

        try {
            groceryInput = openFileInput(GroceryLists.GROCERY_FILE_NAME);

            listsInput = new Scanner(groceryInput);
            gLists.readListsFromFile(listsInput);
            listsInput.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /********************************************************************************************
     * Function name: writeGListsToGroceryFile
     *
     * Description: Writes the current mGLists to GROCERY_FILE_NAME to store the information stored in mGLists
     *
     * Parameters: None
     *
     * Returns: None
     ******************************************************************************************/
    private void writeGListsToGroceryFile ()
    {
        FileOutputStream groceryOutput = null;
        PrintWriter listsOutput = null;

        try
        {
            groceryOutput = openFileOutput(GroceryLists.GROCERY_FILE_NAME, Context.MODE_PRIVATE);

            listsOutput = new PrintWriter(groceryOutput);

            mGLists.writeListsToFile(listsOutput);
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
                    DeleteListDFragment deleteListFragment = new DeleteListDFragment();
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
     * Description:   When the activity is resumed reads in GroceryLists from GROCERY_FILE_NAME and
     *                updates mGLists with the information.
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
        File groceryFile = context.getFileStreamPath(GroceryLists.GROCERY_FILE_NAME);

        if (groceryFile.exists()) {
            readGListsFromGroceryFile(mGLists);
        }
    }

    /********************************************************************************************
     * Function name: onPause
     *
     * Description:   When the activity is paused writes the GroceryLists to groceryList.txt
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

    }

    /********************************************************************************************
     * Function name: deleteList
     *
     * Description:   When the activity is paused writes the GroceryLists to groceryList.txt
     *
     * Parameters:    none
     *
     * Returns:       none
     ******************************************************************************************/
     public void deleteList ()
     {
        mGLists.deleteList(position);
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
