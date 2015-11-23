package edu.pacificu.cs493f15_1.paperorplasticapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

import edu.pacificu.cs493f15_1.paperorplasticjava.GroceryList;
import edu.pacificu.cs493f15_1.paperorplasticjava.GroceryLists;
import edu.pacificu.cs493f15_1.paperorplasticjava.ListItem;


/**
 * Created by jone8832 on 10/26/2015.
 */
public class GroceryListSettingsActivity extends Activity implements View.OnClickListener
{
    private ListView mListOfListView;
    private GroceryLists mGLists;
    private GroceryListAdapter mListAdapter;
    //private Button mButtonShowGroceryList;

    @Override
    protected void onCreate (Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocery_list_settings);

        //mButtonShowGroceryList = (Button) findViewById (R.id.bShowGroceryList);
        //mButtonShowGroceryList.setOnClickListener(this);



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
}
