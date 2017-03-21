/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.co.bvr.forth.processor.implemented;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import za.co.bvr.forth.dictionary.ForthDictionary;
import za.co.bvr.forth.exceptions.VerbNotInDictionaryException;
import za.co.bvr.forth.stack.ForthStack;

/**
 *
 * @author nickm
 */
public class InputprocessorTest {
    final String QUOTE = "" + '"';
    Inputprocessor processor =new Inputprocessor();
    ForthDictionary dictionary = ForthDictionary.INSTANCE;
    ForthStack stack = ForthStack.INSTANCE;
    
    @Test
    public void lineTestQuote() throws Exception{
        String input="."+QUOTE+"Hello there"+QUOTE;
        String result1=processor.process(input);
        
        assertThat(result1, is("Hello there"));
    }
 
    @Test
    public void lineTestQuoteDot() throws Exception{
        String input=QUOTE+"Hello there"+QUOTE+".";
        String result1=processor.process(input);
        
        assertThat(result1, is("Hello there"));
    }
  
    @Test
    public void processTest() throws Exception {
        String result1=processor.process("1 2 + .");
        String result2=processor.process("5 1 - .");
        String result3=processor.process("4 2 * .");
        String result4=processor.process("6 2 / .");
        
        assertThat(result1, is("3"));
        assertThat(result2, is("4"));
        assertThat(result3, is("8"));
        assertThat(result4, is("3"));
    }  
  
    
    @Test
    public void addAndGetVerbFromDictionaryTest() throws VerbNotInDictionaryException, Exception {
        String name = "addAndGetTest";
        String retrievedCompiledDefinition;
        String result;
        String expectedResult="Ok";
        String expectedCompiledDefinition = ". .";
        String verbName="..";
        String instruction = ": .. . . ;";
        result=processor.process(instruction);
        retrievedCompiledDefinition=dictionary.getCompiledDefinition(verbName); 
        assertThat(retrievedCompiledDefinition , is(expectedCompiledDefinition));
        assertThat(result , is(expectedResult));
        
    }
    
    @Test
    public void processDoLoopTest2() throws Exception {
        String result1=processor.process("1 1 do 1 2 + . loop .");
        String result2=processor.process("1 2 do 1 2 + . loop .");
        
        assertThat(result1, is("31"));
        assertThat(result2, is("331"));
    }   

}
