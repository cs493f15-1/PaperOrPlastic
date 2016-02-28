package edu.pacificu.cs493f15_1.paperorplasticapp.ui.grocery;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

import edu.pacificu.cs493f15_1.paperorplasticapp.QtyChangeDialogListener;
import edu.pacificu.cs493f15_1.paperorplasticapp.R;
import edu.pacificu.cs493f15_1.paperorplasticjava.GroceryList;

/**
 * Created by sull0678 on 11/23/2015.
 */
public class GroceryListAdapter extends ArrayAdapter<GroceryList>
{
    private ArrayList<GroceryList> mListArray;
    private ArrayList<QtyChangeDialogListener> mQtyChangeListeners;
    int mLayoutResourceId;
    Context mContext;
    public int mPosition;
    public int mQuantity;

    public GroceryListAdapter (Context context, int layoutResourceId, ArrayList<GroceryList> items)
    {
        super (context, layoutResourceId, items);
        this.mLayoutResourceId = layoutResourceId;
        this.mContext = context;
        this.mListArray = items;
        this.setNotifyOnChange(true);
    }


    /********************************************************************************************
     * Function name: returnItem
     *
     * Description:   returns GroceryList with corresponding index of variable position
     *
     * Parameters:    position
     *
     * Returns:       GroceryList
     ******************************************************************************************/
    @Override
    public GroceryList getItem (int position) {
        // TODO Auto-generated method stub
        return mListArray.get (position);
    }

    /********************************************************************************************
     * Function name: getItems
     *
     * Description:   returns array of GroceryList objects
     *
     * Parameters:    none
     *
     * Returns:       mListArray
     ******************************************************************************************/
    public ArrayList<GroceryList> getItems ()
    {
        return mListArray;
    }

    /********************************************************************************************
     * Function name: add
     *
     * Description:
     *
     * Parameters:    position
     *
     * Returns:       None
     ******************************************************************************************/
    @Override
    public void add (GroceryList list) {
        // TODO Auto-generated method stub
        super.add(list);

        //mListArray.add(list);
    }

    /********************************************************************************************
     * Function name: getView
     *
     * Description:
     *
     * Parameters:    position
     *
     * Returns:       None
     ******************************************************************************************/
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View row = convertView;

        ListHolder listHolder = null;

        mPosition = position; //to see which list was clicked

        if(row == null)
        {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            row = inflater.inflate(mLayoutResourceId, parent, false);



            listHolder = new ListHolder();
            //get items in row and set them to layout items
            listHolder.listName = (TextView)row.findViewById(R.id.listName);



            //get items in row and set them to layout items
            /*listHolder.txtQuantity = (TextView)row.findViewById(R.id.quantityValue);
            // listHolder.txtQuantity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mQtyChangeListeners.add (new QtyChangeDialogListener() {
                        @Override
                        public void onFinishQtyChangeDialog (String inputText) {
                            mQuantity = (Integer.parseInt(inputText));

                        }});

                            FragmentActivity activity = (FragmentActivity)(mContext);
                    FragmentManager fm = activity.getSupportFragmentManager();
                    QuantityChangeDFragment dialog = new QuantityChangeDFragment();
                    dialog.show(fm, "fragment");
                }
            });
            */

           /* mItemInfoListener = new NewItemInfoDialogListener() {
                @Override
                public void onFinishNewItemDialog(String inputText) {
                    ListItem newItem = new ListItem(inputText);

                    addItemToListView(newItem);
                    mLastAddedItemName = inputText;
                }*/

            row.setTag(listHolder);

        }
        else
        {
            listHolder = (ListHolder) row.getTag();
        }

        //set list row info
        GroceryList list = mListArray.get(position);
        listHolder.listName.setText(list.getmListName());


        return row;
    }


    static class ListHolder
    {
        QtyChangeDialogListener mQtyChangeListener;
        TextView listName;
        CheckBox checkBox;
        TextView txtQuantity;
       // Button bMinusQty;
       //Button bAddQty;
        int mQuantity = 1;



    }

}

