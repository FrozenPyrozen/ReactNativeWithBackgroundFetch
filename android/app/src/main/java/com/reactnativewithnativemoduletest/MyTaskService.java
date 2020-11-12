package com.reactnativewithnativemoduletest;

import android.content.Intent;
import android.os.Bundle;
import com.facebook.react.HeadlessJsTaskService;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.jstasks.HeadlessJsTaskConfig;
import com.facebook.react.jstasks.LinearCountingRetryPolicy;

import javax.annotation.Nullable;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.res.Resources;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.os.PowerManager;
import android.app.NotificationChannel;

import com.facebook.react.bridge.ReadableMap;

import org.json.JSONObject;

import static android.os.PowerManager.PARTIAL_WAKE_LOCK;

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

  // / Fixed ID for the 'foreground' notification
    public static final int NOTIFICATION_ID = -574543954;

    // Default title of the background notification
    private static final String NOTIFICATION_TITLE =
            "App is running in background";

    // Default text of the background notification
    private static final String NOTIFICATION_TEXT =
            "Doing heavy tasks.";

    // Default icon of the background notification
    private static final String NOTIFICATION_ICON = "icon";

    // Binder given to clients
    private final IBinder binder = new ForegroundBinder();

    // Partial wake lock to prevent the app from going to sleep when locked
    private PowerManager.WakeLock wakeLock;

    /**
     * Allow clients to call on to the service.
     */
    // @Override
    // public IBinder onBind (Intent intent) {
    //     return binder;
    // }

    /**
     * Class used for the client Binder.  Because we know this service always
     * runs in the same process as its clients, we don't need to deal with IPC.
     */
    class ForegroundBinder extends Binder
    {
        MyTaskService getService()
        {
            // Return this instance of ForegroundService
            // so clients can call public methods
            return MyTaskService.this;
        }
    }

    /**
     * Put the service in a foreground state to prevent app from being killed
     * by the OS.
     */
    // @Override
    public void onCreate()
    {
        super.onCreate();
        System.out.println("service onCreate");
        keepAwake();
    }

    /**
     * No need to run headless on destroy.
     */
    @Override
    public void onDestroy()
    {
        super.onDestroy();
        sleepWell();
    }

    /**
     * Prevent Android from stopping the background service automatically.
     */
    // @Override
    // public int onStartCommand (Intent intent, int flags, int startId) {
    //     return START_STICKY;
    // }

    /**
     * Put the service in a foreground state to prevent app from being killed
     * by the OS.
     */
    @SuppressLint("WakelockTimeout")
    private void keepAwake()
    {
        System.out.println("service keep awake");
        startForeground(NOTIFICATION_ID, makeNotification());

        PowerManager pm = (PowerManager)getSystemService(POWER_SERVICE);

        wakeLock = pm.newWakeLock(
                PARTIAL_WAKE_LOCK, "backgroundmode:wakelock");

        wakeLock.acquire();

        System.out.println("service keep awake finish");
    }

    /**
     * Stop background mode.
     */
    private void sleepWell()
    {
        stopForeground(true);
        getNotificationManager().cancel(NOTIFICATION_ID);

        if (wakeLock != null) {
            wakeLock.release();
            wakeLock = null;
        }
    }


    /**
     * Create a notification as the visible part to be able to put the service
     * in a foreground state.
     */
    private Notification makeNotification ()
    {
        // use channelid for Oreo and higher
        String CHANNEL_ID = "cordova-plugin-background-mode-id";
        if(Build.VERSION.SDK_INT >= 26){
        // The user-visible name of the channel.
        CharSequence name = "cordova-plugin-background-mode";
        // The user-visible description of the channel.
        String description = "cordova-plugin-background-moden notification";

        int importance = NotificationManager.IMPORTANCE_HIGH;

        NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name,importance);

        // Configure the notification channel.
        mChannel.setDescription(description);

        getNotificationManager().createNotificationChannel(mChannel);
        }
        String title    = NOTIFICATION_TITLE;
        String text     = NOTIFICATION_TEXT;
        boolean bigText = false;

        Context context = getApplicationContext();
        String pkgName  = context.getPackageName();
        Intent intent   = context.getPackageManager()
                .getLaunchIntentForPackage(pkgName);

        Notification.Builder notification = new Notification.Builder(context)
                .setContentTitle(title)
                .setContentText(text)
                .setOngoing(true)
                .setSmallIcon(R.mipmap.ic_launcher);

        if(Build.VERSION.SDK_INT >= 26){
                   notification.setChannelId(CHANNEL_ID);
        }

        notification.setPriority(Notification.PRIORITY_MAX);

        if (bigText || text.contains("\n")) {
            notification.setStyle(
                    new Notification.BigTextStyle().bigText(text));
        }

        setColor(notification);

        if (intent != null) {
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            PendingIntent contentIntent = PendingIntent.getActivity(
                    context, NOTIFICATION_ID, intent,
                    PendingIntent.FLAG_UPDATE_CURRENT);


            notification.setContentIntent(contentIntent);
        }

        return notification.build();
    }

    /**
     * Update the notification.
     */
    protected void updateNotification ()
    {
        boolean isSilent = false;

        if (isSilent) {
            stopForeground(true);
            return;
        }

        Notification notification = makeNotification();
        getNotificationManager().notify(NOTIFICATION_ID, notification);

    }

    /**
     * Retrieves the resource ID of the app icon.
     *
     * @param settings A JSON dict containing the icon name.
     */
    private int getIconResId (JSONObject settings)
    {
        String icon = settings.optString("icon", NOTIFICATION_ICON);

        int resId = getIconResId(icon, "mipmap");

        if (resId == 0) {
            resId = getIconResId(icon, "drawable");
        }

        return resId;
    }

    /**
     * Retrieve resource id of the specified icon.
     *
     * @param icon The name of the icon.
     * @param type The resource type where to look for.
     *
     * @return The resource id or 0 if not found.
     */
    private int getIconResId (String icon, String type)
    {
        Resources res  = getResources();
        String pkgName = getPackageName();

        int resId = res.getIdentifier(icon, type, pkgName);

        if (resId == 0) {
            resId = res.getIdentifier("icon", type, pkgName);
        }

        return resId;
    }

    /**
     * Set notification color if its supported by the SDK.
     *
     * @param notification A Notification.Builder instance
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setColor (Notification.Builder notification)
    {

        String hex = null;

        if (Build.VERSION.SDK_INT < 21 || hex == null)
            return;

        try {
            int aRGB = Integer.parseInt(hex, 16) + 0xFF000000;
            notification.setColor(aRGB);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the shared notification service manager.
     */
    private NotificationManager getNotificationManager()
    {
        return (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }
}