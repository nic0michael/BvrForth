package za.co.bvr.forth.processor;

import java.util.List;
import za.co.bvr.forth.dtos.ExecutionPojo;

/**
 *
 * @author nickm 
 */
public abstract class AbstractProcessor {    

    public abstract String process(String line) throws Exception;
    
    public abstract List<ExecutionPojo> preProcess(String line) throws Exception;
    
    public abstract String postProcess(List<ExecutionPojo> executions) throws Exception;
    
    public abstract boolean getDefinitionIsNotComplete();
}
