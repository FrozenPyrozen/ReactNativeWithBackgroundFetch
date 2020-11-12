package com.reactnativewithnativemoduletest;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Promise;

import javax.annotation.Nonnull;

import static android.content.Context.BIND_AUTO_CREATE;

public class BackgroungHeadlessJsModule extends ReactContextBaseJavaModule {
  public static final String REACT_CLASS = "BackgroungHeadlessJs";
  private static ReactApplicationContext reactContext;

  public BackgroungHeadlessJsModule(@Nonnull ReactApplicationContext reactContext) {
    super(reactContext);
    this.reactContext = reactContext;
  }

  @ReactMethod
  public void nativeWait(Promise c) {
    final Handler h = new Handler();
    final Runnable r = new Runnable() {
      @Override
      public void run() {
        c.resolve(1);
        h.postDelayed(this, 1000);
      }
    };
    h.postDelayed(r, 1000);
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
