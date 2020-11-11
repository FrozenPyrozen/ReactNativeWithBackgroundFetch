package com.reactnativewithnativemoduletest;
import android.util.Log;

import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import java.util.Map;
import java.util.Date;
import java.util.HashMap;
import android.widget.Toast;


public class CalendarManager extends ReactContextBaseJavaModule {
  private static ReactApplicationContext reactContext;

  CalendarManager(ReactApplicationContext context) {
    super(context);
    reactContext = context;
  }

  @Override
  public String getName() {
    return "CalendarManager";
  }

  @ReactMethod
  public void addEvent(String name, String location, int date) {
    Date dateFromJs =  new Date(date);
    String message = String.format("Pretending to create an event %s at %s, date %s", name, location, dateFromJs); 
    Log.v("CalendarManager", message);
    Log.v("CalendarManager", "Hi Log");
    Log.d("CalendarManager", "Hi Log");
    Log.e("CalendarManager", "Hi Log");
    Log.i("CalendarManager", "Hi Log");
    Log.w("CalendarManager", "Hi Log");

    Toast.makeText(getReactApplicationContext(), message, 1).show();


  }
}