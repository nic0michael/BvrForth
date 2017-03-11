package za.co.bvr.forth.compiler;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import za.co.bvr.forth.exceptions.LineIsEmptyException;
import za.co.bvr.forth.exceptions.VerbNotInDictionaryException;

/**
 *
 * @author nickm
 */
public class ForthCompilerTest {
    ForthCompiler compiler=new ForthCompiler();
    
    @Test
    public void Test() throws VerbNotInDictionaryException, LineIsEmptyException{
        String verbDefToCompile=" . . push pop ";
        String expectedCompiledDefinition=". . PUSH POP";
        String compiledDefinition=compiler.compile(verbDefToCompile);
        
        assertThat(expectedCompiledDefinition, is(compiledDefinition));
    }
    
}
