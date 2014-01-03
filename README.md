faas-android
============

Faas-android is a library project which helps to integrate [FAAS](http://www.faas.in) service in any Android App.
It provides basic feedback form to collect feedback from users.

###How To Use?
Using the library is really simple, just place [faas.jar]() in your project lib folder or include following artifact in your pom.xml

 ```
 <dependency>
          <groupId>com.github.ehc.tools</groupId>
          <artifactId>faas</artifactId>
          <version>1.0.0</version>
 </dependency>
```

###Configuration

Add below code in your AndroidManifest.xml file
```
 <uses-permission android:name="android.permission.INTERNET"/>
 <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
 <application android:label="@string/app_name"
                 android:icon="@drawable/ic_launcher">
        .........
        .........
        <activity android:name="com.ehc.faas.Faas"/>
</application>
```



Import the Faas class in your code and call Faas.register("channelName","apiKey") method in your main Activity onCreate().
And call Faas.showFeedbackForm(context) method when ever you want to show FeedbackForm.

Ex:
```

import com.ehc.faas.Faas;
public class MyActivity extends Activity {

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);
    Faas.register("Android", "adb256b8d606523ed43d814a5149954c");
    Faas.showFeedbackForm(this);
  }
}

```
