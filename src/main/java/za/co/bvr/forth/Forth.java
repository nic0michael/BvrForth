package za.co.bvr.forth;


import za.co.bvr.forth.processor.implemented.Processor;

/** 
 *
 * @author nickm
 */
public class Forth {    
    
    public String processInput(String input) throws Exception{
//        Inputprocessor processor =new Inputprocessor();
        Processor processor =new Processor();
        return processor.process(input);
    }
}
