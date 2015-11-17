package edu.pacificu.cs493f15_1.paperorplasticapp;
import edu.pacificu.cs493f15_1.paperorplasticjava.*;

import android.app.ActionBar;
import android.app.Activity;

import android.content.Context;
import android.os.SystemClock;
import android.support.v4.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TabHost;
import edu.pacificu.cs493f15_1.paperorplasticjava.ListItem;
import edu.pacificu.cs493f15_1.paperorplasticapp.NewItemInfoDialogListener;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.UUID;


/**
 * Created by sull0678 on 10/26/2015.
 */
public class ListsActivity extends FragmentActivity implements ListDFragment.EditNameDialogListener
{
    private Button mbAddList, mbAddItem;
    private Spinner mGroupBySpinner;
    private ArrayList<TabHost.TabSpec> list = new ArrayList<TabHost.TabSpec>(); /* for later when you want to delete tabs?*/
    private GroceryLists mGLists;
    private TabHost mListTabHost;
    private FragmentManager fm;
    private ListView mListView;
    private ArrayList <GroceryListItemAdapter> listAdapters =
                                        new ArrayList<GroceryListItemAdapter>();

    private NewItemInfoDialogListener mItemInfoListener;

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


        //init my grocery lists
        mGLists = new GroceryLists();

        //to view items
        mListView = (ListView) findViewById(R.id.listView);

        //setup tabs
        mListTabHost = (TabHost) findViewById(R.id.listTabHost);
        mListTabHost.setup();
        mListTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                mListTabHost.setCurrentTab(Integer.parseInt(tabId));
            }
        });


        //on click listener for buttons (connect to the view)

        //add list button
        mbAddList = (Button) findViewById (R.id.bAddList);
        mbAddList.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                fm = getSupportFragmentManager();
                ListDFragment listDFragment = new ListDFragment();
                listDFragment.show(fm, "Hi");
            }
        });

        //add item button
        mbAddItem = (Button) findViewById (R.id.bAddItem);
        mbAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                mItemInfoListener = new NewItemInfoDialogListener()
                {
                    @Override
                    public void onFinishNewItemDialog(String inputText)
                    {
                        ListItem newItem = new ListItem(inputText);
                        addItemToListView(newItem);
                    }

                };
                fm = getSupportFragmentManager();
                NewItemDFragment newItemFragment = new NewItemDFragment();
                newItemFragment.show(fm, "Hi");


            }
        });


        //For testing purposes
        mGLists.addList("My First List");

        //For the Group By Spinner (sorting dropdown)

        mGroupBySpinner = (Spinner)findViewById(R.id.GroupBySpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(ListsActivity.this,
                android.R.layout.simple_spinner_item, PoPList.GroupByStrings);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mGroupBySpinner.setAdapter(adapter);
        mGroupBySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                GroceryList currentList  = getCurrentGList();

                switch (position) {

                    case PoPList.SORT_NONE: // first item in dropdown currently blank
                        currentList.setCurrentSortingValue(PoPList.SORT_NONE);
                        break;
                    case PoPList.SORT_ALPHA: //second item in dropdown currently alphabetical

                        currentList.setCurrentSortingValue(PoPList.SORT_ALPHA);
                        currentList.sortListByName();
                        listAdapters.get(mListTabHost.getCurrentTab()).notifyDataSetChanged();

                        break;
                    case PoPList.SORT_CAL: //calories
                        currentList.setCurrentSortingValue(PoPList.SORT_CAL);
                        break;
                    case PoPList.SORT_DATE: //date entered
                        currentList.setCurrentSortingValue(PoPList.SORT_DATE);
                        break;
                    case PoPList.SORT_AISLE: //aisle
                        currentList.setCurrentSortingValue(PoPList.SORT_AISLE);
                        break;
                    case PoPList.SORT_PRICE: //price
                        currentList.setCurrentSortingValue(PoPList.SORT_PRICE);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                    //Nothing to do if the dropdown is not selected.
            }
        });

        //add all existing lists in GroceryLists to tabs
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
     * Function name: onFinishListDialog
     *
     * Description:   When dialog for adding list is done, add list and list tab with text from
     *                dialog as the new list name
     *
     * Parameters:    newListName - the new list's name
     *
     * Returns:       none
     ******************************************************************************************/

    @Override
    public void onFinishListDialog(String newListName) {
        //add List to Lists and create a tab
        mGLists.addList(newListName);

        addListTab(mGLists.getList(mGLists.getSize() - 1), mGLists.getSize() - 1);

    }

    /********************************************************************************************
     * Function name: addItemToListView
     *
     * Description:   Adds item layout to listView as a new row and adds it to listadapter
     *
     * Parameters:    none
     *
     * Returns:       none
     ******************************************************************************************/

    public void addItemToListView (ListItem newItem)
    {
        listAdapters.get(mListTabHost.getCurrentTab()).add(newItem);
        GroceryList currentList = getCurrentGList();

        switch (currentList.getCurrentSortingValue())
        {
            case PoPList.SORT_ALPHA:    currentList.sortListByName();
                                        listAdapters.get(mListTabHost.getCurrentTab())
                                                .notifyDataSetChanged();
                                        break;
            case PoPList.SORT_AISLE:
            case PoPList.SORT_CAL:
            case PoPList.SORT_DATE:
            case PoPList.SORT_PRICE:
            case PoPList.SORT_NONE:     break;
        }

    }

    /********************************************************************************************
     * Function name: addListAdapter
     *
     * Description:   Adds a list adapter for mListView to keep track of the info in gList
     *
     * Parameters:    gList - the new list whose info needs to be kept track of
     *
     * Returns:       none
     ******************************************************************************************/

    private void addListAdapter (GroceryList gList)
    {
        listAdapters.add(new GroceryListItemAdapter(mListView.getContext(),
                R.layout.grocery_list_item, gList.getItemArray()));
        GroceryListItemAdapter newAdapter = listAdapters.get(listAdapters.size() - 1);
        mListView.setAdapter(newAdapter);
    }

    /********************************************************************************************
     * Function name: getCurrentGList
     *
     * Description:   Gets the GroceryList whose tab we have selected
     *
     * Parameters:    none
     *
     * Returns:       the current list selected
     ******************************************************************************************/

    public GroceryList getCurrentGList ()
    {
        return mGLists.getList(mListTabHost.getCurrentTab());
    }


    /********************************************************************************************
     * Function name: NewItemInfoDialogListener
     *
     * Description:   Returns the newItemInfoDialogListener
     *
     * Parameters:    none
     *
     * Returns:       mItemInfoListener
     ******************************************************************************************/
    //not sure if this is needed
    public NewItemInfoDialogListener getItemInfoListener ()
    {
        return mItemInfoListener;
    }


}
