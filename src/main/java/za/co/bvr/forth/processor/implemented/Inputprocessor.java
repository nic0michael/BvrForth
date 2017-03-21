package za.co.bvr.forth.processor.implemented;

import za.co.bvr.forth.utils.Utilities;
import za.co.bvr.forth.exceptions.UnknownProcessorType;
import za.co.bvr.forth.processor.AbstractLoopProcessor;
import za.co.bvr.forth.processor.AbstractProcessor;
import za.co.bvr.forth.stack.ForthStack;

/**
 *
 * @author nickm
 */
public class Inputprocessor extends AbstractProcessor {

    ForthStack stack = ForthStack.INSTANCE;

    final String QUOTE = "" + '"';
    boolean definingNewVerb = false;
    boolean definingLoop = false;
    boolean definingVariable = false;
    boolean definingString = false;

    @Override
    public String process(String input) throws UnknownProcessorType, Exception {
        String line = Utilities.removeUnwantedSpaces(input);

        if (line.contains(":")) {
            definingNewVerb = true;
        } else if (line.toUpperCase().contains("DO")) {
            definingLoop = true;
        } else if (line.toUpperCase().contains("VARIABLE")) {
            definingVariable = true;
        } else if (line.toUpperCase().contains("CONSTANT")) {
            definingVariable = true;
        } else if (line.toUpperCase().contains(QUOTE)) {
            definingString = true;
        }

        if (definingVariable) {
            VariableAndConstantProcessor processor = new VariableAndConstantProcessor();
            String result = processor.process(line);
            definingVariable = processor.getDefinitionIsNotComplete();
            return result;
        } else if (definingNewVerb) {
            DefineVerbProcessor defineVerbProcessor = new DefineVerbProcessor();
            String result = defineVerbProcessor.process(line);
            definingNewVerb = defineVerbProcessor.getDefinitionIsNotComplete();
            return result;
        } else if (definingLoop) {
            AbstractLoopProcessor loopProcessor = new DoLoopProcessor();
            String result = loopProcessor.process(line);
            definingLoop = loopProcessor.getDefinitionIsNotComplete();
            return result;
        } else if (definingString) {
            line = storingString(line);
            AbstractProcessor lineProcessor = new LineProcessor();
            return lineProcessor.process(line);
        } else {
            AbstractProcessor lineProcessor = new LineProcessor();
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
