package za.co.bvr.forth.stack;

import java.util.Stack;
import za.co.bvr.forth.exceptions.StackIsEmptyException;
import za.co.bvr.forth.utils.Utilities;

/**
 *
 * @author nickm 
 */
public class ForthStack {

    Stack<String> stackStore = new Stack<String>();
    int topOfStack = -1;

    public static final ForthStack INSTANCE = new ForthStack();

    private ForthStack() {
    }

    public int size() {
        return stackStore.size();
    }

    public void push(String value) {
        stackStore.add(value);
        topOfStack++;
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
        return retString;
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
    
    public void add() throws StackIsEmptyException{
        int second=popInt();
        int first=popInt();
        push(first+second);
    }
    
    public void subtract() throws StackIsEmptyException{
        int second=popInt();
        int first=popInt();
        push(first-second);
    }

    
    public void multiply() throws StackIsEmptyException{
        int second=popInt();
        int first=popInt();
        push(first*second);
    }
    
    
    
    public void divide() throws StackIsEmptyException{
        int second=popInt();
        int first=popInt();
        push(first/second);
    }
    
    public String get() {
        if (stackStore.size() > 0) {
            return (String) stackStore.get(topOfStack);
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

}
