package za.co.bvr.forth.processor.implemented;

import za.co.bvr.forth.dictionary.ForthDictionary;
import za.co.bvr.forth.processor.AbstractProcessor;
import za.co.bvr.forth.stack.ForthStack;
import za.co.bvr.forth.utils.Utilities;

/**
 *
 * @author nickm 
 */
class LineProcessor extends AbstractProcessor {

    ForthDictionary dictionary = ForthDictionary.INSTANCE;
    ForthStack stack =ForthStack.INSTANCE;


    @Override
    public String process(String line) throws Exception {
        if(Utilities.isEmptyString(line)){
            return "";
        }
        VerbProcessor verbProcessor = new VerbProcessor();
        StringBuilder result = new StringBuilder();
        String[] verbs = line.split(" ");
        for (String verb : verbs) {
            if (Utilities.isNumeric(verb)) {
                stack.push(verb);
            } else {
                String def = dictionary.getCompiledDefinition(verb);
                String[] definitions = def.split(" ");
                for (String definition : definitions) {
                    result.append(verbProcessor.process(definition));
                }
            }

        }

        return result.toString();
    }

    
    @Override
    public String preProcess(String line) throws Exception {throw new UnsupportedOperationException("Not supported yet.");}
    
    @Override
    public String postProcess(String line) throws Exception {throw new UnsupportedOperationException("Not supported yet."); }

    @Override
    public boolean getDefinitionIsNotComplete() throws Exception {throw new UnsupportedOperationException("Not supported yet.");}

}
