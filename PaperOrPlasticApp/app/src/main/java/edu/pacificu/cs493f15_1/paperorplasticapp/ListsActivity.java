package edu.pacificu.cs493f15_1.paperorplasticapp;
import edu.pacificu.cs493f15_1.paperorplasticjava.*;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TabHost;

import java.util.ArrayList;


/**
 * Created by sull0678 on 10/26/2015.
 */
public class ListsActivity extends Activity implements View.OnClickListener
{
    private Button mButtonAddList;
    private ArrayList<TabHost.TabSpec> list = new ArrayList<TabHost.TabSpec>(); /* for later when you want to delete tabs?*/
    GroceryLists mGLists;
    TabHost mListTabHost;


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
    protected void onCreate (Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_lists);

        //setup tabs
        mListTabHost = (TabHost) findViewById(R.id.listTabHost);
        mListTabHost.setup();

        //on click listener for buttons (connect to the view)
        mButtonAddList = (Button) findViewById (R.id.bAddList);
        mButtonAddList.setOnClickListener(this);

        //For testing purposes
        mGLists = new GroceryLists();
        mGLists.addList("My GList 1");


        //add all lists in GroceryLists to list tabs
        for (int i = 0; i < mGLists.getSize(); i++)
        {
            addListTab(mGLists.getList(i), i);
        }


    }

    /********************************************************************************************
     * Function name: addListTab
     *
     * Description:   Adds a tab to the top of the page corresponding to the newList passed in.
     *
     * Parameters:    newList - a List object whose tab will be added to the top of the page
     *                index   - the index of the newList in the GroceryLists object, also the
     *                          new tab spec id
     *
     * Returns:       none
     ******************************************************************************************/

    private void addListTab (GroceryList newList, int index)
    {
        TabHost.TabSpec spec = mListTabHost.newTabSpec(Integer.toString(index));
        spec.setContent(R.id.linearLayout);
        spec.setIndicator(newList.getListName());
        mListTabHost.addTab (spec);

    }

    /********************************************************************************************
     * Function name: onCLick
     *
     * Description:   To check which buttons were clicked on the list page and call functions
     *                needed for what every was clicked
     *
     * Parameters:    view - what ever view had been clicked
     *
     * Returns:       none
     ******************************************************************************************/

    public void onClick (View view)
    {
        //Intent intent;
        if (mButtonAddList == view)
        {
            //addList to Lists and create a tab
            mGLists.addList ("My NewGList");
            addListTab (mGLists.getList (mGLists.getSize () - 1), mGLists.getSize () - 1);
        }
    }
}
