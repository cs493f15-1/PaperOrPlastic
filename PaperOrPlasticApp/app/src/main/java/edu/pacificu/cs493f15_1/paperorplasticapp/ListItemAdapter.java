package edu.pacificu.cs493f15_1.paperorplasticapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import edu.pacificu.cs493f15_1.paperorplasticjava.GroceryList;
import edu.pacificu.cs493f15_1.paperorplasticjava.ListItem;

/**
 * Created by sull0678 on 11/5/2015.
 */
public class ListItemAdapter extends BaseAdapter
{
    GroceryList gList;

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return gList.getSize();
    }

    @Override
    public ListItem getItem(int arg0) {
        // TODO Auto-generated method stub
        return gList.getItem(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return arg0;
    }

    @Override
    public View getView(int arg0, View arg1, ViewGroup arg2) {

        if(arg1==null)
        {
            LayoutInflater inflater = (LayoutInflater) arg1.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            arg1 = inflater.inflate(R.layout.grocery_list_item, arg2,false);
        }

        Button itemButton = (Button)arg1.findViewById(R.id.bListItem);

        ListItem item = gList.getItem(arg0);

        itemButton.setText(item.getItemName());

        return arg1;
    }


}
