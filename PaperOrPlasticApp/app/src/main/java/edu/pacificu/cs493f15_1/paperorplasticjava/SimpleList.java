package edu.pacificu.cs493f15_1.paperorplasticjava;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.firebase.client.ServerValue;

import java.util.HashMap;

import edu.pacificu.cs493f15_1.utils.Constants;

/**
 * Created by alco8653 on 3/14/2016.
 */
public class SimpleList
{
  String mListName, mOwner;
  private HashMap<String, Object> timestampLastChanged, timestampCreated;


  public SimpleList()
  {}

  public SimpleList(String listname, String owner, HashMap<String, Object>timestampCreated)
  {
    this.mListName = listname;
    this.mOwner = owner;
    this.timestampCreated = timestampCreated;

    HashMap<String, Object> timestampObject = new HashMap<>();
    timestampObject.put(Constants.FIREBASE_PROPERTY_TIMESTAMP, ServerValue.TIMESTAMP);
    this.timestampLastChanged = timestampObject;
  }


  public String getmListName()
  {
    return mListName;
  }

  public String getmOwner()
  {
    return mOwner;
  }

  public HashMap<String, Object> getTimestampLastChanged()
  {
    return timestampLastChanged;
  }

  public HashMap<String, Object> getTimestampCreated()
  {
    return timestampCreated;
  }

  @JsonIgnore
  public long getTimestampLastChangedLong()
  {

    return (long) timestampLastChanged.get(Constants.FIREBASE_PROPERTY_TIMESTAMP_LAST_CHANGED);
  }

  @JsonIgnore
  public long getTimestampCreatedLong()
  {

    return (long) timestampCreated.get(Constants.FIREBASE_PROPERTY_TIMESTAMP);
  }

}
