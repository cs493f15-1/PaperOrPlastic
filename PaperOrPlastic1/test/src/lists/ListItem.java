package lists;

import com.sun.org.apache.bcel.internal.generic.POP;

/**
 * Created by sull0678 on 10/5/2015.
 */

public class ListItem extends PoPList
{
    /*all but price, quantity, calories and mName are indeces corresponding to the category they are in
    (which will be name in list)*/
    int mFoodType ;
    int mCustomCategory;
    double mPrice;
    int mAisle;
    int mQuantity;
    String mName;
    NutritionFacts mNutritionFacts;


    public ListItem ( String name)
    {
        mName = name;
        mQuantity = 0;
    }

    /***********************************
     * GETS*
     **********************************/

    public void printAll ()
    {
        System.out.println (mName);
        System.out.println (mFoodType);
        System.out.println (mAisle);
        System.out.println (mQuantity);
        System.out.println (mPrice);
        System.out.println (mCustomCategory);
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

    public NutritionFacts getNutritionFacts ()
    {
        return mNutritionFacts;
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

    public void setNutritionFacts (int calories, int protein, int fat, int carbohydrate, int sugar, int fiber)
    {
        mNutritionFacts.mCalories = calories;
        mNutritionFacts.mCarbohydrates = carbohydrate;
        mNutritionFacts.mProtein = protein;
        mNutritionFacts.mSugars = sugar;
        mNutritionFacts.mFiber = fiber;
        mNutritionFacts.mTotalFat = fat;
    }

    public void setAll (int foodType, int aisle, int quantityToAdd, double price, int customCategory, NutritionFacts NFacts)
    {
        setFoodType (foodType);
        setAisle(aisle);
        setAddQuantity(quantityToAdd);
        setPrice(price);
        setCustomCategory(customCategory);
        mNutritionFacts = NFacts;
    }
}
