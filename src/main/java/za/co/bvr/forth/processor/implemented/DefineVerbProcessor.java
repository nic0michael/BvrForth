package za.co.bvr.forth.processor.implemented;

import za.co.bvr.forth.compiler.ForthCompiler;
import za.co.bvr.forth.dictionary.ForthDictionary;
import za.co.bvr.forth.dictionary.Verb;
import za.co.bvr.forth.processor.AbstractProcessor;

/**
 *
 * @author nickm
 */
public class DefineVerbProcessor extends AbstractProcessor {

    ForthDictionary dictionary = ForthDictionary.INSTANCE;

    boolean definitionIsNotComplete = true;
    StringBuilder verbDefinition = new StringBuilder();
    String verbName;
    String compiledDefinition;
    int iterationCount = 0;

    @Override
    public String preProcess(String line) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String process(String line) throws Exception {
        ForthCompiler compiler = new ForthCompiler();
        String returnString = "";
        String[] words = line.split(" ");

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
                Verb verb = new Verb(verbName, verbDefinition.toString(), compiledDefinition);
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
        return returnString;

    }

    @Override
    public String postProcess(String line) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean getDefinitionIsNotComplete() throws Exception {
        return definitionIsNotComplete;
    }

}
