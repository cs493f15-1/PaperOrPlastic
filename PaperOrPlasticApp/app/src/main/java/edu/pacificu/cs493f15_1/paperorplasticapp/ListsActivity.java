package edu.pacificu.cs493f15_1.paperorplasticapp;
import edu.pacificu.cs493f15_1.paperorplasticjava.*;

import android.app.ActionBar;
import android.app.Activity;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TabHost;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;


/**
 * Created by sull0678 on 10/26/2015.
 */
public class ListsActivity extends FragmentActivity implements ListDFragment.EditNameDialogListener
{
    private Button mbAddList, mbAddItem;
    private ArrayList<TabHost.TabSpec> list = new ArrayList<TabHost.TabSpec>(); /* for later when you want to delete tabs?*/
    GroceryLists mGLists;
    TabHost mListTabHost;
    FragmentManager fm;
    ListView mListView;
    private ArrayList <GroceryListItemAdapter> listAdapters = new ArrayList<GroceryListItemAdapter>();

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

        //init lists
        mGLists = new GroceryLists();

        mListView = (ListView) findViewById(R.id.listView);

        //setup tabs
        mListTabHost = (TabHost) findViewById(R.id.listTabHost);
        mListTabHost.setup();

        //on click listener for buttons (connect to the view)
        mbAddList = (Button) findViewById (R.id.bAddList);
        mbAddList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fm = getSupportFragmentManager();
                ListDFragment listDFragment = new ListDFragment();
                listDFragment.show(fm, "Hi");
            }
        });

        mbAddItem = (Button) findViewById (R.id.bAddItem);
        mbAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItemToListView();
            }
        });


        //For testing purposes
        mGLists.addList("My First List");


        //add all lists in GroceryLists to tabs
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
        spec.setContent(R.id.fragment);
        spec.setIndicator(newList.getListName());
        mListTabHost.addTab(spec);

        //for keeping track of items in list
        addListAdapter(mGLists.getList(index));
    }

    /********************************************************************************************
     * Function name: onFinishEditDialog
     *
     * Description:
     *
     * Parameters:
     *
     * Returns:       none
     ******************************************************************************************/

    @Override
    public void onFinishListDialog(String inputText) {
        //add List to Lists and create a tab
        mGLists.addList(inputText);

        addListTab(mGLists.getList(mGLists.getSize() - 1), mGLists.getSize() - 1);

    }

    /********************************************************************************************
     * Function name: addItem
     *
     * Description:
     *
     * Parameters:
     *
     * Returns:       none
     ******************************************************************************************/
    public void addItemToListView ()
    {
        ListItem item = new ListItem("newItem");
        GroceryList currentList =  getCurrentGList();

        currentList.addItem(item);

        listAdapters.get(mListTabHost.getCurrentTab()).add(item);
    }

    private void addListAdapter (GroceryList gList)
    {
        listAdapters.add (new GroceryListItemAdapter(mListView.getContext(), R.layout.grocery_list_item, gList.getItemArray()));
        GroceryListItemAdapter newAdapter = listAdapters.get(listAdapters.size() - 1);
        mListView.setAdapter ( newAdapter);
    }

    public GroceryList getCurrentGList ()
    {
        return mGLists.getList(mListTabHost.getCurrentTab());
    }
}
