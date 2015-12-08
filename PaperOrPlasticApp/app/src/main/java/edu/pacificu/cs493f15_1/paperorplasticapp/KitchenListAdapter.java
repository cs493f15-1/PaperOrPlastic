package edu.pacificu.cs493f15_1.paperorplasticapp;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

import edu.pacificu.cs493f15_1.paperorplasticjava.KitchenList;

/**
 * Created by sull0678 on 11/23/2015.
 */
public class KitchenListAdapter extends ArrayAdapter<KitchenList>
{

    private ArrayList<KitchenList> mListArray;
    int mLayoutResourceId;
    Context mContext;
    public int mPosition;

    public KitchenListAdapter (Context context, int layoutResourceId, ArrayList<KitchenList> items)
    {
        super (context, layoutResourceId, items);
        this.mLayoutResourceId = layoutResourceId;
        this.mContext = context;
        this.mListArray = items;
        this.setNotifyOnChange(true);
    }


    @Override
    public KitchenList getItem (int position) {
        // TODO Auto-generated method stub
        return mListArray.get (position);
    }


    public ArrayList<KitchenList> getItems ()
    {
        return mListArray;
    }

    @Override
    public void add (KitchenList list) {
        // TODO Auto-generated method stub
        super.add (list);
        //mListArray.add(list);
    }

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

            row.setTag(listHolder);

        }
        else
        {
            listHolder = (ListHolder) row.getTag();
        }

        //set list row info
        KitchenList list = mListArray.get(position);
        listHolder.listName.setText(list.getListName());


        return row;
    }

    static class ListHolder
    {
        TextView listName;
        CheckBox checkBox;
    }

}


