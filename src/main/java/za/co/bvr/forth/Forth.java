package za.co.bvr.forth;

import za.co.bvr.forth.processor.implemented.Inputprocessor;

/** 
 *
 * @author nickm
 */
public class Forth {    
    
    public String processInput(String input) throws Exception{
        Inputprocessor processor =new Inputprocessor();
        return processor.process(input);
    }
}
