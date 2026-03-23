package za.co.bvr.forth.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class StringUtils {


  public static String getStringFromUser(String displayMessage) throws Exception {
    InputStreamReader isr = new InputStreamReader(System.in);
    BufferedReader reader = new BufferedReader(isr);

    System.out.println(displayMessage);
    System.out.print(">");
    String textRead = reader.readLine();
    return textRead;
  }
}
