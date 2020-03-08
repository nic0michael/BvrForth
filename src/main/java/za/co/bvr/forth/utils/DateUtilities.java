package za.co.bvr.forth.utils;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

/**
 *
 * @author nicm
 */
public class DateUtilities {
    
    public void dateminus(){
        LocalDateTime fiveHoursBefore = LocalDateTime.now().minusHours(5);
        
        LocalDateTime nowLDT = LocalDateTime.now();
        LocalDateTime hourLater = LocalDateTime.now().plusHours(1);
        Duration span = Duration.between(nowLDT, hourLater);
        
        LocalDate nowL = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = nowL.format(formatter);
        
        LocalDateTime localDateTime = LocalDateTime.now();
        ZonedDateTime zdt = ZonedDateTime.of(localDateTime, ZoneId.systemDefault());
        long ms = zdt.toInstant().toEpochMilli();
        
        

    }
    
    
    public static String date() { // Banking Y2K format
        return formattedDate("yyyy-MM-dd");
    }

    public static String dateBritish() {
        return formattedDate("dd/MM/yyyy");
    }

    public static String day() {
        return formattedDate("dd");
    }

    public static String month() {
        return formattedDate("MM");
    }

    public static String year() {
        return formattedDate("yyyy");
    }

    public static String dateUSA() {
        return formattedDate("MM/dd/yyyy");
    }
    public static String dateY2k() { 
        return formattedDate("yyyy-MM-dd"); 
    }
    


    public static String time() {
        return formattedDate("HH:mm:ss");
    }

    public static String now() {
        return formattedDate("yyyy-MM-dd HH:mm:ss");
    }

    public static String timeStamp() {
        return formattedDate("yyyyMMddHHmmss");
    }
    
    public String y2kDate(){
        return formattedDate("yyyy-MM-dd");
    }
    
    public static String formattedDate(String pattern){
        String formattedDate;
        LocalDate nowL = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        formattedDate = nowL.format(formatter);
        return formattedDate;
    }
    
    public static String dateBeginningOfMonth(LocalDate initialDate,String pattern){
        String formattedDate;        
        LocalDate start = initialDate.withDayOfMonth(1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        formattedDate = initialDate.format(formatter);
        return formattedDate;
    }
    
    
    public static String dateBeginningOfCurrentMonth(String pattern){
        String formattedDate;
        LocalDate initial = LocalDate.now();
        LocalDate start = initial.withDayOfMonth(1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        formattedDate = initial.format(formatter);
        return formattedDate;
    }
    
    public static String dateEndOfCurrentMonth(String pattern){
        String formattedDate;
        LocalDate initial = LocalDate.now();
        LocalDate endDate = initial.withDayOfMonth(initial.lengthOfMonth());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        formattedDate = endDate.format(formatter);
        return formattedDate;
    }    
    
    public static String dateEndOfMonth(LocalDate initialDate,String pattern){
        String formattedDate;
        LocalDate endDate = initialDate.withDayOfMonth(initialDate.lengthOfMonth());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        formattedDate = endDate.format(formatter);
        return formattedDate;
    }
    
    public long timeInMilis(){   
        long ms;
        LocalDateTime localDateTime = LocalDateTime.now();
        ZonedDateTime zdt = ZonedDateTime.of(localDateTime, ZoneId.systemDefault());
        ms = zdt.toInstant().toEpochMilli();
        return ms;
    }
    
    
    


    /**
     * This returns milliseconds from Jan 1, 1970.
     * 
     * @return 
     */
    public static long GetTimeInMilliSeconds() {  
        Calendar now = Calendar.getInstance();
        return now.getTimeInMillis();
    }
  
}
