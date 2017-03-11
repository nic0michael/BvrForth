package za.co.bvr.forth.stack;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import za.co.bvr.forth.exceptions.LineIsEmptyException;
import za.co.bvr.forth.exceptions.StackIsEmptyException;

/**
 *
 * @author nickm
 */
public class ForthStackTest {
    ForthStack stack =ForthStack.INSTANCE;

    @Test
    public void pushPopTest() throws LineIsEmptyException,StackIsEmptyException,NumberFormatException {
        int expected =10;
        int poppedValue;
        stack.push(expected);
        poppedValue=stack.popInt();        
        assertThat(expected, is(poppedValue));
    }
   
    @Test
    public void dropTest() throws LineIsEmptyException,StackIsEmptyException {
        int expected =10;
        int poppedValue;
        int expectedStackSize=stack.size();
        stack.push(expected);
        stack.drop();
        int stackSize=stack.size();
        assertThat(stackSize, is(expectedStackSize));
    }

     
    @Test
    public void dupTest() throws LineIsEmptyException,StackIsEmptyException,NumberFormatException {
        int expected =10;
        int poppedValue;
        int poppedValue2;
        stack.push(expected);
        stack.dup();
        poppedValue=stack.popInt();   
        poppedValue2=stack.popInt();        
        assertThat(expected, is(poppedValue));    
        assertThat(expected, is(poppedValue2));
    }
    
     
    @Test
    public void overTest() throws LineIsEmptyException,StackIsEmptyException,NumberFormatException {
        int expected =10;
        int expected2 =20;
        int poppedValue;
        int poppedValue2;
        stack.push(expected);
        stack.push(expected2);
        stack.over();
        poppedValue=stack.popInt();   
        poppedValue2=stack.popInt();        
        assertThat(expected, is(poppedValue));    
        assertThat(expected2, is(poppedValue2));
    }
   
    
    @Test
    public void rotTest() throws LineIsEmptyException,StackIsEmptyException,NumberFormatException {
        int expected =10;
        int expected2 =20;
        int expected3 =30;
        int poppedValue;
        int poppedValue2;
        int poppedValue3;
        stack.push(expected);
        stack.push(expected2);
        stack.push(expected3);
        stack.rot();
        poppedValue=stack.popInt();   
        poppedValue2=stack.popInt();    
        poppedValue3=stack.popInt();        
        assertThat(expected, is(poppedValue));    
        assertThat(expected3, is(poppedValue2));  
        assertThat(expected2, is(poppedValue3));
    }
 
    
    @Test
    public void swapTest() throws LineIsEmptyException,StackIsEmptyException,NumberFormatException {
        int expected =10;
        int expected2 =20;
        int poppedValue;
        int poppedValue2;
        stack.push(expected);
        stack.push(expected2);
        stack.swap();
        poppedValue=stack.popInt();   
        poppedValue2=stack.popInt();        
        assertThat(expected2, is(poppedValue));    
        assertThat(expected, is(poppedValue2));
    }

 
    @Test
    public void showTest() throws LineIsEmptyException,StackIsEmptyException,NumberFormatException {
        String expected="10 20 30";
        int value1 =10;
        int value2 =20;
        int value3 =30;
        stack.push(value1);
        stack.push(value2);
        stack.push(value3);
        String found=stack.show();    
        assertThat(found, is(expected));
    }
}