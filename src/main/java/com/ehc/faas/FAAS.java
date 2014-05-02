package com.ehc.faas;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class FAAS extends Activity {
  static String apiKey;
  static String channelName;

  public static void register(String channelName, String apiKey) {
    FAAS.apiKey = apiKey;
    FAAS.channelName = channelName;
  }

  public static void showFeedbackForm(Context context) {
    Intent intent = new Intent(context, FAAS.class);
    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    context.startActivity(intent);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    FeedbackView feedbackView = new FeedbackView(this);
    setContentView(feedbackView);
  }
}
