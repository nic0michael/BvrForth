package za.co.bvr.forth.utils;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import za.co.bvr.forth.exceptions.LineIsEmptyException;

/**
 *
 * @author nickm
 */
public class UtilitiesTest {
    
    
    @Test
    public void removeUnwantedSpacesTest() throws LineIsEmptyException{
        String lineWithSpaces=" The    Quick   Brown              Fox Jumps     ";
        String expected="The Quick Brown Fox Jumps";
        String trimmedLine=Utilities.removeUnwantedSpaces(lineWithSpaces);
        assertThat(expected, is(trimmedLine));
    }
    
    @Test
    public void removeUnwantedSpacesTest2() throws LineIsEmptyException{
        String lineWithSpaces=" The    Quick   Brown              Fox Jumps";
        String expected="The Quick Brown Fox Jumps";
        String trimmedLine=Utilities.removeUnwantedSpaces(lineWithSpaces);
        assertThat(expected, is(trimmedLine));
    }
    
    @Test
    public void removeUnwantedSpacesTest3() throws LineIsEmptyException{
        String lineWithSpaces=" The    Quick   Brown              Fox Jumps ";
        String expected="The Quick Brown Fox Jumps";
        String trimmedLine=Utilities.removeUnwantedSpaces(lineWithSpaces);
        assertThat(expected, is(trimmedLine));
    }
    
}
