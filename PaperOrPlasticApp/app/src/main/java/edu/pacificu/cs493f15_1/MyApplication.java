package edu.pacificu.cs493f15_1;

import com.firebase.client.Firebase;

/**
 * Created by alco8653 on 12/7/2015.
 */
public class MyApplication extends android.app.Application

{
  @Override
  public void onCreate() {
    super.onCreate();
    Firebase.setAndroidContext(this);
  }
}
