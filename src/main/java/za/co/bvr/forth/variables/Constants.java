package za.co.bvr.forth.variables;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author nickm 
 */
public class Constants {
    
    Map<String,Integer>constantsStore=new HashMap<String,Integer>();
    
    public static final Constants INSTANCE = new Constants();

    private Constants() {}
    
    public void addConstant(String variableName,int value){
        constantsStore.put(variableName, value);
    }
    
    public int getValueOfConstant(String variableName){
        return constantsStore.get(variableName);
    }
    
    
    public boolean isConstant(String variableName){
        return constantsStore.get(variableName)!=null;
    }
}
