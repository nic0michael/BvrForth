package za.co.bvr.forth.variables;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author nickm 
 */
public class Variables {
    Map<String,Integer>variableStore=new HashMap<String,Integer>();
    
    public static final Variables INSTANCE = new Variables();

    private Variables() {}
    
    public void addVariable(String variableName,int value){
        variableStore.put(variableName, value);
    }
    
    public void updateVariable(String variableName,int value){
        variableStore.remove(variableName);
        variableStore.put(variableName, value);
    }
    
    public int getValueOfVariable(String variableName){
        return variableStore.get(variableName);
    }
    
    public boolean isVariable(String variableName){
        return variableStore.get(variableName)!=null;
    }
    
}
