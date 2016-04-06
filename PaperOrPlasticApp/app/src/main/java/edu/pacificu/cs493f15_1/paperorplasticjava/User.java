package edu.pacificu.cs493f15_1.paperorplasticjava;

import java.util.HashMap;

/**
 * General user... simpler version of Firebase user
 *
 * Created by alco8653 on 2/28/2016.
 */
public class User
{
  private String mName, mEmail;
  private HashMap<String, Object> mTimestampJoined;
  private boolean bLoggedInPassword;

  /**
   * Required empty public constructor
   */

  public User()
  {
  }

  public User(String name, String email, HashMap<String, Object> timestampJoined)
  {
    this.mName = name;
    this.mEmail = email;
    this.mTimestampJoined = timestampJoined;
    this.bLoggedInPassword = false;
  }

  public String getmName() {
    return mName;
  }

  public String getmEmail() {
    return mEmail;
  }

  public HashMap<String, Object> getmTimestampJoined() {
    return mTimestampJoined;
  }

  public boolean isbLoggedInPassword() {
    return bLoggedInPassword;
  }
}
