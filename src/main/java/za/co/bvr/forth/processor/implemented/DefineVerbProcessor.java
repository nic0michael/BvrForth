package za.co.bvr.forth.processor.implemented;

import lombok.extern.java.Log;
import za.co.bvr.forth.compiler.ForthCompiler;
import za.co.bvr.forth.dictionary.ForthDictionary;
import za.co.bvr.forth.dictionary.Verb;
import za.co.bvr.forth.processor.AbstractProcessor;

/**
 *
 * @author nickm 
 */
@Log
public class DefineVerbProcessor extends AbstractProcessor {

    ForthDictionary dictionary = ForthDictionary.INSTANCE;

    boolean definitionIsNotComplete = true;
    StringBuilder verbDefinition = new StringBuilder();
    String verbName;
    String compiledDefinition;
    int iterationCount = 0;


    @Override
    public String process(String line) throws Exception {
//        log.info("DefineVerbProcessor process: line: "+line);
        ForthCompiler compiler = new ForthCompiler();
        String returnString = "";
        String[] words = line.split(" ");
        Verb verb=null;

        for (String word : words) {
            if (word.equals(":")) {
                definitionIsNotComplete = true;
                returnString = "";
                verbDefinition = new StringBuilder();
                iterationCount = 0;
            } else if (word.equals(";")) {
                definitionIsNotComplete = false;
                returnString = "Ok";
                String definition = verbDefinition.toString();
                String compiledDefinition = compiler.compile(definition);
                verb = new Verb(verbName, verbDefinition.toString(), compiledDefinition);
                verb.setDescription("USER DEFINED VERB");
                dictionary.addVerbToDictionary(verb);
            } else if (iterationCount > 0) {
                if (iterationCount == 1) {
                    verbName = word;
                } else {
                    if (iterationCount > 2) {
                        verbDefinition.append(" ");
                    }
                    verbDefinition.append(word);
                }
            }
            iterationCount++;
        }
//        log.info("DefineVerbProcessor process: verb: "+verb);
        return returnString;

    }

    @Override
    public boolean getDefinitionIsNotComplete() throws Exception {
        return definitionIsNotComplete;
    }
    
    @Override
    public String preProcess(String line) throws Exception {throw new UnsupportedOperationException("Not supported yet."); }
    
    @Override
    public String postProcess(String line) throws Exception {throw new UnsupportedOperationException("Not supported yet.");}


}
