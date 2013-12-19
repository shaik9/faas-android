package com.ehc.faas;

public class Faas {

  static String apiKey;
  static String channelName;

  public static void register(String channelName, String apiKey) {
    Faas.apiKey = apiKey;
    Faas.channelName = channelName;
  }

}
