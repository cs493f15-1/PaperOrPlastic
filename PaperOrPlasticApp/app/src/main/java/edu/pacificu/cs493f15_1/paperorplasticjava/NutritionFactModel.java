package edu.pacificu.cs493f15_1.paperorplasticjava;

import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Created by jo9026 on 10/22/2015.
 */
public class NutritionFactModel
{
	/******************************
	 * Nutrition Data Model for MVC
	 ******************************/

	int mCalories;
	int mCaloriesFat;
	int mTotal_Fat;
	int mSat_Fat;
	int mTotal_Carbs;
	int mProtein;
	int mSugars;
	int mFiber;
	int mSodium;

	public NutritionFactModel()
	{
	}

	public void setAll (int calories, int protein, int fat, int carbohydrate, int sugar, int fiber)
	{
		mCalories = calories;
		mTotal_Carbs = carbohydrate;
		mProtein = protein;
		mSugars = sugar;
		mFiber = fiber;
		mTotal_Fat = fat;
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
		NutritionOutput.print(mCalories + " " + mProtein + " " + mTotal_Fat + " " + mTotal_Carbs + " " + mSugars + " " + mFiber);
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
		mTotal_Carbs = Nutritioninput.nextInt();
		mProtein = Nutritioninput.nextInt();
		mSugars = Nutritioninput.nextInt();
		mFiber = Nutritioninput.nextInt();
		mTotal_Fat = Nutritioninput.nextInt();
	}
}
