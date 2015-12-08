package edu.pacificu.cs493f15_1.paperorplasticjava;

import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Created by jo9026 on 10/22/2015.
 */
public class NutritionFacts
{
	/* Nutritional Facts */
	// Will most likely add more.
	int mCalories;
	int mProtein;
	int mTotalFat;
	int mCarbohydrates;
	int mSugars;
	int mFiber;

	public NutritionFacts ()
	{
		mCalories = 0;
		mProtein = 0;
		mCarbohydrates = 0;
		mTotalFat = 0;
		mSugars = 0;
		mFiber = 0;
	}

	public void setAll (int calories, int protein, int fat, int carbohydrate, int sugar, int fiber)
	{
		mCalories = calories;
		mCarbohydrates = carbohydrate;
		mProtein = protein;
		mSugars = sugar;
		mFiber = fiber;
		mTotalFat = fat;
	}

	public void setmCalories(int calories)
	{
		mCalories = calories;
	}

	public void setmProtein (int protein)
	{
		mProtein = protein;
	}

	public void setmCarbohydrates (int carbohydrates)
	{
		mCarbohydrates = carbohydrates;
	}

	public void setmSugars (int sugars)
	{
		mSugars = sugars;
	}

	public void setmFiber (int fiber)
	{
		mFiber = fiber;
	}

	public void setmTotalFat (int totalFat)
	{
		mTotalFat = totalFat;
	}

	public int getmCalories ()
	{
		return mCalories;
	}

	public int getmProtein ()
	{
		return mProtein;
	}

	public int getmCarbohydrates ()
	{
		return mCarbohydrates;
	}

	public int getmSugars ()
	{
		return mSugars;
	}

	public int getmFiber ()
	{
		return mFiber;
	}

	public int getmTotalFat ()
	{
		return mTotalFat;
	}

	/*********************************
	 * I/O
	 ********************************/

	/********************************************************************************************
	 * Function name: writeNutritionToFile
	 *
	 * Description: Outputs the current nutritionFacts to the passed in PrintWriter
	 *
	 * Parameters: NutritionOutput - the printWriter which the nutritionFacts will be outputted to
	 *
	 * Returns: None
	 ******************************************************************************************/
	public void writeNutritionToFile (PrintWriter NutritionOutput)
	{
		NutritionOutput.print(mCalories + " " + mProtein + " " + mTotalFat + " " + mCarbohydrates + " " + mSugars + " " + mFiber);
		NutritionOutput.flush();
	}

	/********************************************************************************************
	 * Function name: readNutrtionFromFile
	 *
	 * Description: reads from a file using a scanner and inputs the information into the nutritionFacts
	 *
	 * Parameters: NutritionInput - the Scanner which the nutritionFacts will be read from
	 *
	 * Returns: None
	 ******************************************************************************************/
	public void readNutritionFromFile (Scanner Nutritioninput)
	{
		mCalories = Nutritioninput.nextInt();
		mCarbohydrates = Nutritioninput.nextInt();
		mProtein = Nutritioninput.nextInt();
		mSugars = Nutritioninput.nextInt();
		mFiber = Nutritioninput.nextInt();
		mTotalFat = Nutritioninput.nextInt();
	}
}
