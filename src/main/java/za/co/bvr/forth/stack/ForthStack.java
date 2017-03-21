package za.co.bvr.forth.stack;

import java.util.Stack;
import za.co.bvr.forth.exceptions.StackIsEmptyException;
import za.co.bvr.forth.utils.Utilities;
import org.apache.commons.codec.binary.Base64;

/**
 *
 * @author nickm
 */
public class ForthStack {

    Stack<String> stackStore = new Stack<String>();
    int topOfStack = -1;
    mode currentMode = mode.DECIMAL;

    public static final ForthStack INSTANCE = new ForthStack();

    private ForthStack() {
    }

    public int size() {
        return stackStore.size();
    }

    public void push(String value) {
        if (!Utilities.stringIsEmpty(value)) {
            stackStore.add(value);
            topOfStack++;
        }
    }

    public void push(byte[] value) {
        push(new String(value));
    }

    public void push(int value) {
        stackStore.push(Integer.toString(value));
        topOfStack++;
    }

    public String pop() throws StackIsEmptyException {
        String retString = "";
        if (topOfStack < 0) {
            clear();
            throw new StackIsEmptyException();
        } else if (stackStore.size() < 1) {
            clear();
            throw new StackIsEmptyException();
        } else {
            retString = stackStore.pop();
            topOfStack--;
        }

        return convertToCurrentMode(retString);
    }

    public int popInt() throws StackIsEmptyException, NumberFormatException {
        String retString = "";
        int retValue;
        retString = pop();
        retValue = Integer.parseInt(retString);
        return retValue;
    }

    public void clear() {
        stackStore = new Stack<String>();
        topOfStack = -1;
    }

    public void drop() throws StackIsEmptyException {
        pop();
    }

    public void swap() throws StackIsEmptyException { // 1 2 -> 2 1
        String top = pop();
        String next = pop();
        push(next);
        push(top);
    }

    public void rot() throws StackIsEmptyException { // 1 2 3    ->  2 3 1
        String top = pop();
        String next = pop();
        String bottom = pop();
        push(next);
        push(top);
        push(bottom);
    }

    public void over() throws StackIsEmptyException, NumberFormatException { // 1 2     ->  1 2 1 
        String top = pop();
        String first = pop();
        push(first);
        push(top);
        push(first);
    }

    public void dup() throws StackIsEmptyException { // 1 2     ->  1 2 2 
        String top = pop();
        push(top);
        push(top);
    }

    public void qdup() { // ?dup
        int val = getInt();
        if (val != 0) {
            push(get());
        }
    }

    public void add() throws StackIsEmptyException {
        int second = popInt();
        int first = popInt();
        push(first + second);
    }

    public void subtract() throws StackIsEmptyException {
        int second = popInt();
        int first = popInt();
        push(first - second);
    }

    public void multiply() throws StackIsEmptyException {
        int second = popInt();
        int first = popInt();
        push(first * second);
    }

    public void divide() throws StackIsEmptyException {
        int second = popInt();
        int first = popInt();
        push(first / second);
    }

    public void base64Encode() throws StackIsEmptyException {
        String StringOnStack = pop();
        byte[] encodedstring = Base64.encodeBase64(StringOnStack.getBytes());
        push(new String(encodedstring));
    }

    public void base64Decode() throws StackIsEmptyException {
        String StringOnStack = pop();
        byte[] decodedstring = Base64.decodeBase64(StringOnStack.getBytes());
        push(new String(decodedstring));
    }

    public void convertToBinary() throws StackIsEmptyException {
        int topValue = popInt();
        String strBinaryNumber = Integer.toBinaryString(topValue);
        push(strBinaryNumber);
    }

    public void convertToHex() throws StackIsEmptyException {
        int topValue = popInt();
        String strBinaryNumber = Integer.toHexString(topValue);
        push(strBinaryNumber);
    }

    public void convertToOctal() throws StackIsEmptyException {
        int topValue = popInt();
        String strBinaryNumber = Integer.toOctalString(topValue);
        push(strBinaryNumber);
    }

    public void convertHexToDecimal() throws StackIsEmptyException {
        String StringOnStack = pop();
        int value = Integer.parseInt(StringOnStack, 16);
        push(value);
    }

    public void convertOctalToDecimal() throws StackIsEmptyException {
        String StringOnStack = pop();
        int value = Integer.parseInt(StringOnStack, 8);
        push(value);
    }

    public void convertBinaryToDecimal() throws StackIsEmptyException {
        String StringOnStack = pop();
        int value = Integer.parseInt(StringOnStack, 2);
        push(value);
    }

    public void count() {

    }

    public String get() {
        if (stackStore.size() > 0) {
            String str = (String) stackStore.get(topOfStack);
            return convertToCurrentMode(str);
        } else {
            return "End of stack reached";
        }
    }

    public int getInt() {
        int retValue;
        retValue = Integer.parseInt(get());
        return retValue;
    }

    public String show() {
        StringBuilder stackString = new StringBuilder();
        int count = 0;
        for (String item : stackStore) {
            if (!Utilities.stringIsEmpty(item)) {
                if (count > 0) {
                    stackString.append(" ");
                }
                stackString.append(item);
                count++;
            }
        }

        return stackString.toString();
    }

    public void setModeToDecimal() {
        currentMode = mode.DECIMAL;
    }

    public void setModeToHex() {
        currentMode = mode.HEX;
    }

    public void setModeToOctal() {
        currentMode = mode.OCTAL;
    }

    public void setModeToBinary() {
        currentMode = mode.BINARY;
    }

    String convertToCurrentMode(String value) {
        System.out.println(">" + value + "<");
        String retValue = "";
        String displayMode = currentMode.getValue();

        if (Utilities.stringIsEmpty(value) || !Utilities.isNumeric(value)) {
            return value;
        }
        System.out.println(">IS NUMERIC<");
        int numericValue = Integer.parseInt(value);

        switch (displayMode) {
            case "DECIMAL":
                retValue = value;
                break;

            case "HEX":
                retValue = Integer.toHexString(numericValue).toUpperCase();
                break;

            case "OCTAL":
                retValue = Integer.toOctalString(numericValue);
                break;

            case "BINARY":
                retValue = Integer.toBinaryString(numericValue);
                break;
        }

        return retValue;
    }

    public enum mode {
        DECIMAL("DECIMAL"),
        HEX("HEX"),
        OCTAL("OCTAL"),
        BINARY("BINARY");

        private String value;

        private mode(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

}
