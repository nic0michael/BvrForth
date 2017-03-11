package za.co.bvr.forth.processor.implemented;

import za.co.bvr.forth.processor.AbstractLoopProcessor;
import za.co.bvr.forth.stack.ForthStack;

/**
 *
 * @author nickm
 */
public class DoLoopProcessor extends AbstractLoopProcessor {
    boolean definitionIsNotComplete = true;
    ForthStack stack = ForthStack.INSTANCE;

    StringBuilder lineOfTheLoop = new StringBuilder();
    StringBuilder preProcessLine = new StringBuilder();
    StringBuilder postProcessLine = new StringBuilder();

    @Override
    public String process(String line) throws Exception {
        StringBuilder result = new StringBuilder();
        
        setLineBeforeLoop(line);
        setLineAfterLoop(line);
        setLineOfTheLoop(line);
        
        String preResult=preProcess(preProcessLine.toString());
        String proResult=processLineOfTheLoop();
        String postResult=postProcess(postProcessLine.toString());
        
        result.append(preResult);
        result.append(proResult);
        result.append(postResult);

        return result.toString();
    }

    @Override
    public String preProcess(String line) throws Exception {
        LineProcessor processor = new LineProcessor();
        return processor.process(line);
    }

    @Override
    public String postProcess(String line) throws Exception {
        LineProcessor processor = new LineProcessor();
        return processor.process(line);
    }

    @Override
    public boolean getDefinitionIsNotComplete() throws Exception {
        return definitionIsNotComplete;
    }

    @Override
    public void setLineBeforeLoop(String line) {
        String[] lineItems = line.split(" ");
        
        boolean doFound = false;
        int count=0;        

        for (String lineItem : lineItems) {
            if (lineItem.equalsIgnoreCase("Do")) {
                doFound = true;
            }  else if (!doFound) {
                if (count > 0) {
                    preProcessLine.append(" ");
                }
                preProcessLine.append(lineItem);
                count++;
            }
        }
    }

    @Override
    public void setLineAfterLoop(String line) {
        String[] lineItems = line.split(" ");
        
        boolean doFound = false;
        boolean loopFound = false;
        int nrOfDoFound = 0;
        int nrOfLoopFound = 0;
        
        int count=0;

        for (String lineItem : lineItems) {
            if (lineItem.equalsIgnoreCase("Do")) {
                doFound = true;
                nrOfDoFound++;
            } else if (lineItem.equalsIgnoreCase("loop")) {
                nrOfLoopFound++;
                if (nrOfDoFound == nrOfLoopFound) {
                    loopFound = true;
                    definitionIsNotComplete = false;
                }
            } else if (loopFound) {
                if (count > 0) {
                    postProcessLine.append(" ");
                }
                count++;
                postProcessLine.append(lineItem);
            } 
        }
    }

    @Override
    public void setLineOfTheLoop(String line) {
        String[] lineItems = line.split(" ");
        
        boolean doFound = false;
        boolean loopFound = false;
        int nrOfDoFound = 0;
        int nrOfLoopFound = 0;
        
        int count=0;

        for (String lineItem : lineItems) {
            if (lineItem.equalsIgnoreCase("Do")) {
                doFound = true;
                nrOfDoFound++;
            } else if (lineItem.equalsIgnoreCase("loop")) {
                nrOfLoopFound++;
                if (nrOfDoFound == nrOfLoopFound) {
                    loopFound = true;
                    definitionIsNotComplete = false;
                }
            } else if (doFound && !loopFound) {
                if (count > 0) {
                    lineOfTheLoop.append(" ");
                }
                lineOfTheLoop.append(lineItem);
                count++;
            }
        }
    }


    @Override
    public String processLineOfTheLoop() throws Exception {
        LineProcessor processor = new LineProcessor();
         
        StringBuilder result = new StringBuilder();
        int nrOfLoopIterations=stack.popInt();
        
        for(int i=0;i<nrOfLoopIterations;i++){
            result.append(processor.process(lineOfTheLoop.toString()));
        }
        return result.toString();
    }


}
