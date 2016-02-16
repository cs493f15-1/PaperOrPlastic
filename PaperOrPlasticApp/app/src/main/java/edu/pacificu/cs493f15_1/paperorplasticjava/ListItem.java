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

    public static final int MAX_LENGTH = 200; //arbitrary number

    public ListItem ()
    {}


    public ListItem ( String name)
    {
        mName = name;
        mQuantity = 0;
        mCheckedOff = false;
        mNutritionFacts = new NutritionFacts();
        mNotes = "init";
    }

  public static int getMaxLength()
  {
    return MAX_LENGTH;
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
        System.out.println (mCheckedOff);
        System.out.println (mNotes);
    }

  public int getmFoodType()
  {
    return mFoodType;
  }

  public int getmCustomCategory()
  {
    return mCustomCategory;
  }

  public double getmPrice()
  {
    return mPrice;
  }

  public int getmAisle()
  {
    return mAisle;
  }

  public int getmQuantity()
  {
    return mQuantity;
  }

  public boolean ismCheckedOff()
  {
    return mCheckedOff;
  }

  public String getmNotes()
  {
    return mNotes;
  }

  public String getmName()
  {
    return mName;
  }

  public NutritionFacts getmNutritionFacts()
  {
    return mNutritionFacts;
  }

  public boolean isMbShowsDelete()
  {
    return mbShowsDelete;
  }



  public void setmFoodType(int mFoodType)
  {
    this.mFoodType = mFoodType;
  }

  public void setmCustomCategory(int mCustomCategory)
  {
    this.mCustomCategory = mCustomCategory;
  }

  public void setmPrice(double mPrice)
  {
    this.mPrice = mPrice;
  }

  public void setmAisle(int mAisle)
  {
    this.mAisle = mAisle;
  }

  public void setmQuantity(int mQuantity)
  {
    this.mQuantity = mQuantity;
  }

  public void setmCheckedOff(boolean mCheckedOff)
  {
    this.mCheckedOff = mCheckedOff;
  }

  public void setmNotes(String mNotes)
  {
    if (mNotes.length() <= MAX_LENGTH)
    {
      this.mNotes = mNotes;
    }
    else
    {
      this.mNotes = mNotes.substring(0, MAX_LENGTH);
    }
  }

  public void setmName(String mName)
  {
    this.mName = mName;
  }

  public void setmNutritionFacts(NutritionFacts mNutritionFacts)
  {
    this.mNutritionFacts = mNutritionFacts;
  }

  public void setmNutritionFacts (int calories, int protein, int fat, int carbohydrate, int sugar, int fiber)
  {
    this.mNutritionFacts.mCalories = calories;
    this.mNutritionFacts.mCarbohydrates = carbohydrate;
    this.mNutritionFacts.mProtein = protein;
    this.mNutritionFacts.mSugars = sugar;
    this.mNutritionFacts.mFiber = fiber;
    this.mNutritionFacts.mTotalFat = fat;
  }

  public void setMbShowsDelete(boolean mbShowsDelete)
  {
    this.mbShowsDelete = mbShowsDelete;
  }

  /*******************************
     * SETS
     ******************************/

    public void addQuantity (int quantityToAdd)
    {
      this.mQuantity+=quantityToAdd;
    }

    public void setAll (int foodType, int aisle, int quantityToAdd, double price, int customCategory, boolean checkedOff, String notes, NutritionFacts NFacts)
    {
      setmFoodType(foodType);
      setmAisle(aisle);
      addQuantity(quantityToAdd);
      setmPrice(price);
      setmCustomCategory(customCategory);
      setmCheckedOff(checkedOff);
      setmNotes(notes);
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
                return listitem1.getmName().compareTo(listitem2.getmName());
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
        String content = getmFoodType() + " " + getmAisle() + " " + getmQuantity() + " " + getmPrice()
                + " " + getmCustomCategory() + " " + ismCheckedOff() + " " + getmNotes() + " ";

        itemOutput.println(getmName());
        itemOutput.print(content);
        mNutritionFacts.writeNutritionToFile(itemOutput);
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
        setmName(itemInput.nextLine());
        setmFoodType(itemInput.nextInt());
        setmAisle(itemInput.nextInt());
        setmQuantity(itemInput.nextInt());
        setmPrice(itemInput.nextDouble());
        setmCustomCategory(itemInput.nextInt());
        setmCheckedOff(itemInput.nextBoolean());
        setmNotes(itemInput.next());
        mNutritionFacts.readNutritionFromFile(itemInput);
    }

}
