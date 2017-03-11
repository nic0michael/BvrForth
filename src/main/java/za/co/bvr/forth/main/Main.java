package za.co.bvr.forth.main;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import za.co.bvr.forth.Forth;

/**
 *
 * @author nickm
 */
public class Main {

    public static void main(String[] args) {
        Forth forth = new Forth();
        Scanner keyboard = new Scanner(System.in);
        System.out.println("BVR Forth       version 1.0.0\n");
        System.out.println(logo);
        System.out.println("\nType .help for help\n");
        System.out.print(">");
        boolean aborted = false;
        do {
            String line = keyboard.nextLine();
            aborted = line.equalsIgnoreCase("BYE");
            try {
                if (!aborted) {
                    System.out.println(forth.processInput(line));
                    System.out.print(">");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } while (!aborted);
        System.out.println("Bye bye");
    }



    static String logo = "             _,--\"\"--,_\n"
            + "        _,,-\"          \\\n"
            + "    ,-e\"                ;\n"
            + "   (*             \\     |\n"
            + "    \\o\\     __,-\"  )    |\n"
            + "     `,_   (((__,-\"     L___,,--,,__\n"
            + "        ) ,---\\  /\\    / -- '' -'-' )\n"
            + "      _/ /     )_||   /---,,___  __/\n"
            + "     \"\"\"\"     \"\"\"\"|_ /  ";

}
