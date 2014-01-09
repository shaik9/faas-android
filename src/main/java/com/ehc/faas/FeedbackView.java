package com.ehc.faas;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.GradientDrawable;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.*;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class FeedbackView extends ScrollView {
  private Button sendButton;
  private Button cancelButton;
  private EditText suggestionEditText;
  private RadioButton suggestionButton;
  private RadioButton reportButton;
  private RadioButton ratingButton;
  private RadioGroup radioGroup;
  private RatingBar ratingBar;
  private CheckedTextView deviceInfoTextView;
  private Context context;
  private CheckBox includeDevice;
  private String comment = "";
  private String rating;
  private String feedbackType = "Suggestion";
  private String response;
  private int responseCode;
  private LinearLayout container;


  public FeedbackView(Context context) {
    super(context);
    this.context = context;
    createWidgetInstances();
    attachWidgets();
    applyActions();
  }

  private void attachWidgets() {
    container.addView(radioGroup, 0);
    container.addView(ratingBar, 1);
    container.addView(suggestionEditText, 2);
    container.addView(getLinearLayout(includeDevice, deviceInfoTextView), 3);
    container.addView(getButtonGroup(), 4);
    this.addView(container);
  }

  private LinearLayout getButtonGroup() {
    LinearLayout layout = new LinearLayout(context);
    layout.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
    layout.setOrientation(container.HORIZONTAL);
    layout.addView(cancelButton);
    layout.addView(sendButton);
    layout.setPadding(8, 8, 8, 8);
    return layout;
  }

  private View getLinearLayout(CheckBox includeDevice, CheckedTextView deviceInfoTextView) {
    LinearLayout linearLayout = new LinearLayout(context);
    linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
    linearLayout.addView(includeDevice, 0);
    linearLayout.addView(deviceInfoTextView, 1);
    linearLayout.setPadding(8, 8, 8, 8);
    linearLayout.setGravity(Gravity.CENTER_VERTICAL);
    return linearLayout;
  }

  private void createWidgetInstances() {
    container = new LinearLayout(context);
    container.setOrientation(container.VERTICAL);
    createRadioButtonGroup();
    sendButton = createButton("Send");
    cancelButton = createButton("Cancel");
    suggestionEditText = createEditText();
    ratingBar = createRatingBar();
    ratingBar.setVisibility(GONE);
    deviceInfoTextView = createCheckedTextView("Include Device Information");
    includeDevice = createCheckBox();
  }

  private CheckBox createCheckBox() {
    CheckBox checkBox = new CheckBox(context);
    checkBox.setGravity(Gravity.RIGHT);
    LinearLayout.LayoutParams params = new
        LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    checkBox.setLayoutParams(params);
    checkBox.setChecked(true);
    return checkBox;
  }

  private CheckedTextView createCheckedTextView(String message) {
    CheckedTextView checkedTextView = new CheckedTextView(context);
    checkedTextView.setText(message);
    checkedTextView.setPadding(8, 0, 0, 0);
    LinearLayout.LayoutParams params = new
        LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    checkedTextView.setLayoutParams(params);
    return checkedTextView;
  }

  private RatingBar createRatingBar() {
    RatingBar ratingBar = new RatingBar(context);
    ratingBar.setNumStars(5);
    ratingBar.setMax(5);
    ratingBar.setStepSize(0.5f);
    LinearLayout.LayoutParams params = new
        LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, (int) ((getScreenDimensions().y) * 0.5f));
    params.setMargins(16, 8, 16, 8);
    ratingBar.setLayoutParams(params);
    return ratingBar;
  }

  private EditText createEditText() {
    EditText editText = new EditText(context);
    LinearLayout.LayoutParams params = new
        LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, (int) ((getScreenDimensions().y) * 0.5f));
    params.setMargins(16, 8, 16, 8);
    editText.setLayoutParams(params);
    editText.setTextColor(Color.BLACK);
    editText.setHint("Comments");
    editText.setGravity(Gravity.START);
    GradientDrawable gradientDrawable = new
        GradientDrawable(GradientDrawable.Orientation.BOTTOM_TOP, new int[]{Color.WHITE, Color.WHITE});
    gradientDrawable.setStroke(1, Color.parseColor("#c7c7c7"));
    editText.setBackground(gradientDrawable);
    return editText;
  }

  public Point getScreenDimensions() {
    Point screenSize = new Point();
    ((Activity) context).getWindowManager().getDefaultDisplay().getSize(screenSize);
    return screenSize;
  }

  private Button createButton(String text) {
    Button button = new Button(context);
    button.setText(text);
    button.setPadding(16, 16, 16, 16);
    LinearLayout.LayoutParams params = new
        LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
    params.weight = 1;
    button.setLayoutParams(params);
    return button;
  }

  public void createRadioButtonGroup() {
    radioGroup = new RadioGroup(context);
    radioGroup.setPadding(16, 16, 16, 16);
    radioGroup.setOrientation(RadioGroup.HORIZONTAL);
    LinearLayout.LayoutParams params = new
        LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
    radioGroup.setLayoutParams(params);
    suggestionButton = createRadioButton("Suggestion");
    suggestionButton.setId(1);
    reportButton = createRadioButton("Report");
    reportButton.setId(2);
    ratingButton = createRadioButton("Rating");
    ratingButton.setId(3);
    radioGroup.addView(suggestionButton);
    radioGroup.addView(reportButton);
    radioGroup.addView(ratingButton);
    radioGroup.check(suggestionButton.getId());
  }

  private RadioButton createRadioButton(String text) {
    RadioButton radioButton = new RadioButton(context);
    radioButton.setTextSize(16);
    radioButton.setText(text);
    return radioButton;
  }

  public FeedbackView(Context context, AttributeSet attrs) {
    super(context, attrs);
    this.context = context;
    createWidgetInstances();
    attachWidgets();
    applyActions();
  }

  public FeedbackView(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
    this.context = context;
    createWidgetInstances();
    attachWidgets();
    applyActions();
  }

  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
  }

  @Override
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
  }

  @Override
  protected void onAttachedToWindow() {
    super.onAttachedToWindow();
  }

  public void clearText() {
    suggestionEditText.setText("");
    comment = "";
  }

  private void applyActions() {
    radioGroup.setOnCheckedChangeListener(getListener());
    ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
      @Override
      public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
        rating = String.valueOf(v);
      }
    });
    sendButton.setOnClickListener(getOnClickListener());
    cancelButton.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        ((Activity) context).finish();
      }
    });
  }

  private OnClickListener getOnClickListener() {
    return new OnClickListener() {
      @Override
      public void onClick(View view) {
        if (FAAS.apiKey == null || FAAS.channelName == null || FAAS.apiKey.equals("") || FAAS.channelName.equals("")) {
          showWarningMessage();
          return;
        }
        if (!suggestionEditText.getText().toString().isEmpty()) {
          comment = suggestionEditText.getText().toString();
        }
        if (comment.equals("") && !feedbackType.equalsIgnoreCase("rating")) {
          showAlertDialog();
        } else {
          if (Network.isConnected(context)) {
            new FeedbackAsyncTask().execute();
            ((Activity) context).finish();
          } else {
            displayToast("Please Check Your Network Connection.");
          }
        }
      }
    };
  }

  private RadioGroup.OnCheckedChangeListener getListener() {
    return new RadioGroup.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(RadioGroup radioGroup, int selectionId) {
        switch (selectionId) {
          case 1:
            suggestionEditText.setVisibility(View.VISIBLE);
            ratingBar.setVisibility(View.GONE);
            feedbackType = "Suggestion";
            break;
          case 2:
            suggestionEditText.setVisibility(View.VISIBLE);
            ratingBar.setVisibility(View.GONE);
            feedbackType = "Report";
            break;
          case 3:
            suggestionEditText.setVisibility(View.GONE);
            ratingBar.setVisibility(View.VISIBLE);
            feedbackType = "Rating";
        }
        clearText();
      }
    };
  }

  private void showWarningMessage() {
    AlertDialog.Builder warningDialog = new AlertDialog.Builder(context);
    warningDialog.setMessage("Sorry! Couldn't find Api-key or Channel ");
    warningDialog.setNegativeButton("OK", new DialogInterface.OnClickListener() {
      public void onClick(DialogInterface dialog, int which) {
        dialog.cancel();
      }
    });
    warningDialog.show();
  }

  private void showAlertDialog() {
    AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
    alertDialog.setMessage("Send without comments?");
    alertDialog.setCancelable(false);
    alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
      public void onClick(DialogInterface dialog, int which) {
        new FeedbackAsyncTask().execute();
      }
    });
    alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
      public void onClick(DialogInterface dialog, int which) {
        dialog.cancel();
      }
    });
    alertDialog.show();
  }

  private void postFeedback() {
    HttpClient httpClient = new DefaultHttpClient();
    HttpPost httpPost = createHttpPost();
    try {
      HttpResponse httpResponse = httpClient.execute(httpPost);
      HttpEntity entity = httpResponse.getEntity();
      responseCode = httpResponse.getStatusLine().getStatusCode();
      if (entity != null) {
        response = EntityUtils.toString(entity);
      }
    } catch (ClientProtocolException e) {
      e.printStackTrace();
      Log.d("faas:ClientProtocolException", e.toString());
    } catch (IOException e) {
      e.printStackTrace();
      Log.d("faas:IOException", e.toString());
    }
  }

  private HttpPost createHttpPost() {
    HttpPost httpPost = new HttpPost(getUrl());
    if (includeDevice.isChecked()) {
      httpPost.setHeader("User-Agent", System.getProperty("http.agent"));
    }
    List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
    nameValuePair.add(new BasicNameValuePair("feedback_type", feedbackType));
    nameValuePair.add(new BasicNameValuePair("comments", comment));
    nameValuePair.add(new BasicNameValuePair("rating", rating));
    nameValuePair.add(new BasicNameValuePair("api_key", FAAS.apiKey));
    try {
      httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
      Log.d("faas:UnsupportedEncodingException", e.toString());
    }
    return httpPost;
  }

  private String getUrl() {
    String url = "http://www.faas.in/channels/" + FAAS.channelName + "/feedback";
    return url;
  }

  private void showResultMessage(String response, int responseCode) {
    if (responseCode == 200) {
      if (response.equalsIgnoreCase("success")) {
        displayToast("Thank You For Sending Feedback");
      } else if (response.equalsIgnoreCase("fail")) {
        displayToast("Feedback Sending Failed");
      } else {
        displayToast("Problem Connecting To Server");
      }
    } else {
      displayToast("Problem Connecting To Server");
    }
  }

  private class FeedbackAsyncTask extends AsyncTask {
    @Override
    protected Object doInBackground(Object... objects) {
      postFeedback();
      return null;
    }

    @Override
    protected void onPostExecute(Object o) {
      super.onPostExecute(o);
      showResultMessage(response, responseCode);
    }
  }

  private void displayToast(String message) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
  }
}
