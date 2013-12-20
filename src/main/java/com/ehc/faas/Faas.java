package com.ehc.faas;

import android.app.Activity;
import android.content.Context;

public class Faas {
  static String apiKey;
  static String channelName;

  public static void register(String channelName, String apiKey) {
    Faas.apiKey = apiKey;
    Faas.channelName = channelName;
  }

  public static void showFeedbackView(Context context) {
    FeedbackView feedbackView = new FeedbackView(context);
    ((Activity) (context)).setContentView(feedbackView);
  }
}
