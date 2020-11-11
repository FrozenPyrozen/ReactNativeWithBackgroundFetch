package com.reactnativewithnativemoduletest;

import android.content.Intent;
import android.os.Bundle;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import javax.annotation.Nonnull;

public class BackgroungHeadlessJsModule extends ReactContextBaseJavaModule {
  public static final String REACT_CLASS = "BackgroungHeadlessJs";
  private static ReactApplicationContext reactContext;

  public BackgroungHeadlessJsModule(@Nonnull ReactApplicationContext reactContext) {
    super(reactContext);
    this.reactContext = reactContext;
  }
  
  @Nonnull
  @Override
  public String getName() {
    return REACT_CLASS;
  }
  
  @ReactMethod
   public void startService() {
    Intent service = new Intent(getReactApplicationContext(), MyTaskService.class);
    Bundle bundle = new Bundle();

    bundle.putString("foo", "bar");
    service.putExtras(bundle);

    getReactApplicationContext().startService(service);
   }
}