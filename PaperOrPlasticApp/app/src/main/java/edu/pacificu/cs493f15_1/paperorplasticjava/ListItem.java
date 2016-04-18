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
    private String mName;
    private String mBrandName;
    private String mDesc;
    private NutritionFactModel mNFModel;

    private int mFoodType ;
    private int mCustomCategory;
    private double mPrice;
    private int mAisle;
    private int mQuantity;
    private boolean mCheckedOff;
    private String mNotes;

    private boolean mbShowsDelete;

    public final int MAX_LENGTH = 200; //arbitrary number

    public ListItem (String name, String brand, String desc)
    {
        mName = name;
        mBrandName = brand;
        mDesc = desc;
        mQuantity = 1;
        mCheckedOff = false;
        mNFModel = new NutritionFactModel();
        mNotes = "init";
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

    public String getBrandName ()
    {
        return mBrandName;
    }

    public String getDesc ()
    {
        return mDesc;
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

    /*
    Getters - nutrition facts
     */

    public NutritionFactModel getNutritionFacts ()
    {
        return mNFModel;
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

    public int getCalories()
    {
        return mNFModel.getCalories();
    }

    public double getFiber() {
        return  mNFModel.getFiber();
    }

    public double getProtein() {
        return  mNFModel.getProtein();
    }

    public double getSat_Fat() {
        return  mNFModel.getSatFat();
    }

    public double getSodium() {
        return  mNFModel.getSodium();
    }

    public double getSugars() {
        return  mNFModel.getSugars();
    }

    public double getTotal_Carbs() {
        return  mNFModel.getTotalCarbs();
    }

    public double getTotal_Fat() {
        return  mNFModel.getTotalFat();
    }

    public int getVitC() {
        return mNFModel.getVitC();
    }

    public int getCalcium() {
        return mNFModel.getCalcium();
    }

    public double getCholesterol() {
        return mNFModel.getCholesterol();
    }

    public int getIron() {
        return mNFModel.getIron();
    }

    public double getMonoFat() {
        return mNFModel.getMonoFat();
    }

    public double getPolyFat() {
        return mNFModel.getPolyFat();
    }

    public double getPotassium() {
        return mNFModel.getPotassium();
    }

    public double getTransFat() {
        return mNFModel.getTransFat();
    }

    public int getVitA() {
        return mNFModel.getVitA();
    }



    /*******************************
     * SETS
     ******************************/


    public void setShowingDelete (boolean bIsShowingDelete)
    {
        mbShowsDelete = bIsShowingDelete;
    }

    private void setName (String name) {mName = name;}

    private void setBrand (String brand) {mBrandName = brand;}

    private void setDesc (String desc) {mDesc = desc;}

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
      mNotes = notes.substring(0, MAX_LENGTH);
    }

    public void setNutritionFacts (int itemCal, double itemTotalFat,
                                   double itemSatFat, double itemPolyFat, double itemMonoFat,
                                   double itemTransFat, double itemCholesterol, double itemSodium,
                                   double itemCarbs, double itemFiber, double itemSugar,
                                   double itemProtein, double itemPotassium, int itemVitA,
                                   int itemVitC, int itemCalcium, int itemIron)
    {
        mNFModel.setAll(itemCal, itemTotalFat,
        itemSatFat, itemPolyFat, itemMonoFat,
        itemTransFat, itemCholesterol, itemSodium,
        itemCarbs, itemFiber, itemSugar,
        itemProtein, itemPotassium, itemVitA,
        itemVitC, itemCalcium, itemIron);
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
        mNFModel = NFacts;
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
        itemOutput.println(getBrandName());
        itemOutput.println(getDesc());
        itemOutput.print(content);
        mNFModel.writeNutritionToFile(itemOutput);
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
        setBrand(itemInput.nextLine());
        setDesc(itemInput.nextLine());

        setFoodType(itemInput.nextInt());
        setAisle(itemInput.nextInt());
        setAddQuantity(itemInput.nextInt());
        setPrice(itemInput.nextDouble());
        setCustomCategory(itemInput.nextInt());
        setCheckedOff(itemInput.nextBoolean());
        setNotes(itemInput.next());
        mNFModel.readNutritionFromFile(itemInput);
    }

}
