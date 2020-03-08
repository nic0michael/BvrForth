package za.co.bvr.forth.processor.implemented;

import lombok.extern.java.Log;
import za.co.bvr.forth.dictionary.ForthDictionary;
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
    public String preProcess(String line) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String process(String line) throws Exception {
        StringBuilder result = new StringBuilder();
        String[] lineItems = line.split(" ");
//        log.info("processing line : "+line);
        for (String lineItem : lineItems) {
//            log.info("processing lineItem : "+lineItem);
            if (Utilities.isNumeric(lineItem)) {
                stack.push(lineItem);
            } else if (variables.isVariable(lineItem.toUpperCase())) {
                variables.setCurrentvariableName(lineItem);
            } else {
//                log.info("Added lineItem : "+lineItem);
                result.append(verbProcessor.executeVerb(lineItem));
            }
        }
        return result.toString();
    }

    @Override
    public String postProcess(String line) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean getDefinitionIsNotComplete() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


}
