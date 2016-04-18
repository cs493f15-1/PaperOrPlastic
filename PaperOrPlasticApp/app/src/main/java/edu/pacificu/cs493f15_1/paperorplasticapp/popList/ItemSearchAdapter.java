package edu.pacificu.cs493f15_1.paperorplasticapp.popList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;

import java.util.ArrayList;

import edu.pacificu.cs493f15_1.paperorplasticapp.R;
import edu.pacificu.cs493f15_1.paperorplasticapp.nutrition.NutritionActivity;
import edu.pacificu.cs493f15_1.paperorplasticjava.ListItem;

/**
 * Created by jo9026 on 3/8/2016.
 */
public class ItemSearchAdapter extends ArrayAdapter<ListItem> {
	private ArrayList<ListItem> mItemArray;
	int mLayoutResourceId;
	Context mContext;
	ListItem selectedItem;
	private ItemSearchActivity searchActivity;


	public ItemSearchAdapter(Context context, int layoutResourceId, ArrayList<ListItem> items) {
		super(context, layoutResourceId, items);
		this.mLayoutResourceId = layoutResourceId;
		this.mContext = context;
		this.mItemArray = items;
		this.setNotifyOnChange(true);
		this.selectedItem = null;
	}

	@Override
	public ListItem getItem (int position) {
		// TODO Auto-generated method stub
		return mItemArray.get (position);
	}

	@Override
	public void add (ListItem item) {
		// TODO Auto-generated method stub
		super.add(item);
	}

	//Initializes items in suggestions, and sets up onClick
	@Override
	public View getView(final int position, View convertView, ViewGroup parent)
	{
		View row = convertView;
		Button itemButton;

		LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
		row = inflater.inflate(mLayoutResourceId, parent, false);

		itemButton = (Button)row.findViewById(R.id.bListItem);
		ListItem item = mItemArray.get(position);
		itemButton.setText(item.getItemName());

		itemButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				selectedItem = getItem(position);
				searchActivity.sendItemNameToList(selectedItem.getItemName(),
						selectedItem.getBrandName(), selectedItem.getDesc(),
						selectedItem.getNutritionFacts());
			}
		});

		return row;
	}

	public void setActivity(ItemSearchActivity activity) {
		this.searchActivity = activity;
	}
}