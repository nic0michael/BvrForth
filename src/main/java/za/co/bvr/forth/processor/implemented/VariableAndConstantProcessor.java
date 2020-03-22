package za.co.bvr.forth.processor.implemented;

import org.apache.commons.lang3.StringUtils;
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
    String variableName;

    @Override
    public String process(String line) throws Exception {
        String[] words = line.split(" ");
        StringBuilder verbsToExecute = new StringBuilder();
        String variableName = null;
        String variableType = null;
        boolean variableNameFound = false;
        int count = 0;

        for (String word : words) {
            if (variables.isVariable(word)) {
                variableName=word;
                
            } else if (Utilities.isNumeric(word)) {
                stack.push(word);
            } else if (word.toUpperCase().equals("@")) { 
                String value =variables.getValue(variableName);
                if(StringUtils.isNotEmpty(value)){
                    stack.push(value); 
                }
                
            } else if (word.toUpperCase().equals("$@")) {
                String value =variables.getValue(variableName);
                if(StringUtils.isNotEmpty(value)){
                    stack.push(value); 
                }
                
            } else if (word.toUpperCase().equals("VARIABLE")) {
                variableType = "VARIABLE";
            } else if (word.toUpperCase().equals("$VARIABLE")) {
                variableType = "$VARIABLE";
            } else if (word.toUpperCase().equals("CONSTANT")) {
                variableType = "CONSTANT";
            } else if (!variableNameFound) {
                variableName = word;
                variableNameFound = true;
                if (variableType.equals("VARIABLE")) {
                    variables.addVariable(variableName);
                } else if (variableType.equals("$VARIABLE")) {
                    variables.addStringVariable(variableName);
                }

            } else if (variableNameFound) {
                String valueToStoreInVariable;
                if (word.equals("!")) {
                    valueToStoreInVariable = stack.pop();
                    if (variableType.equals("VARIABLE")) {
                        variables.updateVariable(variableName, valueToStoreInVariable);

                    } else if (variableType.equals("CONSTANT")) {
                        variables.addConstant(variableName, valueToStoreInVariable);
                    }
                }
                if (word.equals("$!")) {
                    valueToStoreInVariable = stack.pop();
                    if (variableType.equals("$VARIABLE")) {
                        variables.updateStringVariable(variableName, valueToStoreInVariable);
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

        return postProcess(verbsToExecute.toString());
    }

    @Override
    public String postProcess(String line) throws Exception {
        LineProcessor lineProcessor = new LineProcessor();
        return lineProcessor.process(line);
    }

    @Override
    public boolean getDefinitionIsNotComplete() throws Exception {
        return definitionIsNotComplete;
    }

    @Override
    public String preProcess(String line) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
