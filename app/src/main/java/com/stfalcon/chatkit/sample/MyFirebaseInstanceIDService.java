package com.stfalcon.chatkit.sample;


import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by nima on 7/29/2018.
 */

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {
    private static final String TAG = "FirebaseInstanceID";

    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        sendRegistrationToServer(refreshedToken);
    }

    private void sendRegistrationToServer(String refreshedToken) {
    }
}
