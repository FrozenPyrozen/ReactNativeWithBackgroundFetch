package com.reactnativewithnativemoduletest;

import android.content.Intent;
import android.os.Bundle;
import com.facebook.react.HeadlessJsTaskService;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.jstasks.HeadlessJsTaskConfig;
import com.facebook.react.jstasks.LinearCountingRetryPolicy;

import javax.annotation.Nullable;

public class MyTaskService extends HeadlessJsTaskService {

  @Override
  protected @Nullable HeadlessJsTaskConfig getTaskConfig(Intent intent) {
    Bundle extras = intent.getExtras();
    if (extras != null) {
      LinearCountingRetryPolicy retryPolicy = new LinearCountingRetryPolicy(
              3, // Max number of retry attempts
              10000 // Delay between each retry attempt
      );


      return new HeadlessJsTaskConfig(
          "ContinueInBackground",
          Arguments.fromBundle(extras),
          1000, // timeout for the task
          true, // optional: defines whether or not  the task is allowed in foreground. Default is false
              retryPolicy
        );
    }
    return null;
  }
}