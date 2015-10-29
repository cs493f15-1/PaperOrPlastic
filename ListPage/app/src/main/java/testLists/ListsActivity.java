package testLists;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.TabHost;
import android.app.TabActivity;
import android.widget.TableRow;



import java.util.ArrayList;

import testLists.lists.GroceryList;
import testLists.lists.GroceryLists;

/**
 * Created by sull0678 on 10/26/2015.
 */
public class ListsActivity extends Activity implements View.OnClickListener
{
    private Button mButtonAddList;
    private ArrayList<Button> bLists;
    private ArrayList<TabHost.TabSpec> list = new ArrayList<TabHost.TabSpec>(); /* for later when you want to delete tabs?*/
    GroceryLists mGLists;
    TabHost mListTabHost;

    @Override
    protected void onCreate (Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_lists);

        mListTabHost = (TabHost) findViewById(R.id.listTabHost);
        mListTabHost.setup();

        //on click listener for buttons (connect to the view)
        mButtonAddList = (Button) findViewById (R.id.bAddList);
        mButtonAddList.setOnClickListener(this);

        mGLists = new GroceryLists();
        mGLists.addList("My GList 1");


        //add all lists in GroceryLists to list tabs
        for (int i = 0; i < mGLists.getSize(); i++)
        {
            addListTab(mGLists.getList(i), i);
        }


    }

    private void addListTab (GroceryList newList, int index)
    {
        TabHost.TabSpec spec = mListTabHost.newTabSpec(Integer.toString(index));
        spec.setContent(R.id.linearLayout);
        spec.setIndicator(newList.getListName());
        mListTabHost.addTab (spec);

    }

    public void onClick (View view)
    {
        //Intent intent;
        if (mButtonAddList == view)
        {
            //newGameDialog ();
            mGLists.addList ("My NewGList");
            addListTab (mGLists.getList (mGLists.getSize () - 1), mGLists.getSize () - 1);
           //add list with popup box

        }
    }
}
