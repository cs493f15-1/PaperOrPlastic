package edu.pacificu.cs493f15_1.paperorplasticjava;

import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Created by jo9026 on 10/22/2015.
 */
public class NutritionFactModel {
	/******************************
	 * Nutrition Data Model for MVC
	 ******************************/
	int mCalories;
	double mTotalFat;
	double mSatFat;
	double mPolyFat;
	double mMonoFat;
	double mTransFat;
	double mTotalCarbs;
	double mCholesterol;
	double mSodium;
	double mPotassium;
	double mProtein;
	double mSugars;
	double mFiber;
	int mVitA;
	int mVitC;
	int mCalcium;
	int mIron;


  public NutritionFactModel()
  {
  }

	/*********************************
	 * GETTERS
	 ********************************/

	public int getVitC() {
		return mVitC;
	}

	public int getCalcium() {
		return mCalcium;
	}

	public int getCalories() {
		return mCalories;
	}

	public double getCholesterol() {
		return mCholesterol;
	}

	public double getFiber() {
		return mFiber;
	}

	public int getIron() {
		return mIron;
	}

	public double getMonoFat() {
		return mMonoFat;
	}

	public double getPolyFat() {
		return mPolyFat;
	}

	public double getPotassium() {
		return mPotassium;
	}

	public double getProtein() {
		return mProtein;
	}

	public double getSatFat() {
		return mSatFat;
	}

	public double getSodium() {
		return mSodium;
	}

	public double getSugars() {
		return mSugars;
	}

	public double getTotalCarbs() {
		return mTotalCarbs;
	}

	public double getTotalFat() {
		return mTotalFat;
	}

	public double getTransFat() {
		return mTransFat;
	}

	public int getVitA() {
		return mVitA;
	}

	/*********************************
	 * SETTERS
	 ********************************/

	public void setAll (int itemCal, double itemTotalFat,
						double itemSatFat, double itemPolyFat, double itemMonoFat,
						double itemTransFat, double itemCholesterol, double itemSodium,
						double itemCarbs, double itemFiber, double itemSugar,
						double itemProtein, double itemPotassium, int itemVitA,
						int itemVitC, int itemCalcium, int itemIron)
	{
		mCalories = itemCal;
		mTotalFat = itemTotalFat;
		mSatFat = itemSatFat;
		mTotalCarbs = itemCarbs;
		mProtein = itemProtein;
		mSugars = itemSugar;
		mFiber = itemFiber;
		mSodium = itemSodium;
		mPolyFat = itemPolyFat;
		mMonoFat = itemMonoFat;
		mTransFat = itemTransFat;
		mCholesterol = itemCholesterol;
		mPotassium = itemPotassium;
		mVitA = itemVitA;
		mVitC = itemVitC;
		mCalcium = itemCalcium;
		mIron = itemIron;
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
		//All new NF data
		NutritionOutput.print(mCalories + " " + mTotalFat + " " + mSatFat + " " + mTotalCarbs +
				" " + mProtein + " " + mSugars + " " + mFiber + " " + mSodium + " " + mPolyFat +
				" " + mMonoFat + " " + mTransFat + " " + mCholesterol + " " + mPotassium + " " +
				mVitA + " " + mVitC + " " + mCalcium + " " + mIron);
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
	public void readNutritionFromFile (Scanner Nutritioninput) {
		//Fetch new data
		mCalories = Nutritioninput.nextInt();
		mTotalFat = Nutritioninput.nextDouble();
		mSatFat = Nutritioninput.nextDouble();
		mTotalCarbs = Nutritioninput.nextDouble();
		mProtein = Nutritioninput.nextDouble();
		mSugars = Nutritioninput.nextDouble();
		mFiber = Nutritioninput.nextDouble();
		mSodium = Nutritioninput.nextDouble();
		mPolyFat = Nutritioninput.nextDouble();
		mMonoFat = Nutritioninput.nextDouble();
		mTransFat = Nutritioninput.nextDouble();
		mCholesterol = Nutritioninput.nextDouble();
		mPotassium = Nutritioninput.nextDouble();
		mVitA = Nutritioninput.nextInt();
		mVitC = Nutritioninput.nextInt();
		mCalcium = Nutritioninput.nextInt();
		mIron = Nutritioninput.nextInt();

	}
}
