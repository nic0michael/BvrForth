package za.co.bvr.forth.processor.implemented;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import za.co.bvr.forth.dictionary.ForthDictionary;
import za.co.bvr.forth.dictionary.Verb;
import za.co.bvr.forth.exceptions.VerbNotInDictionaryException;

/**
 *
 * @author nickm
 */
public class DefineVerbProcessorTest {
    DefineVerbProcessor defineVerbProcessor=new DefineVerbProcessor();
    ForthDictionary dictionary = ForthDictionary.INSTANCE;
    
    
    @Test
    public void addAndGetVerbFromDictionaryTest() throws VerbNotInDictionaryException, Exception {
        String name = "addAndGetTest";
        int initialSize =  dictionary.size();
        String retrievedCompiledDefinition;
        String result;
        String expectedResult="Ok";
        String expectedCompiledDefinition = ". .";
        String verbName="..";
        String instruction = ": .. . . ;";
        result=defineVerbProcessor.process(instruction);
        retrievedCompiledDefinition=dictionary.getCompiledDefinition(verbName); 
        int size = dictionary.size();
        assertThat(retrievedCompiledDefinition , is(expectedCompiledDefinition));
        assertThat(result , is(expectedResult));
        
    }
}
