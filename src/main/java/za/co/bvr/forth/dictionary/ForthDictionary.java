package za.co.bvr.forth.dictionary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import za.co.bvr.forth.exceptions.VerbNotInDictionaryException;
import za.co.bvr.forth.utils.Utilities;

/** 
 *
 * @author nickm
 */
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
            if(verbIsNotSystemVerb(name)){
                Verb previousVerb = new Verb(name, definition, compiledDefinition);
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
            definition=systemVerbDefinitions.get(verbName.toUpperCase());
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
        return compiledDefinition;
    }

    public int size() {
        return verbDefinitions.size();
    }

    public String showVerbs() {
        StringBuilder result = new StringBuilder();
        int loop = 0;
        for (Map.Entry<String, String> entry : systemVerbDefinitions.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            if (loop > 0) {
                result.append(" ");
            }
            result.append(key);
            loop++;
        }        
        for (Map.Entry<String, String> entry : verbDefinitions.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            if (loop > 0) {
                result.append(" ");
            }
            result.append(key);
            loop++;
        }
        String verbs = result.toString();
        String sortedNames = Utilities.sortVerbNamesInString(verbs);
        return sortedNames;
    }

    public String showVerbDetails() {
        StringBuilder result = new StringBuilder();
        int loop = 0;
        for (Map.Entry<String, String> entry : verbDefinitions.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            if (loop > 0) {
                result.append("\n");
            }
            result.append(key);
            result.append(" : ");
            result.append(value);
            loop++;
        }
        return result.toString();
    }

    public void addSystemVerbs(String systemVerbNames) {
        String[] verbNames = systemVerbNames.split(" ");
        for (String verbName : verbNames) {
            Verb verb = new Verb(verbName, verbName, verbName);
            addSystemVerbToSystemDictionary(verb);
        }
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
        String definition=systemVerbDefinitions.get(name.toUpperCase());
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
        String name = verb.getName().toUpperCase();
        String description = "SYSTEM VERB";
        systemVerbDefinitions.put(name, verb.getDefinition());
        systemVerbCompiledDefinitions.put(name, verb.getCompiledDefinition());
        systemVerbDescriptions.put(name, description);
    }

    private void loadDictionary() {

        addSystemVerbs("BYE");
        addSystemVerbs(".HELP");
        addSystemVerbs("NOP : ;");
        addSystemVerbs("CONSTANT");// DEFINE CONSTANT
        addSystemVerbs("VARIABLE");// DEFINE  VARIABLE
        addSystemVerbs("LISTVARIABLE");// DEFINE LIST VARIABLE
        addSystemVerbs("HMAPVARIABLE");// DEFINE HASHMAP VARIABLE
        addSystemVerbs("$CONSTANT");//ADVANCED FEATURE :  DEFINE STRING CONSTANT WE ALSO HAVE STRING CONSTANTS
        addSystemVerbs("$VARIABLE");// DEFINE STRING VARIABLE
        addSystemVerbs("DO DROP DUP");
        addSystemVerbs("?DUP");// QDUP
        addSystemVerbs("AND");// LOGICAL AND
        addSystemVerbs("OR");// LOGICAL OR
        addSystemVerbs("XOR");// LOGICAL EXCLUSIVE OR
        addSystemVerbs(".");// POP AND PRINT TOS
        addSystemVerbs("+ - * / 1- 1+ 2- 2+");
        addSystemVerbs("2*");//SHIFT LEFT
        addSystemVerbs("2/");//SHIFT RIGHT
        addSystemVerbs(">>");//ADVANCED FEATURE :  BORROWED FROM JAVA BIT SHIFT
        addSystemVerbs(">>>");//ADVANCED FEATURE :  BORROWED FROM JAVA BIT SHIFT
        addSystemVerbs("<<");//ADVANCED FEATURE :  BORROWEWD FROM JAVA BIT SHIFT
        addSystemVerbs("!");// STORE     TO VARIABLE
        addSystemVerbs("+!");// APPEND TO VARIABLE
        addSystemVerbs("-!");// APPEND TO VARIABLE
        addSystemVerbs("$!");// STORE TO STRING VARIABLE
        addSystemVerbs("$L");// START OF STRING (THE STRING ENDS WITH A ")
        addSystemVerbs("@");// FETCH FROM NUMERIC VARIABLE
        addSystemVerbs("?");// PRINT NUMERIC VARIABLE
        addSystemVerbs("ALLOT");// STORE TOS TO NUMERIC VARIABLE
        addSystemVerbs("BASE32DECODE");//BASE 32 DECODE TEXT ON STACK TOP AND PUSH RESULT TO STACK TOP
        addSystemVerbs("BASE32ENCODE");//BASE 32 ENCODE TEXT ON STACK TOP AND PUSH RESULT TO STACK TOP
        addSystemVerbs("BASE64DECODE");//BASE 32 DECODE TEXT ON STACK TOP AND PUSH RESULT TO STACK TOP
        addSystemVerbs("BASE64ENCODE");//BASE 32 ENCODE TEXT ON STACK TOP AND PUSH RESULT TO STACK TOP          
        addSystemVerbs("BIN");// BINARY MODE BASE 2 (SEE DEC)
        addSystemVerbs("BYE");// QUIT PROGRAM
        addSystemVerbs("COPY");//BLOCK TO BLOCK COPY
        addSystemVerbs("COUNTER");//PUSH NR OF CLOCK TICKS TO TOS // IT RETURNS MILLSECONDS FROM JAN 1, 1970.
        addSystemVerbs("CR");// SENDS CR OR \N     
        addSystemVerbs("DATE@");//ADVANCED FEATURE :  PUSH DATE TO STACK
        addSystemVerbs("DATEUSA@");//ADVANCED FEATURE :  PUSH DATE TO STACK         
        addSystemVerbs("DATEBRITISH@");//ADVANCED FEATURE :  PUSH DATE TO STACK                   
        addSystemVerbs("DATESIMPLEFORMAT@");//ADVANCED FEATURE :  PUSH DATE TO STACK WHERE FORMAT STRING IS ON TOS YYYY-MM-DD HH:MM:SS
        addSystemVerbs("DATETIME@");//ADVANCED FEATURE :  PUSH DATE TO STACK
        addSystemVerbs("DELAY");//ADVANCED FEATURE :  100MS DELAY
        addSystemVerbs("DEC");// DECIMAL MODE BASE 10 (SEE BIN)                    
        addSystemVerbs("EMIT");// SEND SPACES NR OF SPACES IS TOS
        addSystemVerbs("EXEC");// EXECUTE AN O/S COMMAND
        addSystemVerbs("EXECNANO");// EDIT FILE WITH NANO  FIRST PUSH PATH TO FILE TO EDIT AS A STRING
        addSystemVerbs("EXECKATE");// EDIT FILE WITH KATE FIRST PUSH PATH TO FILE TO EDIT AS A STRING
        addSystemVerbs("EXECKWRITE");//EDIT FILE WITH KWRITE FIRST PUSH PATH TO FILE TO EDIT AS A STRING
        addSystemVerbs("EXECVI");// EDIT FILE WITH VI FIRST PUSH PATH TO FILE TO EDIT AS A STRING
        addSystemVerbs("EXECVIM");// EDIT FILE WITH VIM FIRST PUSH PATH TO FILE TO EDIT AS A STRING
        addSystemVerbs("EXECWORDPAD");// EDIT FILE WITH WORDPAD FIRST PUSH PATH TO FILE TO EDIT AS A STRING
        addSystemVerbs("EXECNOTEPAD");// EDIT FILE WITH NOTEPAD FIRST PUSH PATH TO FILE TO EDIT AS A STRING
        addSystemVerbs("EDIT");// MULTIPLE TEXT EDITOR PUSH (1 TO 7) TO SELECT EDITOR THE PUSH PATH TO FILE TO EDIT AS A STRING 
        addSystemVerbs("FLUSH");//FLUSHES CHANGED BLOCKS TO DISK                   
        addSystemVerbs("FILE@");//READ A FILE FROM DISK WHOSE FILESPECK(FILENAME AND PATH) IN ON TOS SAVE STRING READ TO TOS
        addSystemVerbs("FORGET");// REMOVES A VERB FROM THE DICTIONARY (DICTIONARYINDEXMAP)
        addSystemVerbs("HELP");// DISPLAY THIS LIST OF VERBS
        addSystemVerbs("HTMLLIST@");//DEPRICATED// LIST THE TAGS OF HTML PAGE WHOSE URL IS ON THE STACK TOP SAVE THIS ON THE STACK TOP
        addSystemVerbs("HTMLREAD@"); //  TEXT READ THE HTML PAGE WHOSE URL IS ON THE STACK TOP SAVE THIS ON THE STACK TOP
        addSystemVerbs("INETADDRESS@");// PUSHES THE IP ADDRESS OF A SERVER ON THESTACK
        addSystemVerbs("LATEST"); //     WILL CONNECT TO THE WEBSERVER AND DISPLAY LATEST VERSION AVAILABLE FOR DOWNOLOAD
        addSystemVerbs("LFS"); // SEND A NUMBER OF LF'S TO PRINT NUMBER IS TOS
        addSystemVerbs("LOAD"); // LOAD BLOCK FROM DISK N WHERE N IS TOS
        addSystemVerbs("LOOP");
        addSystemVerbs("LIST"); // LIST BLOCK N WHERE N IS TOS
        addSystemVerbs("MAX"); // KEEP THE MAXIMUN OF TWO NUMBERS ON THE STACK DROP THE OTHER
        addSystemVerbs("MIN");// KEEP THE MINIMUM OF TWO NUMBERS ON THE STACK DROP THE OTHER
        addSystemVerbs("MOD");// POP AND DIVIDE THE TOP TWO MUMBERS ON STACK AND PUSH THE MODULUS
        addSystemVerbs("NEGATE");// CHANGE THE SIGN OF THE NUMBER AT TOS NGATIVE/POSITIVE
        addSystemVerbs("OVER");
        addSystemVerbs("OSSETLINUX"); // SET O/S COMPATIBILITY  TO LINUX
        addSystemVerbs("OSSETMAC"); // SET O/S COMPATIBILITY  TO MAC OS
        addSystemVerbs("OSSETSWING"); // SET O/S COMPATIBILITY  FOR SWING CLIENT
        addSystemVerbs("OSSETUNITTEST"); // SET O/S COMPATIBILITY  FOR UNIT TESTING
        addSystemVerbs("OSSETWEBSERVER"); // SET O/S COMPATIBILITY  FOR WEB SERVER
        addSystemVerbs("OSSETWINDOWS"); // SET O/S COMPATIBILITY  TO WINDOWS
        addSystemVerbs("PUSH");// PUSH ONTO STACK // INTERNAL OPERATION
        addSystemVerbs("POP");// POP FROM STACK // INTERNAL OPERATION
        addSystemVerbs("ROT");
        addSystemVerbs("RND");// PUSH A RANDOM NUMBER RND(10) TO STACK
        addSystemVerbs("RSSFEEDREAD"); // READS ALL MESSAGES FROM RSS STREAM WHERE URL IS ONTO THE STACK TOP
        addSystemVerbs("RSSFEEDMESSAGE@");// READS A MESSAGE WHERE THE INDEX IS THE TOS AND PUSHES THIS MESSAGE TO STACK
        addSystemVerbs("RSSFEEDSIZE@");// PUSHES THE NUMBER OF MESSAGES TO TOS
        addSystemVerbs("RSSJ2EEREAD");// READS ALL MESSAGES FROM RSS STREAM FOR THIS PROJECT  ONTO THE STACK TOP
        addSystemVerbs("SPACE");// PRINT ONE SPACE
        addSystemVerbs("SPACES");// PRINT N SPACES WHERE N IS TOS
        addSystemVerbs("SWAP");
        addSystemVerbs("SYSTEM!");// ADVANCED FEATURE : STORE SYSTEM VARIABLES TO PROPERTIES FILE ON HARD DISK
        addSystemVerbs("SYSTEM@");// ADVANCED FEATURE : RETRIEVE SYSTEM VARIABLES FROM PROPERTIES FILE ON HARD DISK
        addSystemVerbs("TIME@");// ADVANCED FEATURE : PUSH DATE TO STACK
        addSystemVerbs("TIMESTAMP@");//ADVANCED FEATURE :  PUSH DATE TIME STAMP TO STACK
        addSystemVerbs("WEBPAGE@");// READ AND STORE HTML WEBPAGE ONTO STACK TOP
        addSystemVerbs("XMLMAKE");//ADVANCED FEATURE :  PARSE VARIABLES INTO VARIABLE XML TO GENERATE XML
        addSystemVerbs("XML!");// ADVANCED FEATURE : SAVE XML TAGS TO VARIABLES XML
        addSystemVerbs("XML@");// ADVANCED FEATURE : PUSH XML TAGS TO STACK
        addSystemVerbs(".DATE");// ADVANCED FEATURE : PRINT DATE IN Y2K BANKING FORMAT (Y2K COMPLIANT DATE)
        addSystemVerbs(".DATEUSA");//ADVANCED FEATURE :  PRINT DATE USA FORMAT  (Y2K COMPLIANT DATE)
        addSystemVerbs(".DATEBRITISH");//ADVANCED FEATURE :  PRINT DATE BRITISH FORMAT  (Y2K COMPLIANT DATE)
        addSystemVerbs(".DATESF");// ADVANCED FEATURE : PRINT Y2K COMPLIANT DATE USING FORMAT STRING ON STACK TOP
        addSystemVerbs(".DATESIMPLEFORMAT");// ADVANCED FEATURE : PRINT Y2K COMPLIANT DATE USING FORMAT STRING ON STACK TOP YYYY-MM-DD HH:MM:SS
        addSystemVerbs(".DATETIME");//ADVANCED FEATURE :  PRINT DATE AND TIME  (Y2K COMPLIANT DATE)
        addSystemVerbs(".DAY");// PRINTS THE DAY
        addSystemVerbs(".DICT");//ADVANCED FEATURE : LIST THE DICTIONARY
        addSystemVerbs(".D");//ADVANCED FEATURE : LIST THE DICTIONARY
        addSystemVerbs(".DICTDEF");//ADVANCED FEATURE : LIST ALL THE DEFINITIONS OF ALL THE VERBS
        addSystemVerbs(".ERRORLOG");// PRINT ERROR LOG
        addSystemVerbs(".H");// HELP
        addSystemVerbs(".HASHMAPVARIABLES");//ADVANCED FEATURE : LISTS THE NAMES OF ALL THE HASHMAP VARIABLES
        addSystemVerbs(".HM");//ADVANCED FEATURE : LISTS THE NAMES OF ALL THE HASHMAP VARIABLES
        addSystemVerbs(".INETADDRESS");// PRINTS THE IP ADDRESS OF A SERVER 
        addSystemVerbs(".LISTVARIABLES");//ADVANCED FEATURE : LISTS THE NAMES OF ALL THE LIST VARIABLES
        addSystemVerbs(".L");//ADVANCED FEATURE : LISTS THE NAMES OF ALL THE LIST VARIABLES
        addSystemVerbs(".MONTH");// PRINTS THE MONTH
        addSystemVerbs(".MTH");// PRINTS THE MONTH
        addSystemVerbs(".OS");// PRINT OPERATING SYSTEM COMPATIBILITY
        addSystemVerbs(".RSSFEEDMESSAGE");// PRINTS A MESSAGE WHERE THE INDEX IS THE TOS 
        addSystemVerbs(".STACK");// ADVANCED FEATURE : LIST THE STACK
        addSystemVerbs(".STATUS");
        addSystemVerbs(".S");// ADVANCED FEATURE : LIST THE STACK
        addSystemVerbs(".TIME");// ADVANCED FEATURE : PRINT TIME
        addSystemVerbs(".TIMESTAMP");// PRINT DATE
        addSystemVerbs(".VARIABLES");//ADVANCED FEATURE : LIST THE VARIABLES AND THEIR CONTENT
        addSystemVerbs(".V");//ADVANCED FEATURE : LIST THE VARIABLES AND THEIR CONTENT
        addSystemVerbs(".YEAR");// PRINTS THE YEAR
        addSystemVerbs(".YR");// PRINTS THE YEAR               
        addSystemVerbs("$@");//PUST THE VALUE OF A STRING VARIABLE TO TOS
        addSystemVerbs("EXECWRITE");
        addSystemVerbs("TWO_MINUS");
    }

}
