package za.co.bvr.forth.dictionary;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import lombok.extern.java.Log;
import org.apache.commons.lang3.StringUtils;
import za.co.bvr.forth.exceptions.VerbNotInDictionaryException;
import za.co.bvr.forth.utils.Utilities;

/**
 *  
 *  
 * @author nickm
 */
@Log
public class ForthDictionary {

    private static Map<String, String> verbDefinitions = new HashMap<String, String>();
    private static Map<String, String> verbCompiledDefinitions = new HashMap<String, String>();
    private static Map<String, String> verbDescriptions = new HashMap<String, String>();

    private static Map<String, String> systemVerbDefinitions = new HashMap<String, String>();
    private static Map<String, String> systemVerbCompiledDefinitions = new HashMap<String, String>();
    private static Map<String, String> systemVerbDescriptions = new HashMap<String, String>();

    private static List<Verb> verbHistory = new ArrayList<Verb>();
    
    public static final ForthDictionary INSTANCE = new ForthDictionary();

    private ForthDictionary() {
        loadDictionary();
    }

    public void clearDictionary() {
        verbDefinitions = new HashMap<>();
        verbCompiledDefinitions = new HashMap<>();
        verbDescriptions = new HashMap<>();
        verbHistory = new ArrayList<Verb>();
    }

    public void addVerbToDictionary(Verb verb) {
        String name = verb.getName().toUpperCase();
        String description;
        String definition = verbDefinitions.get(name);
        String compiledDefinition = verbCompiledDefinitions.get(name);

        if (definition == null) {
            verbDefinitions.put(name, verb.getDefinition());
            verbCompiledDefinitions.put(name, verb.getCompiledDefinition());
            description = verb.getDescription();
            if (description == null) {
                description = verb.getDefinition();
            }
            verbDescriptions.put(name, description);
        } else {
            if (verbIsNotSystemVerb(name)) {
                Verb previousVerb = new Verb(name, definition, compiledDefinition,"User defined verb");
                verbHistory.add(previousVerb);
            }

            verbDefinitions.remove(name);
            verbCompiledDefinitions.remove(name);
            verbDescriptions.remove(name);

            description = verb.getDescription();
            if (description == null) {
                description = verb.getDefinition();
            }
            verbDescriptions.put(name, description);
            verbDefinitions.put(name, verb.getDefinition());
            verbCompiledDefinitions.put(name, verb.getCompiledDefinition());
        }
    }

    public void removeFromDictionary(Verb verb) {
        String name = verb.getName().toUpperCase();
        verbDefinitions.remove(name);
        verbCompiledDefinitions.remove(name);
        verbDescriptions.remove(name);

        Verb previousVerb = findPreviousVerb(verb);
        removePreviousVerbFromHistory(verb);
        if (previousVerb != null) {
            addVerbToDictionary(previousVerb);
        }
    }

    public String getDefinition(String verbName) throws VerbNotInDictionaryException {
        String definition = verbDefinitions.get(verbName.toUpperCase());
        if (definition == null) {
            definition = systemVerbDefinitions.get(verbName.toUpperCase());
        }
        if (definition == null) {
            throw new VerbNotInDictionaryException(verbName);
        }
        return definition;
    }

    public String getCompiledDefinition(String verbName) throws VerbNotInDictionaryException {
        String compiledDefinition = verbCompiledDefinitions.get(verbName.toUpperCase());
        if (compiledDefinition == null) {
            compiledDefinition = systemVerbCompiledDefinitions.get(verbName.toUpperCase());
        }
        if (compiledDefinition == null) {
            throw new VerbNotInDictionaryException(verbName);
        }
//        log.info("ForthDictionary verbName : "+verbName+ " compiledDefinition : "+compiledDefinition);
        return compiledDefinition;
    }

    public int size() {
        return verbDefinitions.size();
    }
 
    /**
     *   verbDescriptions systemVerbDescriptions
     *   verbDefinitions systemVerbDefinitions
     * 
     * @return 
     */
    public String showVerbs() {
        StringBuilder result = new StringBuilder();
        List<String> SystemVerbNames =new ArrayList<>();
        result.append("\nSystem Verbs :\n");
        result.append("============\n");
        for (Map.Entry<String, String> entry : systemVerbDescriptions.entrySet()) {
            String key = entry.getKey();
            SystemVerbNames.add(key);
            String value = entry.getValue();
        }
        
         Collections.sort(SystemVerbNames);
        
        for(String SystemVerbName : SystemVerbNames){
            String value = systemVerbDescriptions.get(SystemVerbName);            
            result.append(SystemVerbName);
            result.append(" ");
            result.append(value);
            result.append("\n");
        }
        result.append("\nUser Defined Verbs :\n");
        result.append("==================\n");
        for (Map.Entry<String, String> entry : verbDefinitions.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();            
            result.append(key);
            result.append(" defined as: ");
            result.append(value);
            result.append("\n");
        }
        return result.toString();
    }

