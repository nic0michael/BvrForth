package za.co.bvr.forth.processor.implemented;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import za.co.bvr.forth.variables.Constants;
import za.co.bvr.forth.variables.Variables;

/**
 *
 * @author nickm
 */
public class VariableAndConstantProcessorTest {
    VariableAndConstantProcessor processor=new VariableAndConstantProcessor();
    Constants constants =Constants.INSTANCE;
    Variables variables=Variables.INSTANCE;
    
    @Test
    public void processTest() throws Exception {
        processor.process("1 variable one !");
        processor.process("2 constant two !");
        processor.process("variable three");
        variables.updateVariable("three",3);
        int result1=variables.getValueOfVariable("one");
        int result2=constants.getValueOfConstant("two");
        int result3=variables.getValueOfVariable("three");
        
        assertThat(result1, is(1));
        assertThat(result2, is(2));
        assertThat(result3, is(3));
    }   
    
}
