package edu.pacificu.cs493f15_1.utils;

import android.content.Context;

import java.text.SimpleDateFormat;

/**
 * Created by alco8653 on 2/28/2016.
 */
public class Utils
{
  /**
   * Format the date with SimpleDateFormat
   */
  public static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm");
  private Context mContext = null;


  /**
   * Public constructor that takes mContext for later use
   */
  public Utils(Context con)
  {
    mContext = con;
  }

  /**
   * Return true if currentUserEmail equals to shoppingList.owner()
   * Return false otherwise
   */

//  public static boolean checkIfOwner(ShoppingList shoppingList, String currentUserEmail)
//  {
//    return (shoppingList.getOwner() != null && shoppingList.getOwner().equals(currentUserEmail));
//  }


  public static String encodeEmail(String userEmail)
  {
    return userEmail.replace(".", ",");
  }
}
