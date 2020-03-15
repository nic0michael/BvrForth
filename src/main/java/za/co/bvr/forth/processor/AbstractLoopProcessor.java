package za.co.bvr.forth.processor;

/**
 *
 * @author nickm 
 */
public abstract class AbstractLoopProcessor extends AbstractProcessor{
    String lineBeforeLoop;
    String lineOfTheLoop;
    String lineAfterLoop;
    
//    LineProcessor lineProcessor=new LineProcessor();
    


    public abstract void setLineBeforeLoop(String line);
    public abstract void setLineAfterLoop(String line);
    public abstract void setLineOfTheLoop(String line) throws Exception;
    
    public abstract String processLineOfTheLoop() throws Exception;

    public boolean getDefinitionIsNotComplete() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
