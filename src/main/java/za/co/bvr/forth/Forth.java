package za.co.bvr.forth;

import za.co.bvr.forth.process.ProcessInput;

/**
 *
 * @author nickm
 */
public class Forth {
    public static String processInput(String input) throws Exception{
//        return "3";
        ProcessInput process =new ProcessInput();
        return process.process(input);
    }
}
