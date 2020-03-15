package za.co.bvr.forth.processor.implemented;

import java.util.List;
import org.apache.commons.lang3.StringUtils;
import za.co.bvr.forth.dtos.ExecutionPojo;
import za.co.bvr.forth.processor.AbstractProcessor;
import za.co.bvr.forth.stack.ForthStack;
import za.co.bvr.forth.utils.Utilities;
import za.co.bvr.forth.variables.VariablesStore;

/**
 *
 * @author nickm 
 */
public class VariableAndConstantProcessor extends AbstractProcessor {    
    VariablesStore variables = VariablesStore.INSTANCE;
    ForthStack stack = ForthStack.INSTANCE;

    boolean definitionIsNotComplete = true;


    @Override
    public String process(String line) throws Exception {
        String[] words = line.split(" ");
        StringBuilder verbsToExecute = new StringBuilder();
        String variableName = null;
        String variableType = null;
        boolean variableNameFound = false;
        int count = 0;

        for (String word : words) {
            if (StringUtils.isNumeric(word)) {
                stack.push(word);
            } else if (word.toUpperCase().equals("VARIABLE")) {
                variableType = "VARIABLE";
            }  else if (word.toUpperCase().equals("CONSTANT")) {
                variableType = "CONSTANT";
            } else if (!variableNameFound) {
                variableName = word;
                variableNameFound = true;
                if (variableType.equals("VARIABLE")) {
                    variables.addVariable(variableName);
                }
            } else if (variableNameFound) {
                if (word.equals("!")) {
                    String valueToStoreInVariable = stack.pop();
                    if (variableType.equals("VARIABLE")) {
                        variables.updateVariable(variableName, valueToStoreInVariable);
                    } else if (variableType.equals("CONSTANT")) {
                        variables.addConstant(variableName, valueToStoreInVariable);
                    }
                } else {
                    if (count > 0) {
                        verbsToExecute.append(" ");
                    }
                    verbsToExecute.append(word);
                    count++;
                }
            }
        }
        

        LineProcessor lineProcessor = new LineProcessor();
        return lineProcessor.process(line);
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
        return definitionIsNotComplete;
    }

    
}
