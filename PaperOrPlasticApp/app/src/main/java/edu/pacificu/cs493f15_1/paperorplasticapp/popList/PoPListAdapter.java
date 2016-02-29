package edu.pacificu.cs493f15_1.paperorplasticapp.popList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

import edu.pacificu.cs493f15_1.paperorplasticapp.R;
import edu.pacificu.cs493f15_1.paperorplasticjava.PoPList;

/**
 * Updated by heyd5159 on 2/27/2016.
 * Used to show PoPList names and properties in a list (Like in kitchen/grocery list settings)
 */
public abstract class PoPListAdapter extends ArrayAdapter<PoPList>
{
  private ArrayList<PoPList> mListArray;
  private ArrayList<QtyChangeDialogListener> mQtyChangeListeners;
  int mLayoutResourceId;
  Context mContext;
  public int mPosition;

  public PoPListAdapter (Context context, int layoutResourceId, ArrayList<PoPList> items)
  {
    super (context, layoutResourceId, items);
    this.mLayoutResourceId = layoutResourceId;
    this.mContext = context;
    this.mListArray = items;
    this.setNotifyOnChange(true);
  }


  /********************************************************************************************
   * Function name: getItem
   *
   * Description:   returns PoPList with corresponding index of variable position
   *
   * Parameters:    position
   *
   * Returns:       PoPList
   ******************************************************************************************/
  @Override
  public PoPList getItem (int position) {
    // TODO Auto-generated method stub
    return mListArray.get (position);
  }

  /********************************************************************************************
   * Function name: getItems
   *
   * Description:   returns array of PoPList objects
   *
   * Parameters:    none
   *
   * Returns:       mListArray
   ******************************************************************************************/
  public ArrayList<PoPList> getItems ()
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
  public void add (PoPList list) {
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
  public View getView(final int position, View convertView, ViewGroup parent)
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
    PoPList list = mListArray.get(position);
    listHolder.listName.setText(list.getListName());


    return row;
  }


  static class ListHolder
  {
    QtyChangeDialogListener mQtyChangeListener;
    TextView listName;
    CheckBox checkBox;
  }

}