package edu.pacificu.cs493f15_1.paperorplasticjava;

/**
 * Created by heyd5159 on 2/6/2016.
 */

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public abstract class PoPLists
{
  ArrayList<PoPList> mLists;

  /*********************************
   * Printing
   *********************************/

  public void printListNames()
  {
    for (PoPList tempList : mLists)
    {
      tempList.printListName();
    }
  }

  /*********************************
   * Gets
   ********************************/

  public String getListName(int listIndex)
  {
    return mLists.get(listIndex).getListName();
  }

  public PoPList getList(int listIndex)
  {

    PoPList list = null;
    if (mLists.size() > listIndex)
    {
      list = mLists.get(listIndex);
    }
    return list;

  }

  public PoPList getListByName(String listName)
  {
    PoPList list = null;

    for (int i = 0; i < mLists.size() && list == null; ++i)
    {
      if (mLists.get(i).getListName().equals(listName))
      {
        list = mLists.get(i);
      }
    }

    return list;

  }


  public int getSize()
  {
    return mLists.size();
  }

  public ArrayList<PoPList> getArrayOfLists()
  {
    return mLists;
  }

  /**********************************
   * Sets
   *********************************/

  public void setListName(int listIndex, String newListName)
  {
    mLists.get(listIndex).setListName(newListName);
  }

  /*********************************
   * Exists
   ********************************/

  /**
   * ListNameExists
   * <p/>
   * Checks if the list names exists in the list. Captilization does not matter.
   *
   * @param listName - the name of the list being checked
   * @return whether the list name is in the list already
   */
  public boolean ListNameExists(String listName)
  {
    boolean bExists = false;
    int i;
    PoPList tempList;

    for (i = 0; i < getSize() && !bExists; ++i)
    {
      tempList = getList(i);
      bExists = (tempList.getListName().toUpperCase().contains(listName.toUpperCase()));
    }

    return bExists;
  }

  /*********************************
   * Adding
   ********************************/

  public abstract void addList(String listName);

  /*********************************
   * Deletes
   ********************************/
  public void deleteList(int listIndex)
  {
    mLists.remove(listIndex);
  }

  /*********************************
   * I/O
   ********************************/

  /********************************************************************************************
   * Function name: writeListsToFile
   * <p/>
   * Description: Outputs the current mLists to the passed in PrintWriter
   * <p/>
   * Parameters: listsOutput - the printWriter which the PoPLists will be outputted to
   * <p/>
   * Returns: None
   ******************************************************************************************/
  public void writeListsToFile(PrintWriter listsOutput)
  {
    listsOutput.println(mLists.size());

    for (PoPList glist : mLists)
    {
      glist.getListName();
      glist.writeListToFile(listsOutput);
      listsOutput.flush();
    }
  }

  /********************************************************************************************
   * Function name: readListsFromFile
   * <p/>
   * Description: reads from a file using a scanner and inputs the information into mLists
   * <p/>
   * Parameters: listsInput - the Scanner which the PoPLists will be read from
   * <p/>
   * Returns: None
   ******************************************************************************************/
  public void readListsFromFile(Scanner listsInput)
  {
    int size;
    PoPList tempList;

    size = listsInput.nextInt();

    for (int i = 0; i < size; ++i)
    {
      addList("temp");
      tempList = getList(i);
      tempList.readListFromFile(listsInput);
    }
  }

  public void clearLists()
  {
    for (int i = 0; i < mLists.size(); i++)
    {
      mLists.remove(i);
    }

  }
}
