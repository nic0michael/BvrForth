package za.co.bvr.forth.compiler;

import lombok.extern.java.Log;
import za.co.bvr.forth.dictionary.ForthDictionary;
import za.co.bvr.forth.exceptions.LineIsEmptyException;
import za.co.bvr.forth.exceptions.VerbNotInDictionaryException;
import za.co.bvr.forth.utils.Utilities;

/** 
 *
 * @author nickm
 */
@Log
public class ForthCompiler {
    ForthDictionary dictionary = ForthDictionary.INSTANCE;
    
    public String compile(String rawDefinition) throws VerbNotInDictionaryException, LineIsEmptyException{
//        log.info("ForthCompiler compile rawDefinition: "+rawDefinition);
        String rawDefinitionWithNoExtraSpaces=Utilities.removeUnwantedSpaces(rawDefinition);
        StringBuilder result=new StringBuilder();
        String[] verbs =rawDefinitionWithNoExtraSpaces.split(" ");
        int count=0;
        for (String verb : verbs) {
            String compiledDefinition =dictionary.getCompiledDefinition(verb);
            if(count>0){
                result.append(" ");
            }
            result.append(compiledDefinition);
            
            count++;
        }
        return result.toString();
    }
}
