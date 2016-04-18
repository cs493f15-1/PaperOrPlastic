package edu.pacificu.cs493f15_1.Utils;

import android.content.Context;

import java.text.SimpleDateFormat;

import edu.pacificu.cs493f15_1.paperorplasticjava.SimpleList;

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

  public static boolean checkIfOwner(SimpleList simpleList, String currentUserEmail)
  {
    return (simpleList.getmOwner() != null && simpleList.getmOwner().equals(currentUserEmail));
  }


  public static String encodeEmail(String userEmail)
  {
    return userEmail.replace(".", ",");
  }
}
