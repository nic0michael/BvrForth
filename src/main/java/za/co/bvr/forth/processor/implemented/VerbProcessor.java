package za.co.bvr.forth.processor.implemented;

import za.co.bvr.forth.dictionary.ForthDictionary;
import za.co.bvr.forth.exceptions.StackIsEmptyException;
import za.co.bvr.forth.exceptions.VerbNotInDictionaryException;
import za.co.bvr.forth.processor.AbstractProcessor;
import za.co.bvr.forth.stack.ForthStack;
import za.co.bvr.forth.utils.Utilities;

/**
 *
 * @author nickm
 */
public class VerbProcessor extends AbstractProcessor {

    ForthStack stack = ForthStack.INSTANCE;
    ForthDictionary dictionary = ForthDictionary.INSTANCE;

    @Override
    public String preProcess(String line) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String process(String line) throws Exception {
        StringBuilder result = new StringBuilder();
        String[] lineItems = line.split(" ");
        for (String lineItem : lineItems) {
            if (Utilities.isNumeric(lineItem)) {
                stack.push(lineItem);
            } else {
                result.append(executeVerb(lineItem));
            }
        }
        return result.toString();
    }

    @Override
    public String postProcess(String line) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean getDefinitionIsNotComplete() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private Object executeVerb(String lineItem) throws  VerbNotInDictionaryException {
        StringBuilder result = new StringBuilder();
        try{
        switch (lineItem) {
            case ".":
                result.append(stack.pop());
                break;

            case "+":
                int second = stack.popInt();
                int first = stack.popInt();
                stack.push(first + second);
                break;

            case "-":
                second = stack.popInt();
                first = stack.popInt();
                stack.push(first - second);
                break;

            case "*":
                second = stack.popInt();
                first = stack.popInt();
                stack.push(first * second);
                break;

            case "/":
                second = stack.popInt();
                first = stack.popInt();
                stack.push(first / second);
                break;

            case "DROP":
                stack.drop();
                break;

            case "DUP":
                stack.dup();
                break;

            case "OVER":
                stack.over();
                break;

            case "ROT":
                stack.rot();
                break;

            case "SWAP":
                stack.swap();
                break;

            case ".STACK":
                result.append(stack.show());
                break;

            case ".HELP":
                result.append("The following verbs have been defined:\n");
                result.append(dictionary.showVerbs());
                break;

            default:
                throw new VerbNotInDictionaryException(lineItem);
        }
        } catch (StackIsEmptyException e){
            result.append("\nThe Stack is empty");
        }
        return result.toString();
    }

}
