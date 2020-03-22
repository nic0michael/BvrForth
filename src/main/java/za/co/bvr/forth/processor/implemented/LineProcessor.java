package za.co.bvr.forth.processor.implemented;

import java.util.ArrayList;
import java.util.List;
import lombok.extern.java.Log;
import za.co.bvr.forth.dictionary.ForthDictionary;
import za.co.bvr.forth.dtos.ExecutionPojo;
import za.co.bvr.forth.enums.ExecutionType;
import za.co.bvr.forth.processor.AbstractProcessor;
import za.co.bvr.forth.stack.ForthStack;
import za.co.bvr.forth.utils.Utilities;
import za.co.bvr.forth.variables.VariablesStore;

/**
 *
 * @author nickm 
 */
@Log
class LineProcessor extends AbstractProcessor {

    ForthDictionary dictionary = ForthDictionary.INSTANCE;
    ForthStack stack =ForthStack.INSTANCE;
    VariablesStore variables = VariablesStore.INSTANCE;


    @Override
    public String process(String line) throws Exception {
        List<ExecutionPojo> executions =new ArrayList<>();
        
//        log.info("LineProcessor processing line : "+line+"\n\n");
        if(Utilities.isEmpty(line)){
            return "";
        }
        StringBuilder result = new StringBuilder();
        String[] lineItems = line.split(" ");
        for (String lineItem : lineItems) {
            ExecutionPojo execution =new ExecutionPojo();
            
            if (Utilities.isNumeric(lineItem)) {
                execution=makeExecutionPojo(lineItem, ExecutionType.NUMBER);
                executions.add(execution);
                
            }else if (variables.isVariable(lineItem.toUpperCase())) {
                execution=makeExecutionPojo(lineItem.toUpperCase(), ExecutionType.VARIABLE);
                executions.add(execution);
                
            }else{
//                log.info("LineProcessor lineItem : "+lineItem);
                String def = dictionary.getCompiledDefinition(lineItem).toUpperCase();
                String[] definitions = def.split(" ");
//                log.info("LineProcessor def : "+def);
                int count=1;
                for (String definition : definitions) {
                    execution =new ExecutionPojo();
//                    log.info("LineProcessor definition"+count+" : "+definition);
                    
                    if (Utilities.isNumeric(definition)) {
//                        log.info("LineProcessor is numeric : "+definition);
                        execution=makeExecutionPojo(definition, ExecutionType.NUMBER);                        
                        executions.add(execution);

                    } else if (variables.isVariable(definition.toUpperCase())) {
//                        log.info("LineProcessor is variable : "+definition);
                        execution=makeExecutionPojo(definition.toUpperCase(), ExecutionType.VARIABLE);
                        executions.add(execution);

                    } else{
//                        log.info("LineProcessor is verb"+count+" : "+definition);
                        execution=makeExecutionPojo(definition.toUpperCase(), ExecutionType.VERB);
                        executions.add(execution);
                    }                    
                    count++;
                }
            }
            
//            log.info("LineProcessor executions : "+executions);

        }

        return postProcess(executions);
    }

    public String postProcess(List<ExecutionPojo> executions) throws Exception {
        VerbPreProcessor verbProcessor = new VerbPreProcessor();
        StringBuilder results = new StringBuilder();
        for (ExecutionPojo execution : executions) {
            switch(execution.getExecutionType().name()){
                case "NUMBER" :
                    stack.push(execution.getLineItem());                    
                    break;
                    
                case "VARIABLE" :
                    String variableResult=verbProcessor.process(execution.getLineItem());
                    results.append(variableResult);
//                    log.info("LineProcessor variableResult : "+variableResult);
                    break;                    
                       
                case "VERB" :
                    String verb=execution.getLineItem();
                    String verbResult=verbProcessor.process( verb);
                    results.append(verbResult);
//                    log.info("LineProcessor verb: "+verb+" verbResult: "+verbResult);
                    break;                    
            }
        }
        
        return results.toString();
    } 
   
    private ExecutionPojo makeExecutionPojo(String lineItem,ExecutionType executionType){
        ExecutionPojo execution =new ExecutionPojo();
        execution.setLineItem(lineItem);
        execution.setExecutionType(executionType);
        return  execution;               
    }
    
    @Override
    public String preProcess(String line) throws Exception {throw new UnsupportedOperationException("Not supported yet.");}
    
    @Override
    public String postProcess(String line) throws Exception {throw new UnsupportedOperationException("Not supported yet."); }

    @Override
    public boolean getDefinitionIsNotComplete() throws Exception {throw new UnsupportedOperationException("Not supported yet.");}

}
