package za.co.bvr.forth.processor.implemented;

import java.util.List;
import lombok.extern.java.Log;
import org.apache.commons.lang3.StringUtils;
import za.co.bvr.forth.dictionary.ForthDictionary;
import za.co.bvr.forth.dtos.ExecutionPojo;
import za.co.bvr.forth.processor.AbstractProcessor;
import za.co.bvr.forth.stack.ForthStack;
import za.co.bvr.forth.utils.Utilities;
import za.co.bvr.forth.variables.VariablesStore;

/**
 *
 * @author nickm
 */
@Log
public class VerbPreProcessor extends AbstractProcessor {

    ForthStack stack = ForthStack.INSTANCE;
    ForthDictionary dictionary = ForthDictionary.INSTANCE;
    VariablesStore variables = VariablesStore.INSTANCE;
//    ExecutionQueue queue = ExecutionQueue.INSTANCE;
//    ExecutionQueueListener executionQueueListener= ExecutionQueueListener.INSTANCE;
    VerbProcessor verbProcessor=VerbProcessor.INSTANCE; 

 

    @Override
    public String process(String line) throws Exception {
        StringBuilder result = new StringBuilder();
        String[] lineItems = line.toUpperCase().split(" ");
//        log.info("processing line : "+line);
        for (String lineItem : lineItems) {
//            log.info("processing lineItem : "+lineItem);
            if (StringUtils.isNumeric(lineItem)) {
                stack.push(lineItem);
            } else if (variables.isVariable(lineItem)) {
                variables.setCurrentvariableName(lineItem);
            } else {
//                log.info("Added lineItem : "+lineItem);
                result.append(verbProcessor.executeVerb(lineItem));
            }
        }
        return result.toString();
    }

    @Override
    public List<ExecutionPojo> preProcess(String line) throws Exception {
        return null;
    }

    @Override
    public String postProcess(List<ExecutionPojo> executions) throws Exception {
        return null;
    }

    @Override
    public boolean getDefinitionIsNotComplete() {
        return false;
    }


}
