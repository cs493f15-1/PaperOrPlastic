package edu.pacificu.cs493f15_1.paperorplasticjava;

/**
 * Created by alco8653 on 3/14/2016.
 */
public class SimpleListItem
{
  private String mItemName, mOwner, mBoughtBy;
  private boolean bBought;


  public SimpleListItem()
  {}

  public SimpleListItem (String itemName, String owner)
  {
    this.mItemName = itemName;
    this.mOwner = owner;
    this.mBoughtBy = null;
    this.bBought = false;
  }


  public String getmItemName() {return mItemName;}

  public String getmOwner() {return mOwner;}

  public String getmBoughtBy() {return mBoughtBy;}

  public boolean isbBought() {return bBought;}
}
