package edu.pacificu.cs493f15_1.paperorplasticjava;

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
}
