package edu.pacificu.cs493f15_1.paperorplasticapp.POPList;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;

import java.util.ArrayList;
import java.util.TimerTask;

import edu.pacificu.cs493f15_1.paperorplasticapp.R;
import edu.pacificu.cs493f15_1.paperorplasticjava.ListItem;

/**
 * Created by sull0678 on 11/5/2015.
 */
public class ListItemAdapter extends ArrayAdapter<ListItem>
{
    private ArrayList<ListItem> mItemArray;
    int mLayoutResourceId;
    Context mContext;
    public int mPosition;
    final Handler mHandlerUI = new Handler(); //for waiting if needed


    private int nCounter;

    public ListItemAdapter (Context context, int layoutResourceId, ArrayList<ListItem> items)
    {
        super (context, layoutResourceId, items);
        this.mLayoutResourceId = layoutResourceId;
        this.mContext = context;
        this.mItemArray = items;
        this.setNotifyOnChange(true);
    }


    @Override
    public ListItem getItem (int position) {
        // TODO Auto-generated method stub
        return mItemArray.get (position);
    }


    public ArrayList<ListItem> getItems ()
    {
        return mItemArray;
    }

    @Override
    public void add (ListItem item) {
        // TODO Auto-generated method stub
        super.add (item);
        //mItemArray.add(item);
    }


    /*public void replaceItems (ArrayList<ListItem> sortedItems)
    {
        super.clear ();
        super.addAll();
        super.notifyDataSetChanged();

    }*/

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View row = convertView;

        ItemHolder itemHolder = null;

        mPosition = position; //to see which item was clicked

        if(row == null)
        {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            row = inflater.inflate(mLayoutResourceId, parent, false);


            itemHolder = new ItemHolder();
            //get items in row and set them to layout items
            itemHolder.itemButton = (Button)row.findViewById(R.id.bListItem);

            //set up check box functionality
            itemHolder.checkBox = (CheckBox)row.findViewById(R.id.itemCheckBox);
            itemHolder.checkBox.setOnClickListener(new OnCheckListener()
            {
                @Override
                public void onClick (View v)
                {
                    //to wait for a certain amount of time
                    if (!mbWaiting)
                    {
                        mbWaiting = Boolean.TRUE;

                        mTimerTask = new TimerTask()
                        {
                            public void run()
                            {
                                mHandlerUI.post(new Runnable() {
                                    public void run() {
                                        if (mbWaiting)
                                        {
                                            //remove item after certain amount of time?
                                            mItemArray.remove(mPosition);
                                            ListItemAdapter.this.notifyDataSetChanged();
                                            mbWaiting = false;
                                        }
                                    }
                                });
                            }

                        };
                        timer.schedule(mTimerTask, 3000);  //

                    }
                    else
                    {
                       mbWaiting = Boolean.FALSE;
                       mTimerTask.cancel();
                    }
                }

            });

            row.setTag(itemHolder);
        }
        else
        {
            itemHolder = (ItemHolder) row.getTag();
        }

        //set list row info
        ListItem item = mItemArray.get(position);
        itemHolder.itemButton.setText(item.getItemName());


        return row;
    }

    static class ItemHolder
    {
        Button itemButton;
        CheckBox checkBox;
    }


}
