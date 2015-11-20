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

	/*********************************
	 * I/O
	 ********************************/

	/**
	 * Outputs the current nutrition to the passed in file.
	 * @param NutritionOutput - the file being written to
	 */
	public void writeNutritionToFile (PrintWriter NutritionOutput)
	{
		NutritionOutput.print(mCalories + " " + mProtein + " " + mTotalFat + " " + mCarbohydrates + " " + mSugars + " " + mFiber);
	}


	/**
	 * reads from the file in to the current nutrition.
	 * @param Nutritioninput - the file being read from
	 */
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
