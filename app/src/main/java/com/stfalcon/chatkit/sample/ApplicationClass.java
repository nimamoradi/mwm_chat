package com.stfalcon.chatkit.sample;

import android.app.Application;

import com.onesignal.OneSignal;

/**
 * Created by nima on 7/29/2018.
 */

public class ApplicationClass extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // OneSignal Initialization
        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();
    }
}
