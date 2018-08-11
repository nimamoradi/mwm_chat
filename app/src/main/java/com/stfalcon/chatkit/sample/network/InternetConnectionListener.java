package com.stfalcon.chatkit.sample.network;

public interface InternetConnectionListener {
    void onInternetUnavailable();

    void onCacheUnavailable();
}
