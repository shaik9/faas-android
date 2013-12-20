package com.ehc.faas;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Network {
  public static NetworkInfo getNetworkInfo(Context context) {
    ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    return connectivityManager.getActiveNetworkInfo();
  }

  public static boolean isConnected(Context context) {
    NetworkInfo networkInfo = Network.getNetworkInfo(context);
    return (networkInfo != null && networkInfo.isConnected());
  }
}
