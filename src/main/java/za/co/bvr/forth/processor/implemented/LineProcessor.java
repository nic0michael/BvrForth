package za.co.bvr.forth.processor.implemented;

import java.util.ArrayList;
import java.util.List;
import lombok.extern.java.Log;
import org.apache.commons.lang3.StringUtils;
import za.co.bvr.forth.dictionary.ForthDictionary;
import za.co.bvr.forth.dtos.ExecutionPojo;
import za.co.bvr.forth.enums.ExecutionType;
import za.co.bvr.forth.processor.AbstractProcessor;
import za.co.bvr.forth.stack.ForthStack;
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
        
        log.info("LineProcessor processing line : "+line+"\n\n");
        if(StringUtils.isEmpty(line)){
            return "";
        }
        StringBuilder result = new StringBuilder();
        String[] lineItems = line.split(" ");
        for (String lineItem : lineItems) {
            ExecutionPojo execution =new ExecutionPojo();
            
            if (StringUtils.isNumeric(lineItem)) {
                execution.setLineItem(lineItem);
                execution.setExecutionType(ExecutionType.NUMBER);
                executions.add(execution);
                
            }else if (variables.isVariable(lineItem.toUpperCase())) {
                execution.setLineItem(lineItem.toUpperCase());
                execution.setExecutionType(ExecutionType.VARIABLE);
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
                    
                    if (StringUtils.isNumeric(definition)) {
//                        log.info("LineProcessor is numeric : "+definition);
                        execution.setLineItem(definition);
                        execution.setExecutionType(ExecutionType.NUMBER);
                        executions.add(execution);

                    } else if (variables.isVariable(definition.toUpperCase())) {
//                        log.info("LineProcessor is variable : "+definition);
                        execution.setLineItem(definition.toUpperCase());
                        execution.setExecutionType(ExecutionType.VARIABLE);
                        executions.add(execution);

                    } else{
//                        log.info("LineProcessor is verb"+count+" : "+definition);
                        execution.setLineItem(definition);
                        execution.setExecutionType(ExecutionType.VERB);
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

    @Override
    public List<ExecutionPojo> preProcess(String line) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean getDefinitionIsNotComplete() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
