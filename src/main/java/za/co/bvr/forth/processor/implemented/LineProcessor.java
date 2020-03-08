package za.co.bvr.forth.processor.implemented;

import lombok.extern.java.Log;
import za.co.bvr.forth.dictionary.ForthDictionary;
import za.co.bvr.forth.processor.AbstractProcessor;
import za.co.bvr.forth.stack.ForthStack;
import za.co.bvr.forth.utils.Utilities;
import za.co.bvr.forth.variables.VariablesStore;

/**
 *
 * @author nickm 
 */
@Log
class LineProcessor extends AbstractProcessor {

    ForthDictionary dictionary = ForthDictionary.INSTANCE;
    ForthStack stack =ForthStack.INSTANCE;
    VariablesStore variables = VariablesStore.INSTANCE;


    @Override
    public String process(String line) throws Exception {
        
//        log.info("LineProcessor processing line : "+line+"\n\n");
        if(Utilities.isEmpty(line)){
            return "";
        }
        VerbPreProcessor verbProcessor = new VerbPreProcessor();
        StringBuilder result = new StringBuilder();
        String[] verbs = line.split(" ");
        for (String verb : verbs) {
            if (Utilities.isNumeric(verb)) {
                stack.push(verb);
            }else if (variables.isVariable(verb.toUpperCase())) {
                result.append(verbProcessor.process(verb));
            }else{
//                log.info("LineProcessor verb : "+verb);
                String def = dictionary.getCompiledDefinition(verb);
                String[] definitions = def.split(" ");
                for (String definition : definitions) {
//                    log.info("LineProcessor definition : "+definition+"\n\n");
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