    public String showVerbDetails() throws VerbNotInDictionaryException {
        StringBuilder result = new StringBuilder();
        int count = 0;

        for (Map.Entry<String, String> entry : verbDefinitions.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            if (count > 0) {
                result.append("\n");
            }
            result.append("Verb: ");
            result.append(key);
            result.append(" Definition: ");
            result.append(value);
            count++;
        }
        
        return result.toString();
    }

    
    public void addSystemVerb(String name,String description) {
//        log.info("addSystemVerb "+name+" "+description);
        String tab;
        if(name.length()<7){
            tab="\t\t\t";
        } else if(name.length()>14){
            tab="\t";
        } else{
            tab="\t\t";
        }
        Verb verb = new Verb(name.toUpperCase(), name.toUpperCase(), name.toUpperCase(),tab+"SYSTEM VERB:\t"+description);
//        log.info("Verb "+verb);
        addSystemVerbToSystemDictionary(verb);
    }

    private Verb findPreviousVerb(Verb verbToFind) {
        Verb verbFound = null;
        String uuid = verbToFind.getUuid();
        for (Verb verb : verbHistory) {
            if (verb.equals(verbToFind.getName().toUpperCase()) && !verb.guidEquals(uuid)) {
                verbFound = verb;
            }
        }
        return verbFound;
    }

    private boolean verbIsNotSystemVerb(String name) {
        String definition = systemVerbDefinitions.get(name.toUpperCase());
        return definition == null;
    }

    private void removePreviousVerbFromHistory(Verb verbToFind) {
        int foundIndex = -1;
        Verb verbFound = null;
        for (int i = verbHistory.size() - 1; i >= 0; i--) {
            Verb verb = verbHistory.get(i);
            if (verb.equals(verb) && !verb.equals(verbToFind.getName(), verbToFind.getDefinition(), verbToFind.getCompiledDefinition())) {
                foundIndex = i;
            }
        }
        if (foundIndex > -1) {
            verbHistory.remove(foundIndex);
        }
    }

    public void addSystemVerbToSystemDictionary(Verb verb) {
//        log.info("Verb "+verb);
        String name = verb.getName().toUpperCase();
        systemVerbDefinitions.put(name, verb.getDefinition());
        systemVerbCompiledDefinitions.put(name, verb.getCompiledDefinition());
        systemVerbDescriptions.put(name, verb.getDescription());
    }
       /**
     * "REMOVES A VERB FROM THE DICTIONARY (DICTIONARYINDEXMAP)");
     * @return 
     */
    public String forget(String verb) {
        String def = verbDefinitions.get(verb);
        String def2 = verbCompiledDefinitions.get(verb);
        String def3 = verbDescriptions.get(verb);
        
        if(StringUtils.isNotEmpty(def) && !" ".equals(def) &&
              StringUtils.isNotEmpty(def2) && !" ".equals(def2) && 
                StringUtils.isNotEmpty(def3) && !" ".equals(def3) ){
            verbDefinitions.remove(verb);
            verbCompiledDefinitions.remove(verb);
            verbDescriptions.remove(verb);
            return "User verb "+verb+ " removed from dictionary";
        }

        return "User verb "+verb+ " not removed from dictionary !!";
    }

