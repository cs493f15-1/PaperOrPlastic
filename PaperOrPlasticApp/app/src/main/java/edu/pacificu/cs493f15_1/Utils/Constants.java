package edu.pacificu.cs493f15_1.Utils;

import edu.pacificu.cs493f15_1.paperorplasticapp.BuildConfig;

/**
 * Created by alco8653 on 2/16/2016.
 */
public final class Constants
{

  /**
   * Constants related to locations in Firebase, such as the name of the node
   * where active lists are stored (ie "activeLists")
   */
  public static final String FIREBASE_LOCATION_GROCERY_LISTS = "groceryLists";
  public static final String FIREBASE_LOCATION_GROCERY_LIST_ITEMS = "groceryListItems";
  public static final String FIREBASE_LOCATION_KITCHEN_INVENTORY = "kitchenInventory";
  public static final String FIREBASE_LOCATION_KITCHEN_INVENTORY_ITEMS = "kitchenInventoryItems";

  public static final String FIREBASE_LOCATION_USERS = "users";


  /**
   * Constants for Firebase object properties
   */

  public static final String FIREBASE_PROPERTY_BOUGHT = "bBought";
  public static final String FIREBASE_PROPERTY_BOUGHT_BY = "mBoughtBy";
  public static final String FIREBASE_PROPERTY_LIST_NAME = "mListName";
  public static final String FIREBASE_PROPERTY_TIMESTAMP_LAST_CHANGED = "timestampLastChanged";
  public static final String FIREBASE_PROPERTY_TIMESTAMP = "timestamp";
  public static final String FIREBASE_PROPERTY_ITEM_NAME = "mItemName";
  public static final String FIREBASE_PROPERTY_EMAIL = "email";
  public static final String FIREBASE_PROPERTY_USER_HAS_LOGGED_IN_WITH_PASSWORD = "bLoggedInWithPassword";

  /**
   * Constants for Firebase URL
   */
  public static final String FIREBASE_URL = BuildConfig.UNIQUE_FIREBASE_ROOT_URL;

  public static final String FIREBASE_URL_GROCERY_LISTS = FIREBASE_URL + "/" + FIREBASE_LOCATION_GROCERY_LISTS;
  public static final String FIREBASE_URL_GROCERY_LIST_ITEMS = FIREBASE_URL + "/" + FIREBASE_LOCATION_GROCERY_LIST_ITEMS;

  public static final String FIREBASE_URL_KITCHEN_INVENTORY = FIREBASE_URL + "/" + FIREBASE_LOCATION_KITCHEN_INVENTORY;
  public static final String FIREBASE_URL_KITCHEN_INVENTORY_ITEMS = FIREBASE_URL + "/" + FIREBASE_LOCATION_KITCHEN_INVENTORY_ITEMS;

  public static final String FIREBASE_URL_USERS = FIREBASE_URL + "/" + FIREBASE_LOCATION_USERS;

  /**
   * Constants for bundles, extras and shared preferences keys
   */
  public static final String KEY_LIST_NAME = "LIST_NAME";
  public static final String KEY_LAYOUT_RESOURCE = "LAYOUT_RESOURCE";
  public static final String KEY_LIST_ID = "LIST_ID";
  public static final String KEY_SIGNUP_EMAIL = "SIGNUP_EMAIL";
  public static final String KEY_LIST_ITEM_NAME = "ITEM_NAME";
  public static final String KEY_LIST_ITEM_ID = "LIST_ITEM_ID";
  public static final String KEY_PROVIDER = "PROVIDER";
  public static final String KEY_ENCODED_EMAIL = "ENCODED_EMAIL";
  public static final String KEY_LIST_OWNER = "LIST_OWNER";
  public static final String KEY_GOOGLE_EMAIL = "GOOGLE_EMAIL";


  /**
   * Constants for Firebase login
   */
  public static final String PASSWORD_PROVIDER = "password";
  public static final String GOOGLE_PROVIDER = "google";
  public static final String PROVIDER_DATA_DISPLAY_NAME = "displayName";
}
