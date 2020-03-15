package za.co.bvr.forth.main;

import java.io.Console;
import java.util.Scanner;
import org.apache.commons.lang3.StringUtils;
import za.co.bvr.forth.Forth;
import za.co.bvr.forth.exceptions.LineIsEmptyException;
import za.co.bvr.forth.exceptions.VerbNotInDictionaryException;

/**
 *
 * @author nicm
 */
public class Main {
    

    public static void main(String[] args) {
        Forth forth = new Forth();
        Scanner keyboard = new Scanner(System.in);
        System.out.println("BVR Forth       version 1.0.0\n");
        System.out.println(logo);
        System.out.println("\nType .help for help");
        System.out.print("\n>");
        boolean aborted = false;
        String line ="";
        do {
            
        
            line = keyboard.nextLine();
            aborted = "BYE".equalsIgnoreCase(line);
            if (!aborted) {
                aborted = line.equalsIgnoreCase("EXIT");
            }
            try {
                if (!aborted) {
                    System.out.println(forth.processInput(line));
                    System.out.print(">");
                }
            } catch(VerbNotInDictionaryException x){
                System.out.println("Verb not in Dictionary "+x.getMessage());
                System.out.print(">");
                
            } catch(LineIsEmptyException x){
                System.out.println("The line sent for processing is empty");
                System.out.print(">");
                
            } catch (Exception ex) {
                String reason="";
                if(StringUtils.isNotEmpty(ex.getMessage())){
                    reason+=ex.getMessage()+ " ";
                }
                if(StringUtils.isEmpty(""+ex.getCause())){
                    reason+=ex.getCause();
                }
                System.out.println("The folowing error occured : "+ex.getClass() + " " + reason);
//                ex.printStackTrace();
                System.out.print(">");
            }
        } while (!aborted);
        System.out.println("Bye bye");
    }
    
    
    static String logo = "             _,--\"\"--,_\n"
            + "        _,,-\"          \\\n"
            + "    ,-e\"                ;\n"
            + "   (*             \\     |   BVR FORTH\n"
            + "    \\o\\     __,-\"  )    |\n"
            + "     `,_   (((__,-\"     L___,,--,,__\n"
            + "        ) ,---\\  /\\    / -- '' -'-' ) \n"
            + "      _/ /     )_||   /---,,___  __/\n"
            + "     \"\"\"\"     \"\"\"\"|_ /  ";
}
