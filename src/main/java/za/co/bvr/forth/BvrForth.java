package za.co.bvr.forth;

import za.co.bvr.forth.utils.StringUtils;

/**
 * @author nickm
 */
public class BvrForth {

  public static void main(String[] args) {
    System.out.println("\n\n\n\nWelcome to BVR Forth");
    System.out.println("Type .HELP for help");

    String input = ">";
    do {
      try {
        input = StringUtils.getStringFromUser(input).toUpperCase();

        if (!input.equalsIgnoreCase("BYE")) {
            input = Forth.processInput(input);
//            System.out.println(input);
        }
      } catch (Exception e) {
        System.out.println(e.getMessage());
        input = ">";
      }
    }while (!input.equalsIgnoreCase("BYE")) ;
    System.out.println("OK");
  }
}
