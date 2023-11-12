package za.co.bvr.forth.processor.implemented;

import lombok.extern.java.Log;
import za.co.bvr.forth.utils.Utilities;

import za.co.bvr.forth.exceptions.UnknownProcessorType;
import za.co.bvr.forth.processor.AbstractLoopProcessor;
import za.co.bvr.forth.processor.AbstractProcessor;
import za.co.bvr.forth.stack.ForthStack;

/**
 *
 * @author nickm
 */
@Log
public class Inputprocessor extends AbstractProcessor {

    ForthStack stack = ForthStack.INSTANCE;

    final String QUOTE = "\"";
    boolean definingNewVerb = false;
    boolean definingLoop = false;
    boolean definingVariable = false;
    boolean definingStringVariable = false;
    boolean definingString = false;
    boolean definingIfStatement = false;
    boolean retrievingVariable=false;
    boolean retrievingStringVariable=false;

    @Override
    public String process(String input) throws UnknownProcessorType, Exception {
        String line = Utilities.removeUnwantedSpaces(input);

        if (line.contains(":")) {
            definingNewVerb = true;
        }else if (line.contains("@")) {
            retrievingVariable=true;
        }else if (line.contains("$@")) {
            retrievingStringVariable=true;
        } else if (".VARIABLES".equalsIgnoreCase(line)) {

        } else if (".CONSTANTS".equalsIgnoreCase(line)) {

        } else if (line.toUpperCase().contains("DO")) {
            definingLoop = true;
        } else if (line.toUpperCase().contains("IF")) {
            definingIfStatement = true;
        } else if (line.toUpperCase().contains("VARIABLE")) {
            if (line.toUpperCase().contains("$")) {
                definingStringVariable = true;
            } else {
                definingVariable = true;
            }
        } else if (line.toUpperCase().contains("CONSTANT")) {
            definingVariable = true;
        } else if (line.toUpperCase().contains(QUOTE)) {
            definingString = true;
        }
        
        if (retrievingVariable) {
            za.co.bvr.forth.processor.implemented.VariableAndConstantProcessor processor = new za.co.bvr.forth.processor.implemented.VariableAndConstantProcessor();
            String result = processor.process(line);
            definingStringVariable = processor.getDefinitionIsNotComplete();
            return result;

        } else if (retrievingStringVariable) {
            return "";

        }else if (definingVariable) {
            za.co.bvr.forth.processor.implemented.VariableAndConstantProcessor processor = new za.co.bvr.forth.processor.implemented.VariableAndConstantProcessor();
            String result = processor.process(line);
            definingVariable = processor.getDefinitionIsNotComplete();
            return result;

        } else if (definingStringVariable) {
            za.co.bvr.forth.processor.implemented.VariableAndConstantProcessor processor = new za.co.bvr.forth.processor.implemented.VariableAndConstantProcessor();
            String result = processor.process(line);
            definingStringVariable = processor.getDefinitionIsNotComplete();
            return result;

        } else if (definingNewVerb) {
            za.co.bvr.forth.processor.implemented.DefineVerbProcessor defineVerbProcessor = new za.co.bvr.forth.processor.implemented.DefineVerbProcessor();
            String result = defineVerbProcessor.process(line);
            definingNewVerb = defineVerbProcessor.getDefinitionIsNotComplete();
            return result;
        } else if (definingLoop) {
            AbstractLoopProcessor loopProcessor = new za.co.bvr.forth.processor.implemented.DoLoopProcessor();
            String result = loopProcessor.process(line);
            definingLoop = loopProcessor.getDefinitionIsNotComplete();
            return result;
        } else if (definingIfStatement) {
            za.co.bvr.forth.processor.implemented.IfStatementProcessor ifStatementProcessor = new IfStatementProcessor();
            String result = ifStatementProcessor.process(line);
            definingIfStatement = ifStatementProcessor.getDefinitionIsNotComplete();
            return result;
        } else if (definingString) {
            line = storingString(line);
            AbstractProcessor lineProcessor = new za.co.bvr.forth.processor.implemented.LineProcessor();
            return lineProcessor.process(line);
        } else {
            AbstractProcessor lineProcessor = new za.co.bvr.forth.processor.implemented.LineProcessor();
            return lineProcessor.process(line);
        }
    }

    private String storingString(String input) {
        String processLine = new String();
        String[] lines = input.split(QUOTE);
        int count = 0;
        int index = 0;
        for (String line : lines) {
            if (line.length() > 0) {
                if (index == 1) {
                    stack.push(line);
                } else if (count > 0) {
                    processLine += " " + line;
                    count++;
                } else {
                    processLine = line;
                    count++;
                }
            }
            index++;
        }

        return processLine;
    }

    @Override
    public String preProcess(String line) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String postProcess(String line) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean getDefinitionIsNotComplete() throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
