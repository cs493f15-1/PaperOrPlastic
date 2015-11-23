package edu.pacificu.cs493f15_1.paperorplasticjava;


//import com.sun.org.apache.bcel.internal.generic.POP;
import java.util.Comparator;

/**
 * Created by sull0678 on 10/5/2015.
 */

public class ListItem
{
    /*all but price, quantity, calories and mName are indeces corresponding to the category they are in
    (which will be name in list)*/
    private int mFoodType ;
    private int mCustomCategory;
    private double mPrice;
    private int mAisle;
    private int mQuantity;
    private boolean mCheckedOff;
    private String mNotes;
    private String mName;
    private NutritionFacts mNutritionFacts;
    private boolean mbShowsDelete;

    public final int MAX_LENGTH = 200; //arbitrary number

    public ListItem ( String name)
    {
        mName = name;
        mQuantity = 0;
        mCheckedOff = false;
    }

    /***********************************
     * GETS*
     **********************************/

    public boolean isShowingDelete ()
    {
        return mbShowsDelete;
    }

    public void printAll ()
    {
        System.out.println (mName);
        System.out.println (mFoodType);
        System.out.println (mAisle);
        System.out.println (mQuantity);
        System.out.println (mPrice);
        System.out.println (mCustomCategory);
        System.out.println (mCheckedOff);
        System.out.println (mNotes);
    }

    public String getItemName ()
    {
        return mName;
    }

    public int getFoodType ()
    {
        return mFoodType;
    }

    public int getAisle ()
    {
        return mAisle;
    }

    public int getQuantity ()
    {
        return mQuantity;
    }

    public double getPrice ()
    {
        return mPrice;
    }

    public int getCustomCategory ()
    {
        return mCustomCategory;
    }

    public boolean getCheckedOff ()
    {
        return mCheckedOff;
    }

    public String getNotes ()
    {
        return mNotes;
    }

    public NutritionFacts getNutritionFacts ()
    {
        return mNutritionFacts;
    }

    /*******************************
     * SETS
     ******************************/

    public void setShowingDelete (boolean bIsShowingDelete)
    {
        mbShowsDelete = bIsShowingDelete;
    }

    public void setFoodType (int foodType)
    {
         mFoodType = foodType;
    }

    public void setAisle (int aisle)
    {
        mAisle = aisle;
    }

    public void setAddQuantity (int quantityToAdd)
    {
        mQuantity += quantityToAdd;
    }

    public void setPrice (double price)
    {
        mPrice = price;
    }

    public void setCustomCategory (int customCategory)
    {
        mCustomCategory = customCategory;
    }

    public void setCheckedOff (boolean checkedOff)
    {
        mCheckedOff = checkedOff;
    }

    public void setNotes (String notes)
    {
        if (notes.length() <= MAX_LENGTH)
        {
            mNotes = notes;
        }
        else
        {
            mNotes = notes.substring(0, MAX_LENGTH);
        }
    }

    public void setNutritionFacts (int calories, int protein, int fat, int carbohydrate, int sugar, int fiber)
    {
        mNutritionFacts.mCalories = calories;
        mNutritionFacts.mCarbohydrates = carbohydrate;
        mNutritionFacts.mProtein = protein;
        mNutritionFacts.mSugars = sugar;
        mNutritionFacts.mFiber = fiber;
        mNutritionFacts.mTotalFat = fat;
    }

    public void setAll (int foodType, int aisle, int quantityToAdd, double price, int customCategory, boolean checkedOff, String notes, NutritionFacts NFacts)
    {
        setFoodType (foodType);
        setAisle(aisle);
        setAddQuantity(quantityToAdd);
        setPrice(price);
        setCustomCategory(customCategory);
        setCheckedOff(checkedOff);
        setNotes(notes);
        mNutritionFacts = NFacts;
    }

    /*******************************
     * SORTING
     ******************************/

    public static class Comparators
    {
        /**
         * A Comparator<ListItem> that compares two ListItems by their names using the Java string comparison
         */
        public static Comparator<ListItem> NAME = new Comparator<ListItem>()
        {
            @Override
            public int compare(ListItem listitem1, ListItem listitem2)
            {
                return listitem1.getItemName().compareTo(listitem2.getItemName());
            }
        };
    }

}
