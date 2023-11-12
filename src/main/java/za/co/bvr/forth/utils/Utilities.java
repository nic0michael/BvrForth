package za.co.bvr.forth.utils;

import java.util.Collection;
import java.util.TreeMap;


import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;
import za.co.bvr.forth.exceptions.LineIsEmptyException;

public class Utilities {


  public static String removeUnwantedSpaces(String line) throws LineIsEmptyException {
    if(isEmpty(line)){
      throw new LineIsEmptyException();
    }
    String str = line;
    str = removeDuplicateSpaces(str);
    str = removeFirstAndLastSpace(str);
    return str;
  }

  private static String removeDuplicateSpaces(String line) throws LineIsEmptyException {
    if(isEmpty(line)){
      throw new LineIsEmptyException();
    }
    String str = line.replaceAll("\\s+", " ");

    return str;

  }

  private static String removeFirstAndLastSpace(String line) throws LineIsEmptyException {
    if(isEmpty(line)){
      throw new LineIsEmptyException();
    }
    String str = line;
    str = str.replaceFirst("^ *", "");
    if (str.charAt(str.length() - 1) == ' ') {
      str=str.substring(0,str.length() - 1);
    }
    return str;
  }

  public static String[] sortStringArray(String[] unsortedArray) {
    String[] sortedArray = new String[unsortedArray.length];
    TreeMap<String, String> treeMap = new TreeMap<>();
    for (String element : unsortedArray) {
      treeMap.put(element, element);
    }
    Collection<String> sortedList = treeMap.values();
    int count = 0;
    for (String element : sortedList) {
      sortedArray[count] = element;
      count++;
    }
    return sortedArray;
  }

  public static String sortVerbNamesInString(String names) {

    String verbs = names;
    String[] verbNames = verbs.split(" ");
    String[] sortedVerbNames = Utilities.sortStringArray(verbNames);
    StringBuilder result = new StringBuilder();
    int loop = 0;
    for (String verbName : sortedVerbNames) {
      if (loop > 0) {
        result.append(" ");
      }
      result.append(verbName);
      loop++;
    }
    return result.toString();
  }


  public static boolean isEmpty(String str){
    return StringUtils.isEmpty(str);
  }

  public static boolean isNumeric(String strNum) {
    Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");

    if(StringUtils.isEmpty(strNum) || " ".equals(strNum)){
      return false;
    }

    return pattern.matcher(strNum).matches();
  }

  public static boolean isInteger(String val) {
    boolean retval = false;
    if(!StringUtils.isNumeric(val)){
      return false;
    }
    try {
      //int i = new Integer(val).parseInt(val);
      int i = Integer.parseInt(val);
      retval = true;
    } catch (NumberFormatException e) {
      retval = false;
    }
    return retval;
  }


  public static boolean isDouble(String val) {
    boolean retval = false;
    if(!StringUtils.isNumeric(val)){
      return false;
    }
    try {
      //int i = new Integer(val).parseInt(val);
      double d = Double.parseDouble(val);
      retval = true;
    } catch (NumberFormatException e) {
      retval = false;
    }

    return retval;
  }

  public static String getComputerIpAddress() throws UnknownHostException{
    String ip = InetAddress.getLocalHost().getHostAddress();
    return ip;
  }

  public static String getComputerName() throws UnknownHostException{
    String computerName = InetAddress.getLocalHost().getHostName();
    return computerName;
  }

  public static String getIpAddressOfHosst(String hostUrl) throws UnknownHostException{
    String ip = InetAddress.getByName(hostUrl).getHostAddress();
    return ip;
  }



  public char toAscii(int c) {
    char ch = (char) c;

    return ch;
  }

  public void hundredMsDelay(int val) { // 10   =   1 second
    int delay = 100 * val;
    try {
      //  add to unit test System.out.println(System.currentTimeMillis());
      Thread.sleep(delay);
    } catch (InterruptedException e) {
    }
  }


}
