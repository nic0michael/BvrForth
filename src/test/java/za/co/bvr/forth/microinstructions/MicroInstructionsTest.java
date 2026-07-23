package za.co.bvr.forth.microinstructions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import za.co.bvr.forth.exceptions.StackIsEmptyException;
import za.co.bvr.forth.stack.ForthStack;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MicroInstructionsTest {

    private MicroInstructions microInstructions;
    private final ForthStack stack = ForthStack.INSTANCE;

    @BeforeEach
    void setUp() {
        microInstructions = new MicroInstructions();
        stack.clear();
    }

    @Test
    @DisplayName("DOT - Stack test")
    void testDot() {

        System.out.println("Running testDot...");
        stack.push("1");
        String result = microInstructions.execInstruction("DOT");

        assertEquals("1", result, "DOT should return the top of the stack");
        assertEquals(0, stack.size(), "Stack should be empty after DOT");
    }

    @Test
    @DisplayName("OVER - Stack test")
    void testOver() throws StackIsEmptyException {

        System.out.println("Running testOver...");

        stack.push("1");
        stack.push("2");

        String result = microInstructions.execInstruction("OVER");

        assertEquals("2", result, "OVER should return the duplicated top value");
        assertEquals(3, stack.size(), "Stack should contain three items after OVER");

        assertEquals("1", stack.pop(), "Top of stack after OVER");
        assertEquals("2", stack.pop(), "Second item after OVER");
        assertEquals("1", stack.pop(), "Bottom item after OVER");
    }

    @Test
    @DisplayName("DROP - Stack test")
    void testDrop() throws StackIsEmptyException {

        System.out.println("Running testDrop...");

        stack.push("1");
        stack.push("2");

        String result = microInstructions.execInstruction("DROP");

        assertEquals("", result, "DROP should return an empty string");
        assertEquals(1, stack.size(), "Stack should contain one item after DROP");

        assertEquals("1", stack.pop(), "Remaining item after DROP");
    }

    @Test
    @DisplayName("SWAP - Stack test")
    void testSwap() throws StackIsEmptyException {

        System.out.println("Running testSwap...");

        stack.push("1");
        stack.push("2");

        String result = microInstructions.execInstruction("SWAP");

        assertEquals("", result, "SWAP should return an empty string");

        assertEquals("2", stack.pop(), "Top of stack after SWAP");
        assertEquals("1", stack.pop(), "Second item after SWAP");
    }

    @Test
    @DisplayName("ROT - Stack test")
    void testRot() throws StackIsEmptyException {

        System.out.println("Running testRot...");

        stack.push("1");
        stack.push("2");
        stack.push("3");

        String result = microInstructions.execInstruction("ROT");

        assertEquals("", result, "ROT should return an empty string");

        assertEquals("1", stack.pop(), "Top of stack after ROT");
        assertEquals("3", stack.pop(), "Second item after ROT");
        assertEquals("2", stack.pop(), "Bottom item after ROT");
    }

}
