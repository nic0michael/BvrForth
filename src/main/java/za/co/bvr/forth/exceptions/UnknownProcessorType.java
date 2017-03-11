package za.co.bvr.forth.exceptions;

/**
 *
 * @author nickm 
 */
public class UnknownProcessorType extends Exception {
     public UnknownProcessorType(){}
     
     public UnknownProcessorType(String loopType) {
         super(loopType);
     }    
}
