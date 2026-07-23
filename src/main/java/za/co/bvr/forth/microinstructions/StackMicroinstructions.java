package za.co.bvr.forth.microinstructions;

import za.co.bvr.forth.exceptions.StackIsEmptyException;
import za.co.bvr.forth.stack.ForthStack;

public class StackMicroinstructions {

    private final ForthStack stack = ForthStack.INSTANCE;

    public String dot() {
        try {
            return stack.pop();
        } catch (StackIsEmptyException e) {
            return "\n\nStackIsEmpty";
        }
    }

    public String over() {
        try {
            return stack.over();
        } catch (NumberFormatException e) {
            return "\n\nNumberFormatException";
        } catch (StackIsEmptyException e) {
            return "\n\nStackIsEmpty";
        }
    }

    public String drop() {
        try {
            stack.drop();
            return "";
        } catch (StackIsEmptyException e) {
            return "\n\nStackIsEmpty";
        }
    }

    public String swap() {
        try {
            stack.swap();
            return "";
        } catch (StackIsEmptyException e) {
            return "\n\nStackIsEmpty";
        }
    }

    public String rot() {
        try {
            stack.rot();
            return "";
        } catch (StackIsEmptyException e) {
            return "\n\nStackIsEmpty";
        }
    }

    public String dup() {
        try {
            stack.dup();
            return "";
        } catch (StackIsEmptyException e) {
            return "\n\nStackIsEmpty";
        }
    }

    public String qdup() {
        try {
            stack.qdup();
            return "";
        } catch (NumberFormatException e) {
            return "\n\nNumberFormatException";
        }
    }

    public String add() {
        try {
            stack.add();
            return "";
        } catch (StackIsEmptyException e) {
            return "\n\nStackIsEmpty";
        }
    }

    public String subtract() {
        try {
            stack.subtract();
            return "";
        } catch (StackIsEmptyException e) {
            return "\n\nStackIsEmpty";
        }
    }

    public String multiply() {
        try {
            stack.multiply();
            return "";
        } catch (StackIsEmptyException e) {
            return "\n\nStackIsEmpty";
        }
    }

    public String divide() {
        try {
            stack.divide();
            return "";
        } catch (StackIsEmptyException e) {
            return "\n\nStackIsEmpty";
        } catch (ArithmeticException e) {
            return "\n\nArithmeticException";
        }
    }

    public String modulus() {
        try {
            stack.modulus();
            return "";
        } catch (StackIsEmptyException e) {
            return "\n\nStackIsEmpty";
        } catch (ArithmeticException e) {
            return "\n\nArithmeticException";
        }
    }
}