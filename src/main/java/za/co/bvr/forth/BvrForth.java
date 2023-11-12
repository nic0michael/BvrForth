package za.co.bvr.forth;

import za.co.bvr.forth.utils.StringUtils;

/**
 * @author nickm
 */
public class BvrForth {

  public static void main(String[] args) throws Exception {

    String input = ">";
    do {
        input = StringUtils.getStringFromUser(input);

        if (!input.equalsIgnoreCase("BYE")) {
            input = Forth.processInput(input);
//            System.out.println(input);
        }
    }while (!input.equalsIgnoreCase("BYE")) ;
    System.out.println("OK");
  }
}
