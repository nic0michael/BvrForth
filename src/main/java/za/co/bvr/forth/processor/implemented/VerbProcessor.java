package za.co.bvr.forth.processor.implemented;

import java.net.UnknownHostException;
import lombok.extern.java.Log;
import za.co.bvr.forth.dictionary.ForthDictionary;
import za.co.bvr.forth.exceptions.StackIsEmptyException;
import za.co.bvr.forth.exceptions.VerbNotInDictionaryException;
import za.co.bvr.forth.processor.AbstractProcessor;
import za.co.bvr.forth.stack.ForthStack;
import za.co.bvr.forth.utils.Utilities;
import za.co.bvr.forth.utils.DateUtilities;
import za.co.bvr.forth.variables.VariablesStore;

/**
 *
 * @author nicm
 */
@Log
public class VerbProcessor extends AbstractProcessor {
    
    
    public static final VerbProcessor INSTANCE = new VerbProcessor();

    private VerbProcessor() { }

    ForthStack stack = ForthStack.INSTANCE;
    ForthDictionary dictionary = ForthDictionary.INSTANCE;
    VariablesStore variables = VariablesStore.INSTANCE;


    public String executeVerb(String lineItem) throws VerbNotInDictionaryException, StackIsEmptyException, UnknownHostException {
        StringBuilder result = new StringBuilder();
        lineItem=lineItem.toUpperCase();
//        log.info("VerbProcessor executeVerb lineItem : "+lineItem);
        if (variables.isVariable(lineItem)) {
            variables.setCurrentvariableName(lineItem);

        } else {

            try {
                switch (lineItem) {
                    case ".":
                        result.append(stack.pop());
                        break;

                    case "FORGET":
                        result.append(dictionary.forget(stack.pop()));
                        
                    case "SPACE":
                        result.append(" ");
                        break;

                    case "SPACES":
                        int topValue = stack.popInt();
                        for (int i = 0; i < topValue; i++) {
                            result.append(" ");
                        }
                        break;
                        
                    case "LFS":
                        topValue = stack.popInt();
                        for (int i = 0; i < topValue; i++) {
                            result.append("\n");
                        }
                        break;

                    case "EMIT":
                        stack.intToChar();
                        result.append(stack.pop());
                        break;

                    case "=":
                        stack.equals();
                        break;

                    case "0=":
                        stack.equalsZero();
                        break;

                    case "0<":
                        stack.equalsZero();
                        break;

                    case ">":
                        stack.greaterThan();
                        break;

                    case "<":
                        stack.smallerThan();
                        break;
                        
                    case "=>":
                        stack.equalsOrGreaterThan();
                        break;
                    case "<=":
                        stack.smallerThanOrEquals();
                        break;
                    case "NOT":
                        stack.not();
                        break;
                    case "+":
                        stack.add();
                        break;

                    case "-":
                        stack.subtract();
                        break;

                    case "*":
                        stack.multiply();
                        break;

                    case "/":
                        stack.divide();
                        break;

                    case "MOD":
                        stack.modulus();
                        break;

                    case "D+":
                        stack.addDoubles();
                        break;

                    case "D-":
                        stack.subtractDoubles();
                        break;

                    case "D*":
                        stack.multiplyDoubles();
                        break;

                    case "D/":
                        stack.divideDoubles();
                        break;

                    case "D>":
                        stack.greaterThanDoubles();
                        break;

                    case "D<":
                        stack.smallerThanDoubles();
                        break;

                    case "D=":
                        stack.equalsDoubles();
                        break;

                    case "D0=":
                        stack.equalsZeroDoubles();
                        break;

                    case "D0<":
                        stack.equalsZeroDoubles();
                        break;

                    case "DMOD":
                        stack.modulusDoubles();
                        break;

                    case "DSQR":
                        stack.squareDoubles();
                        break;

                    case "SQARE":
                        stack.square();
                        break;                        

                    case "SQR":
                        stack.sqrt();
                        break;

                    case "PWR":
                        stack.power();
                        break;

                    case "AND":
                        stack.and();
                        break;

                    case "OR":
                        stack.or();
                        break;

                    case "XOR":
                        stack.exor();
                        break;

                    case "RANDOM":
                        stack.random();
                        break;

                    case "RND":
                        stack.random();
                        break;

                    case "MAX":
                        stack.max();
                        break;
                    case "MIN":
                        stack.min();
                        break;
                    case "ROUND":
                        stack.round();
                        break;
                    case "FLOOR":
                        stack.floor();
                        break;
                    case "CEIL":
                        stack.ceil();
                        break;
                    case "RADTODEG":
                        stack.radiansToDegrees();
                        break;
                    case "DEGTORAD":
                        stack.degreesToRadians();
                        break;
                    case "SIN":
                        stack.degreesToRadians();
                        stack.sin();
                        stack.push(0.00000000000000006);
                        stack.addDoubles();
                        break;
                    case "COS":
                        stack.degreesToRadians();
                        stack.cos();
                        stack.push(0.0000000000000001);
                        stack.subtractDoubles();
                        break;
                    case "TAN":
                        stack.degreesToRadians();
                        stack.tan();
                        stack.push(0.0000000000000001);
                        stack.addDoubles();
                        break;
                    case "LOG":
                        stack.log();
                        break;
                    case "LOGBASE10":
                        stack.logBase10();
                        break;

                    case "DEC":
                        stack.setModeToDecimal();
                        break;

                    case "HEX":
                        stack.setModeToHex();
                        break;

                    case "BIN":
                        stack.setModeToBinary();
                        break;

                    case "OCT":
                        stack.setModeToOctal();
                        break;

                    case "BASE64ENCODE":
                        stack.base64Encode();
                        break;

                    case "BASE64DECODE":
                        stack.base64Decode();
                        break;

                    case "DECTOBINARY":
                        stack.convertToBinary();
                        break;

                    case "DECTOHEX":
                        stack.convertToHex();
                        break;

                    case "DECTOOCTAL":
                        stack.convertToOctal();
                        break;

                    case "HEXTODEC":
                        stack.convertHexToDecimal();
                        break;

                    case "OCTALTODEC":
                        stack.convertOctalToDecimal();
                        break;

                    case "BINARYTODEC":
                        stack.convertBinaryToDecimal();
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

                    case "TIME@":
                        stack.push(DateUtilities.dateY2k());
                        break;

                    case "TIMESTAMP@":
                        stack.push(DateUtilities.timeStamp());
                        break;

                    case "?TIME":
                        result.append(DateUtilities.dateY2k());
                        break;

                    case "?TIMESTAMP":
                        result.append(DateUtilities.timeStamp());
                        break;

                    case ".STACK":
                        result.append(stack.show());
                        break;

                    case "CR":
                        result.append("\r\n");
                        break;

                    case ".DAY":
                        result.append(DateUtilities.day());
                        break;

                    case ".MONTH":
                        result.append(DateUtilities.month());
                        break;

                    case ".MTH":
                        result.append(DateUtilities.month());
                        break;

                    case ".YEAR":
                        result.append(DateUtilities.year());
                        break;

                    case ".YR":
                        result.append(DateUtilities.year());
                        break;

                    case ".DATE":
                        result.append(DateUtilities.dateY2k());
                        break;

                    case ".DATESF":
                        result.append(DateUtilities.dateY2k());
                        break;

                    case ".DATESIMPLEFORMAT":
                        result.append(DateUtilities.dateY2k());
                        break;

                    case ".DATETIME":
                        result.append(DateUtilities.now());
                        break;

                    case ".DATEBRITISH":
                        result.append(DateUtilities.dateBritish());
                        break;
                    case ".DATEUSA":
                        result.append(DateUtilities.dateUSA());
                        break;

                    case ".TIME":
                        result.append(DateUtilities.time());
                        break;
                    case ".TIMESTAMP":
                        result.append(DateUtilities.timeStamp());
                        break;

                    case ".D":
                        result.append(dictionary.showVerbs());
                        break;

                    case ".DICT":
                        result.append(dictionary.showVerbs());
                        break;

                    case ".DICTDEF":
                        result.append(dictionary.showVerbDetails());
                        break;

                    case ".MODE":
                        result.append(stack.getCurrentMode());
                        break;

                    case ".COMPUTERNAME":
                        result.append(Utilities.getComputerName());
                        break;
                    case ".CNAME":
                        result.append(Utilities.getComputerName());
                        break;
                    case ".COMPUTERIP":
                        result.append(Utilities.getComputerIpAddress());
                        break;
                    case ".CIP":
                        result.append(Utilities.getComputerIpAddress());
                        break;

                    case ".SERVERIP":
                        String serverUlr = stack.pop();
                        result.append(Utilities.getIpAddressOfHosst(serverUlr));
                        break;

                    case ".SIP":
                        String serverUlr2 = stack.pop();
                        result.append(Utilities.getIpAddressOfHosst(serverUlr2));
                        break;

                    case "!":
                        String value = stack.pop();
                        variables.setCurrentvariableValue(value);
                        break;

                    case "XML!":
                        break;

                    case "XML@":
                        break;

                    case "JSON!":
                        break;

                    case "JSON@":
                        break;
                        
                    case "@":
                        String currentvariableValue = variables.getCurrentvariableValue();
                        stack.push(currentvariableValue);
                        break;

                    case ".VARIABLES":
                        result.append(variables.showVariables());
                        break;

                    case ".V":
                        result.append(variables.showVariables());
                        break;

                    case ".HELP":
                        result.append("The following verbs have been defined:\n");
                        result.append(dictionary.showVerbs());
                        break;

                    case ".H":
                        result.append("The following verbs have been defined:\n");
                        result.append(dictionary.showVerbs());
                        break;

                    default:
                        throw new VerbNotInDictionaryException(lineItem);
                }
            } catch (StackIsEmptyException e) {
                result.append("\nThe Stack is empty");
            }
        }
//        log.info("VerbProcessor result : "+result);
        return result.toString();
    }
    
    @Override
    public String preProcess(String line) throws Exception {
        return null;
    }

    @Override
    public String process(String line) throws Exception {
        return null;
    }

    @Override
    public String postProcess(String line) throws Exception {
        return null;
    }

    @Override
    public boolean getDefinitionIsNotComplete() throws Exception {
        return false;
    }
}
