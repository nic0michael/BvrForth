package za.co.bvr.forth.utils;

import com.sun.xml.internal.fastinfoset.stax.events.Util;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.Collection;
import java.util.TreeMap;
import za.co.bvr.forth.exceptions.LineIsEmptyException;

/**
 *
 * @author nickm 
 */
public class Utilities {

    public static String removeUnwantedSpaces(String line) throws LineIsEmptyException {
        if(Util.isEmptyString(line)){
            throw new za.co.bvr.forth.exceptions.LineIsEmptyException();
        }
        String str = line;
        str = removeDuplicateSpaces(str);
        str = removeFirstAndLastSpace(str);
        return str;
    }

    private static String removeDuplicateSpaces(String line) throws LineIsEmptyException {
        if(Util.isEmptyString(line)){
            throw new za.co.bvr.forth.exceptions.LineIsEmptyException();
        }
        String str = line.replaceAll("\\s+", " ");

        return str;

    }

    private static String removeFirstAndLastSpace(String line) throws LineIsEmptyException {
        if(Util.isEmptyString(line)){
            throw new za.co.bvr.forth.exceptions.LineIsEmptyException();
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

        String verbs = names.toString();
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

    public static boolean isNumeric(String verb) {
        NumberFormat formatter = NumberFormat.getInstance();
        ParsePosition pos = new ParsePosition(0);
        formatter.parse(verb, pos);
        return verb.length() == pos.getIndex();
    }

    public static boolean stringIsEmpty(String str) {
        return str==null || str.length()==0;
    }
    
    public boolean isEmptyString(String str){
        return str==null || str.length()==0;
    }
}
