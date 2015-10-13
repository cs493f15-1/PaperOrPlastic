package lists;

/**
 * Created by sull0678 on 10/5/2015.
 */

public class ListItem
{

    /*all but price, quantity, calories and mName are indeces corresponding to the category they are in
    (which will be name in list)*/

    int mFoodType ;
    int mCustomCategory;
    double mPrice;
    int mAisle;
    int mCalories;
    int mQuantity;
    String mName;
    /*Need nutritional info?*/

    public ListItem ( String name)
    {

        mName = name;
        mQuantity = 1;
    }

    /***********************************
     * GETS*
     **********************************/

    public int getFoodType ()
    {
        return mFoodType;
    }

    public int getAisle ()
    {
        return mAisle;
    }

    public int getCalories ()
    {
        return mCalories;
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

    /*******************************
     * SETS
     ******************************/

    public void setFoodType (int foodType)
    {
         mFoodType = foodType;
    }

    public void setAisle (int aisle)
    {
        mAisle = aisle;
    }

    public void setCalories (int calories)
    {
        mCalories = calories;
    }

    public void setQuantity (int quantity)
    {
        mQuantity = quantity;
    }

    public void setPrice (double price)
    {
        mPrice = price;
    }

    public void setCustomCategory (int customCategory)
    {
        mCustomCategory = customCategory;
    }



}
