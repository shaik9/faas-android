package com.ehc.faas;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class Faas extends Activity {
  static String apiKey;
  static String channelName;

  public static void register(String channelName, String apiKey) {
    Faas.apiKey = apiKey;
    Faas.channelName = channelName;
  }

  public static void showFeedbackForm(Context context) {
    Intent intent = new Intent(context, Faas.class);
    ((Activity) context).startActivity(intent);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    FeedbackView feedbackView = new FeedbackView(this);
    setContentView(feedbackView);
  }
}
