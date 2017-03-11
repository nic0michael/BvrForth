package za.co.bvr.forth.processor.implemented;

import za.co.bvr.forth.utils.Utilities;
import za.co.bvr.forth.exceptions.UnknownProcessorType;
import za.co.bvr.forth.processor.AbstractLoopProcessor;
import za.co.bvr.forth.processor.AbstractProcessor;

/**
 *
 * @author nickm 
 */
public class Inputprocessor extends AbstractProcessor {

    boolean definingNewVerb = false;
    boolean definingLoop = false;
    boolean definingVariable = false;

    @Override
    public String process(String input) throws UnknownProcessorType, Exception {
        String line = Utilities.removeUnwantedSpaces(input);
        if (line.contains(":")) {
            definingNewVerb = true;
        } else if (line.contains("DO")) {
            definingLoop = true;
        } else if (line.contains("VARIABLE")) {
            definingVariable = true;
        } else if (line.contains("CONSTANT")) {
            definingVariable = true;
        }

        if (definingVariable) {
            VariableAndConstantProcessor processor=new VariableAndConstantProcessor();
            String result = processor.process(line);
            definingVariable=processor.getDefinitionIsNotComplete();
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
        } else {
            AbstractProcessor lineProcessor = new LineProcessor();
            return lineProcessor.process(line);
        }
    }

    @Override
    public String preProcess(String line) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
