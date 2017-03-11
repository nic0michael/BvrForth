package za.co.bvr.forth.processor.implemented;

import za.co.bvr.forth.processor.AbstractLoopProcessor;
import za.co.bvr.forth.processor.AbstractProcessor;

/**
 *
 * @author nickm
 */
public class DoLoopProcessor extends AbstractLoopProcessor {

    boolean definitionIsNotComplete = true;

    @Override
    public String process(String line) throws Exception {

        String lineOfTheLoop;

        StringBuilder result = new StringBuilder();

        result.append(preProcess(line));
//        result.append(processLineOfTheLoop());
        result.append(postProcess(line));

        return result.toString();
    }

    @Override
    public String preProcess(String line) throws Exception {
        String[] lines = line.split("DO");
        String lineBeforeLoop = lines[0];
        if (lineBeforeLoop != null && lineBeforeLoop.length() > 0) {
            AbstractProcessor lineProcessor = new LineProcessor();
            return lineProcessor.process(lineBeforeLoop);
        } else {
            return "";
        }
    }

    @Override
    public String postProcess(String line) throws Exception {
        String[] lines = line.split("LOOP");
        String lineAfterLoop = lines[lines.length - 1];

        if (lineAfterLoop != null && lineAfterLoop.length() > 0 && !lineAfterLoop.equals("LOOP")) {
            AbstractProcessor lineProcessor = new LineProcessor();
            return lineProcessor.process(lineAfterLoop);
        } else {
            return "";
        }
    }

    @Override
    public void setLineBeforeLoop(String line) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setLineAfterLoop(String line) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setLineOfTheLoop(String line) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String processLineBeforeLoop() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String processLineOfTheLoop() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String processLineAfterLoop() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean getDefinitionIsNotComplete() throws Exception {
        return definitionIsNotComplete;
    }

}