    private void loadDictionary() {

        addSystemVerb(":","Start defining a new verb");
        addSystemVerb("=","Is equal ");
        addSystemVerb("0=","Is equal to 0");
        addSystemVerb("0<","Is smaller than 0");
        addSystemVerb(">","Is greater than");
        addSystemVerb("<","Is smaller than");
        addSystemVerb("<>","Is not equal ");
        addSystemVerb("=>","Is greater equal");
        addSystemVerb("<=","Is smaller equal");
        addSystemVerb("NOT","Logical invert");
        addSystemVerb("+","Add");
        addSystemVerb("-","Subtract");
        addSystemVerb("*","Multiply");
        addSystemVerb("/","Divide");
        addSystemVerb("D+","Floating point addition using Doubles");// Floating point addition using Doubles
        addSystemVerb("D-","Floating point subtraction using Doubles");// floating point arithmetic using Doubles
        addSystemVerb("D*","Floating point multiplication using Doubles"); // floating point arithmetic using Doubles
        addSystemVerb("D/,","Floating point divisuion using Doubles"); // floating point arithmetic using Doubles
        addSystemVerb("D>","Floating point multiplication using Doubles");// floating point arithmetic using Doubles
        addSystemVerb("D<","Floating point is smaller than using Doubles");// floating point arithmetic using Doubles
        addSystemVerb("D=","Floating point is equals using Doubles");// floating point arithmetic using Doubles
        addSystemVerb("D0=","Floating point is equal to zero using Doubles");
        addSystemVerb("D0<","Floating point is smaller than zero using Doubles");

        addSystemVerb(".","Pop and print Stack top");// POP AND PRINT TOS
        

        addSystemVerb("1+","Increase stack top by 1");
        addSystemVerb("1-","Decrease stack top by 1");
        addSystemVerb("2+","Increase stack top by 2");
        addSystemVerb("2*","Multiply stack top by 2");
        addSystemVerb("2/","Divide stack top by 2 SHIFT RIGHT");//SHIFT RIGHT
        addSystemVerb("2-","Decrease stack top by 1");
        addSystemVerb(">>","ADVANCED FEATURE :  BORROWED FROM JAVA BIT SHIFT");
        addSystemVerb(">>>","ADVANCED FEATURE :  BORROWED FROM JAVA BIT SHIFT");
        addSystemVerb("<<","ADVANCED FEATURE :  BORROWEWD FROM JAVA BIT SHIFT");
        addSystemVerb("!","STORE     TO VARIABLE");
        addSystemVerb("+!","APPEND TO VARIABLE");
        addSystemVerb("-!","APPEND TO VARIABLE");
        addSystemVerb("$!","STORE TO STRING VARIABLE");
        addSystemVerb("$L","START OF STRING (THE STRING ENDS WITH A \" ");
        addSystemVerb("\"","START OF STRING (THE STRING ENDS WITH A \" ");
        addSystemVerb("@","FETCH FROM NUMERIC VARIABLE");
        addSystemVerb("?","PRINT NUMERIC VARIABLE");  
        
        addSystemVerb(".COMPUTERNAME","Get Computer name");//Utilities.getComputerIpAddress()
        addSystemVerb(".CNAME","Get Computer name");//Utilities.getComputerIpAddress()
        addSystemVerb(".COMPUTERIP","Get Computer IP");//Utilities.getComputerName()
        addSystemVerb(".CIP","Get Computer IP");//Utilities.getComputerName()

        addSystemVerb(".D","LIST THE DICTIONARY");
        addSystemVerb(".DATE","PRINT DATE IN Y2K BANKING FORMAT (Y2K COMPLIANT DATE)");
        addSystemVerb(".DATEBRITISH","PRINT DATE BRITISH FORMAT  (Y2K COMPLIANT DATE)");
        addSystemVerb(".DATESF","PRINT Y2K COMPLIANT DATE USING FORMAT STRING ON STACK TOP");
        addSystemVerb(".DATESIMPLEFORMAT","PRINT Y2K COMPLIANT DATE USING FORMAT STRING ON STACK TOP YYYY-MM-DD HH:MM:SS");
        addSystemVerb(".DATETIME","PRINT DATE AND TIME  (Y2K COMPLIANT DATE)");
        addSystemVerb(".DATEUSA","PRINT DATE USA FORMAT  (Y2K COMPLIANT DATE)");
        addSystemVerb(".DAY","PRINTS THE DAY");
        addSystemVerb(".DICT","LIST THE DICTIONARY");
        addSystemVerb(".DICTDEF","LIST ALL THE DEFINITIONS OF ALL THE VERBS");
        addSystemVerb(".HELP","Print Help Function");
        addSystemVerb(".H","Print Help Function");
        addSystemVerb(".HASHMAPVARIABLES","LISTS THE NAMES OF ALL THE HASHMAP VARIABLES");
        addSystemVerb(".HM","LISTS THE NAMES OF ALL THE HASHMAP VARIABLES");
        addSystemVerb(".INETADDRESS","PRINTS THE IP ADDRESS OF A SERVER ");
//        addSystemVerb(".LISTVARIABLES","LISTS THE NAMES OF ALL THE LIST VARIABLES");
        addSystemVerb(".L","LISTS THE NAMES OF ALL THE LIST VARIABLES");
        addSystemVerb(".MODE","Print Mode");
        addSystemVerb(".MONTH","PRINTS THE MONTH");
        addSystemVerb(".MTH","PRINTS THE MONTH");
        addSystemVerb(".OS","PRINT OPERATING SYSTEM COMPATIBILITY");
        addSystemVerb(".RSSFEEDMESSAGE","PRINTS A MESSAGE WHERE THE INDEX IS THE TOP OF STACK"); 
        
        addSystemVerb(".SERVERIP","Get Ip Address Of Hosst");      
        addSystemVerb(".SIP","Get Ip Address Of Hosst");    
        addSystemVerb(".STACK","Non-destructively LIST THE STACK");
        addSystemVerb(".STATUS","Display Status");
        addSystemVerb(".S","Non-destructively LIST THE STACK");
        addSystemVerb(".TIME","PRINT TIME");
        addSystemVerb(".TIMESTAMP","Print Timestamp");// PRINT DATE
        addSystemVerb(".VARIABLES","LIST THE VARIABLES AND THEIR CONTENT");
        addSystemVerb(".V","LIST THE VARIABLES AND THEIR CONTENT");
        addSystemVerb(".YEAR","PRINTS THE YEAR");
        addSystemVerb(".YR","PRINTS THE YEAR"); 

        addSystemVerb("$@","PUSH THE VALUE OF A STRING VARIABLE TO THE TOP OF STACK"); 
        addSystemVerb("$CONSTANT","DEFINE STRING CONSTANT WE ALSO HAVE STRING CONSTANTS");
        addSystemVerb("$VARIABLE","DEFINE STRING VARIABLE");
        addSystemVerb("?DUP","QDUP");
//        addSystemVerbs("ASC");
        addSystemVerb("AND","LOGICAL AND");
        addSystemVerb("ALLOT","STORE THE TOP OF STACK TO NUMERIC VARIABLE");

        addSystemVerb("BIN","Set Mode to Binary (Base 2)");
        addSystemVerb("BASE64ENCODE","BASE64 ENCODE THE TOP OF STACK"); 
        addSystemVerb("BASE64DECODE","BASE64 DECODE THE TOP OF STACK"); 
        addSystemVerb("BINARYTODEC","Convert THE TOP OF STACK from BINARY TO DEC");
        addSystemVerb("BASE32DECODE","BASE32 DECODE THE TOP OF STACK"); 
        addSystemVerb("BASE32ENCODE","BASE32 DECODE THE TOP OF STACK"); 
        addSystemVerb("BYE","QUIT PROGRAM");  

        addSystemVerb("CEIL","Round fraction upwards");
        addSystemVerb("COS","Trigonometry Function");
        addSystemVerb("CR","Emit a carrage return");
        addSystemVerb("CONSTANT","DEFINE CONSTANT");
        addSystemVerb("COPY","BLOCK TO BLOCK COPY");
        addSystemVerb("COUNTER","PUSH NR OF CLOCK TICKS TO TOS // IT RETURNS MILLSECONDS FROM JAN 1, 1970.");
//        addSystemVerbs("CHR$");

        
        addSystemVerb("DATE@","PUSH DATE TO STACK");
        addSystemVerb("DATEUSA@","PUSH DATE (USA Format) TO STACK");         
        addSystemVerb("DATEBRITISH@","PUSH DATE (British Format) TO STACK");                      
        addSystemVerb("DATESIMPLEFORMAT@","PUSH DATE TO STACK WHERE FORMAT STRING IS ON TOS YYYY-MM-DD HH:MM:SS");
        addSystemVerb("DATETIME@","PUSH DATE & Time TO STACK");
        addSystemVerb("DELAY","100MS DELAY");
        addSystemVerb("DEC","Set Mode to DECIMAL MODE BASE 10 (SEE BIN)");
        addSystemVerb("DEGTORAD","Convert Degrees to Radians");
        addSystemVerb("DECTOBINARY","Convert Decimal to Binary");
        addSystemVerb("DECTOHEX","Convert Decimal to Hexadecimal");
        addSystemVerb("DECTOOCTAL","Convert Decimal to Octal");
        addSystemVerb("DMOD","Modulus of Floating point division");
        addSystemVerb("DO","Start a DO Loop");
        addSystemVerb("DROP","Stack Operation drop THE TOP OF STACK");        
        addSystemVerb("DSQR","square of Floating point Double of THE TOP OF STACK");
        addSystemVerb("DUP","Stack Operation duplicate THE TOP OF STACK"); 
          

        addSystemVerb("EDIT","MULTIPLE TEXT EDITOR PUSH (1 TO 7) TO SELECT EDITOR THE PUSH PATH TO FILE TO EDIT AS A STRING"); 
        addSystemVerb("ELSE","The Else part of an If statement");
        addSystemVerb("EMIT","Print SPACES The NR OF SPACES IS THE TOP OF STACK"); 
        addSystemVerb("EXEC","EXECUTE AN O/S COMMAND");
        addSystemVerb("EXECNANO","EDIT FILE WITH NANO  FIRST PUSH PATH TO FILE TO EDIT AS A STRING");
        addSystemVerb("EXECKATE","EDIT FILE WITH KATE FIRST PUSH PATH TO FILE TO EDIT AS A STRING");
//        addSystemVerbs("EXECWRITE");
//        addSystemVerbs("EXECKWRITE");//EDIT FILE WITH KWRITE FIRST PUSH PATH TO FILE TO EDIT AS A STRING
//        addSystemVerbs("EXECVI");// EDIT FILE WITH VI FIRST PUSH PATH TO FILE TO EDIT AS A STRING
//        addSystemVerbs("EXECVIM");// EDIT FILE WITH VIM FIRST PUSH PATH TO FILE TO EDIT AS A STRING
//        addSystemVerbs("EXECWORDPAD");// EDIT FILE WITH WORDPAD FIRST PUSH PATH TO FILE TO EDIT AS A STRING
//        addSystemVerbs("EXECNOTEPAD");// EDIT FILE WITH NOTEPAD FIRST PUSH PATH TO FILE TO EDIT AS A STRING

        addSystemVerb("FLOOR","Math floor function");
//        addSystemVerbs("FLUSH");//FLUSHES CHANGED BLOCKS TO DISK                   
//        addSystemVerbs("FILE@");//READ A FILE FROM DISK WHOSE FILESPECK(FILENAME AND PATH) IN ON TOS SAVE STRING READ TO TOS
        addSystemVerb("FORGET","REMOVES A USER DEFINED VERB FROM THE DICTIONARY");

        addSystemVerb("HELP","Print Help Function");
        addSystemVerb("HEX","Set Mode to HEXADECIMAL MODE BASE 16 (SEE BIN)"); 
        addSystemVerb("HEXTODEC","Convert from Hexadecimal to Decimal");
        addSystemVerb("HMAPVARIABLE","DEFINE HASHMAP VARIABLE");
//        addSystemVerbs("HTMLLIST@");//DEPRICATED// LIST THE TAGS OF HTML PAGE WHOSE URL IS ON THE STACK TOP SAVE THIS ON THE STACK TOP
//        addSystemVerbs("HTMLREAD@"); //  TEXT READ THE HTML PAGE WHOSE URL IS ON THE STACK TOP SAVE THIS ON THE STACK TOP

        addSystemVerb("IF","Starts an IF statement");
//        addSystemVerbs("INETADDRESS@");// PUSHES THE IP ADDRESS OF A SERVER ON THESTACK

//        addSystemVerbs("LATEST"); //     WILL CONNECT TO THE WEBSERVER AND DISPLAY LATEST VERSION AVAILABLE FOR DOWNOLOAD
        addSystemVerb("LFS","SEND A NUMBER OF LF'S TO PRINT NUMBER IS TOS");
//        addSystemVerbs("LIST"); // LIST BLOCK N WHERE N IS TOS
        addSystemVerb("LISTVARIABLE","DEFINE LIST VARIABLE");
//        addSystemVerbs("LOAD"); // LOAD BLOCK FROM DISK N WHERE N IS TOS
        addSystemVerb("LOG","Math LOG BASE N function");
        addSystemVerb("LOGBASE10","Math LOG BASE 10 function");
        addSystemVerb("LOOP","Define a LOOP");

        addSystemVerb("MAX","KEEP THE MAXIMUN OF TWO NUMBERS ON THE STACK DROP THE OTHER");
        addSystemVerb("MIN","KEEP THE MINIMUM OF TWO NUMBERS ON THE STACK DROP THE OTHER");
        addSystemVerb("MOD","POP AND DIVIDE THE TOP TWO MUMBERS ON STACK AND PUSH THE MODULUS");

        addSystemVerb("NEGATE","CHANGE THE SIGN OF THE NUMBER AT TOS NGATIVE/POSITIVE");       
        addSystemVerb("NOP","Do Nothing");

        addSystemVerb("OCT","Set Mode to OCTAL MODE BASE 16 (SEE BIN)"); 
        addSystemVerb("OCTALTODEC","Convert Octal to Decimal");
        addSystemVerb("OR","LOGICAL OR");
        addSystemVerb("OSSETLINUX","SET O/S COMPATIBILITY  TO LINUX");
        addSystemVerb("OSSETMAC","SET O/S COMPATIBILITY  TO MAC OS");
//        addSystemVerbs("OSSETSWING","SET O/S COMPATIBILITY  FOR SWING CLIENT
//        addSystemVerbs("OSSETUNITTEST"); // SET O/S COMPATIBILITY  FOR UNIT TESTING
        addSystemVerb("OSSETWEBSERVER","SET O/S COMPATIBILITY  FOR WEB SERVER");
        addSystemVerb("OSSETWINDOWS","SET O/S COMPATIBILITY  TO WINDOWS");
        addSystemVerb("OVER","Stack Operation OVER");

        addSystemVerb("POP","Stack Operation POP FROM STACK");
        addSystemVerb("PUSH","Stack Operation PUSH ONTO STACK");
        addSystemVerb("PWR","Math Power function");

        addSystemVerb("RADTODEG","Convert Radians to Degrees");
        addSystemVerb("RND","Math function which POPS SMALEST AND LARGEST NUMBERS AND PUSHES A RANDOM NUMBER  TO STACK");
        addSystemVerb("ROT","Stack Operation");
        addSystemVerb("ROUND","Math Power function");
//        addSystemVerbs("RSSFEEDREAD"); // READS ALL MESSAGES FROM RSS STREAM WHERE URL IS ONTO THE STACK TOP
//        addSystemVerbs("RSSFEEDMESSAGE@");// READS A MESSAGE WHERE THE INDEX IS THE TOS AND PUSHES THIS MESSAGE TO STACK
//        addSystemVerbs("RSSFEEDSIZE@");// PUSHES THE NUMBER OF MESSAGES TO TOS
//        addSystemVerbs("RSSJ2EEREAD");// READS ALL MESSAGES FROM RSS STREAM FOR THIS PROJECT  ONTO THE STACK TOP

        addSystemVerb("SIN","Math Sin function");
        addSystemVerb("SPACE","PRINT ONE SPACE");
        addSystemVerb("SPACES","PRINT N SPACES WHERE N IS THE TOP OF STACK"); 
        addSystemVerb("SQARE","PUSH THE SQUARE THE TOP OF STACK"); 
        addSystemVerb("SQR","PUSH THE SQUARE ROOT OF THE TOP OF STACK"); 
        addSystemVerb("SWAP","Stack Operation");
//        addSystemVerbs("SYSTEM!");// ADVANCED FEATURE : STORE SYSTEM VARIABLES TO PROPERTIES FILE ON HARD DISK
//        addSystemVerbs("SYSTEM@");// ADVANCED FEATURE : RETRIEVE SYSTEM VARIABLES FROM PROPERTIES FILE ON HARD DISK

        addSystemVerb("TAN","Math Tan function");
        addSystemVerb("TIME@","PUSH DATE TO STACK");
        addSystemVerb("TIMESTAMP@","PUSH DATE TIME STAMP TO STACK");
        addSystemVerb("?TIME","PRINT DATE TO STACK");
        addSystemVerb("?TIMESTAMP","PRINT DATE TIME STAMP TO STACK");

        addSystemVerb("VARIABLE","DEFINE  VARIABLE");

//        addSystemVerbs("WEBPAGE@");// READ AND STORE HTML WEBPAGE ONTO STACK TOP

        addSystemVerb("XOR","LOGICAL EXCLUSIVE OR");
//        addSystemVerbs("XMLMAKE");//ADVANCED FEATURE :  PARSE VARIABLES INTO VARIABLE XML TO GENERATE XML
        addSystemVerb("XML!","SAVE XML TAGS TO VARIABLE XML");
        addSystemVerb("XML@","PUSH XML TAGS TO STACK");
        addSystemVerb("JSON!","SAVE JSON TO VARIABLE JSON");
        addSystemVerb("JSON@","PUSH JSON TO STACK");
    }


}
