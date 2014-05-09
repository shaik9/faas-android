package com.ehc.faas;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

public class FAAS extends Activity {
  static String apiKey;
  static String channelName;
  static Drawable icon = null;

  public static Drawable getIcon() {
    return icon;
  }

  public static void setActionBarIcon(Drawable icon) {
    FAAS.icon = icon;
  }

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
    if (icon != null) {
      getActionBar().setIcon(icon);
    }
  }
}
