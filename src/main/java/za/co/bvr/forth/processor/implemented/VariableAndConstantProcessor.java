package za.co.bvr.forth.processor.implemented;

import za.co.bvr.forth.processor.AbstractProcessor;
import za.co.bvr.forth.stack.ForthStack;
import za.co.bvr.forth.utils.Utilities;
import za.co.bvr.forth.variables.Constants;
import za.co.bvr.forth.variables.Variables;

/**
 *
 * @author nickm 
 */
public class VariableAndConstantProcessor extends AbstractProcessor {

    Constants constants = Constants.INSTANCE;
    Variables variables = Variables.INSTANCE;
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
            if (Utilities.isNumeric(word)) {
                stack.push(word);
            } else if (word.toUpperCase().equals("VARIABLE")) {
                variableType = "VARIABLE";
            }  else if (word.toUpperCase().equals("CONSTANT")) {
                variableType = "CONSTANT";
            } else if (!variableNameFound) {
                variableName = word;
                variableNameFound = true;
                if (variableType.equals("VARIABLE")) {
                    variables.addVariable(variableName, 0);
                }
            } else if (variableNameFound) {
                if (word.equals("!")) {
                    int valueToStoreInVariable = stack.popInt();
                    if (variableType.equals("VARIABLE")) {
                        variables.updateVariable(variableName, valueToStoreInVariable);
                    } else if (variableType.equals("CONSTANT")) {
                        constants.addConstant(variableName, valueToStoreInVariable);
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
    public String preProcess(String line) throws Exception { throw new UnsupportedOperationException("Not supported yet.");  }
    
}
