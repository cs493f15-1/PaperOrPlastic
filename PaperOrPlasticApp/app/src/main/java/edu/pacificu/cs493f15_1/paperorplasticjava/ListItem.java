package edu.pacificu.cs493f15_1.paperorplasticjava;


//import com.sun.org.apache.bcel.internal.generic.POP;

import java.io.PrintWriter;
import java.util.Comparator;
import java.util.Scanner;

/**
 * Created by sull0678 on 10/5/2015.
 */

public class ListItem
{
    /*all but price, quantity, calories and mName are indeces corresponding to the category they are in
    (which will be name in list)*/
    private String itemID;
    private String item_name;
    private String brand_name;
    private String item_type;
    private String item_description;
    private int serv_per_cont;
    private double serv_size_qty;
    private String serv_size_unit;
    private double serv_size_weight;

    private int mFoodType ;
    private int mCustomCategory;
    private double mPrice;
    private int mAisle;
    private int mQuantity;
    private boolean mCheckedOff;
    private String mNotes;
    private String mName;
    private NutritionFactModel mNutritionFactModel;
    private boolean mbShowsDelete;

    public final int MAX_LENGTH = 200; //arbitrary number

    public ListItem ( String name)
    {
        mName = name;
        mQuantity = 1;
        mCheckedOff = false;
        mNutritionFactModel = new NutritionFactModel();
        mNotes = "init";
    }

    public ListItem (String itemId, String itemName, String brandName, String itemType,
                     String itemDescription, int servPerCont, double servSizeQty,
                     String servSizeUnit, double servSizeWeight)
    {
        itemID = itemId;
        item_name = itemName;
        brand_name = brandName;
        item_type = itemType;
        item_description = itemDescription;
        serv_per_cont = servPerCont;
        serv_size_qty = servSizeQty;
        serv_size_unit = servSizeUnit;
        serv_size_weight = servSizeWeight;
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

    public NutritionFactModel getNutritionFacts ()
    {
        return mNutritionFactModel;
    }

    /*******************************
     * SETS
     ******************************/


    public void setShowingDelete (boolean bIsShowingDelete)
    {
        mbShowsDelete = bIsShowingDelete;
    }

    private void setName (String name) {mName = name;}


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
        mNutritionFactModel.mCalories = calories;
        mNutritionFactModel.mTotal_Carbs = carbohydrate;
        mNutritionFactModel.mProtein = protein;
        mNutritionFactModel.mSugars = sugar;
        mNutritionFactModel.mFiber = fiber;
        mNutritionFactModel.mTotal_Fat = fat;
    }

    public void setAll (int foodType, int aisle, int quantityToAdd, double price, int customCategory, boolean checkedOff, String notes, NutritionFactModel NFacts)
    {
        setFoodType (foodType);
        setAisle(aisle);
        setAddQuantity(quantityToAdd);
        setPrice(price);
        setCustomCategory(customCategory);
        setCheckedOff(checkedOff);
        setNotes(notes);
        mNutritionFactModel = NFacts;
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


    /*********************************
     * I/O
     ********************************/

    /********************************************************************************************
     * Function name: writeItemToFile
     *
     * Description: Outputs the current item to the passed in PrintWriter
     *
     * Parameters: itemOutput - the printWriter which the listItem will be outputted to
     *
     * Returns: None
     ******************************************************************************************/
    public void writeItemToFile (PrintWriter itemOutput)
    {
        String content = getFoodType() + " " + getAisle() + " " + getQuantity() + " " + getPrice()
                + " " + getCustomCategory() + " " + getCheckedOff() + " " + getNotes() + " ";

        itemOutput.println(getItemName());
        itemOutput.print(content);
        mNutritionFactModel.writeNutritionToFile(itemOutput);
        itemOutput.print("\n");
        itemOutput.flush();
    }

    /********************************************************************************************
     * Function name: readItemFromFile
     *
     * Description: reads from a file using a scanner and inputs the information into the listItem
     *
     * Parameters: itemInput - the Scanner which the listItem will be read from
     *
     * Returns: None
     ******************************************************************************************/
    public void readItemFromFile (Scanner itemInput)
    {
        itemInput.nextLine(); //get new line character leftover from before
        setName(itemInput.nextLine());
        setFoodType(itemInput.nextInt());
        setAisle(itemInput.nextInt());
        setAddQuantity(itemInput.nextInt());
        setPrice(itemInput.nextDouble());
        setCustomCategory(itemInput.nextInt());
        setCheckedOff(itemInput.nextBoolean());
        setNotes(itemInput.next());
        mNutritionFactModel.readNutritionFromFile(itemInput);
    }

}
