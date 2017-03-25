package za.co.bvr.forth.processor.implemented;

import org.junit.Test;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import za.co.bvr.forth.stack.ForthStack;
/**
 *
 * @author nickm
 */
public class IfStatementProcessorTest {
    IfStatementProcessor ifStatementProcessor=new IfStatementProcessor();
    ForthStack stack =ForthStack.INSTANCE;
    
    @Test
    public void Test() throws Exception{
        String line ="2 1 if 2 3 + . then .";
        String expected="52";
        String result=ifStatementProcessor.process(line);
        assertThat(result, is(expected));
    }
}
