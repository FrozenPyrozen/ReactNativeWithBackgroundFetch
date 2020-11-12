package com.reactnativewithnativemoduletest;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
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

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getReactApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    @Nonnull
    @Override
    public String getName() {
        return REACT_CLASS;
    }

    @ReactMethod
    public void startService() {
        if (!isMyServiceRunning(MyTaskService.class)) {
            Intent service = new Intent(getReactApplicationContext(), MyTaskService.class);
            Bundle bundle = new Bundle();

            bundle.putString("foo", "bar");
            service.putExtras(bundle);

            getReactApplicationContext().startService(service);
        }
    }
}