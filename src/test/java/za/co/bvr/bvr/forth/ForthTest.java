package za.co.bvr.bvr.forth;

import org.junit.jupiter.api.Test;
import za.co.bvr.forth.Forth;


import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 *
 * @author nickm
 */
public class ForthTest {
    

    @Test
    public void processInput() throws Exception {
        String input = "1 2 + .";
        String expectedValue ="3";
        String resultValue = Forth.processInput(input);
        assertEquals(expectedValue, resultValue);
    }
    
}
