package za.co.bvr.forth.processor.implemented;


import java.net.UnknownHostException;
import lombok.extern.java.Log;
import za.co.bvr.forth.dictionary.ForthDictionary;
import za.co.bvr.forth.exceptions.StackIsEmptyException;
import za.co.bvr.forth.exceptions.VerbNotInDictionaryException;
import za.co.bvr.forth.processor.AbstractProcessor;
import za.co.bvr.forth.utils.Utilities;
import za.co.bvr.forth.variables.VariablesStore;
import za.co.bvr.forth.microinstructions.DateMicroinstructions;
import za.co.bvr.forth.microinstructions.StackMicroinstructions;

/**
 * WE WILL USE MICROINSTRUCTIONS HERE
 * @author nicm
 */
@Log
public class VerbProcessor extends AbstractProcessor {
    
    public StackMicroinstructions stackMicroinstructions =new StackMicroinstructions();
    public static final VerbProcessor INSTANCE = new VerbProcessor();
    ForthDictionary dictionary = ForthDictionary.INSTANCE;
    VariablesStore variables = VariablesStore.INSTANCE;

    private VerbProcessor() { }

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
                        result .append(stackMicroinstructions.pop());
                        break;

                    case "1+": stackMicroinstructions.push(stackMicroinstructions.onePlus());
                        break;
                    case"1-":  stackMicroinstructions.push(stackMicroinstructions.oneMinis());
                        break;
                    case "2+":  stackMicroinstructions.push(stackMicroinstructions.twoPlus());
                        break;
                    case "2-":  stackMicroinstructions.push(stackMicroinstructions.twoMinus());
                        break;
                    case "2/":  stackMicroinstructions.push(stackMicroinstructions.twoDivide());
                        break;
                    case "2*":  stackMicroinstructions.push(stackMicroinstructions.twoTimes());
                        break;

                    case "FORGET":
                        result.append(dictionary.forget(stackMicroinstructions.pop()));
                        break;
                        
                    case "SPACE":
                        result.append(" ");
                        break;

                    case "SPACES":
                        int topValue = stackMicroinstructions.popInt();
                        for (int i = 0; i < topValue; i++) {
                            result.append(" ");
                        }
                        break;
                        
                    case "LFS":
                        topValue = stackMicroinstructions.popInt();
                        for (int i = 0; i < topValue; i++) {
                            result.append("\n");
                        }
                        break;

                    case "EMIT":
                        stackMicroinstructions.intToChar();
                        result.append(stackMicroinstructions.pop());
                        break;

                    case "=":
                        stackMicroinstructions.equals();
                        break;

                    case "0=":
                        stackMicroinstructions.equalsZero();
                        break;

                    case "0<":
                        stackMicroinstructions.greaterThanZero();
                        break;

                    case "0>":
                        stackMicroinstructions.smallerThanZero();
                        break;

                    case ">":
                        stackMicroinstructions.greaterThan();
                        break;

                    case "<":
                        stackMicroinstructions.smallerThan();
                        break;
                        
                    case "=>":
                        stackMicroinstructions.equalsOrGreaterThan();
                        break;
                    case "<=":
                        stackMicroinstructions.smallerThanOrEquals();
                        break;
                    case "NOT":
                        stackMicroinstructions.not();
                        break;
                    case "+":
                        stackMicroinstructions.add();
                        break;

                    case "-":
                        stackMicroinstructions.subtract();
                        break;

                    case "*":
                        stackMicroinstructions.multiply();
                        break;

                    case "/":
                        stackMicroinstructions.divide();
                        break;

                    case "MOD":
                        stackMicroinstructions.modulus();
                        break;

                    case "D+":
                        stackMicroinstructions.addDoubles();
                        break;

                    case "D-":
                        stackMicroinstructions.subtractDoubles();
                        break;

                    case "D*":
                        stackMicroinstructions.multiplyDoubles();
                        break;

                    case "D/":
                        stackMicroinstructions.divideDoubles();
                        break;

                    case "D>":
                        stackMicroinstructions.greaterThanDoubles();
                        break;

                    case "D<":
                        stackMicroinstructions.smallerThanDoubles();
                        break;

                    case "D=":
                        stackMicroinstructions.equalsDoubles();
                        break;

                    case "D0=":
                        stackMicroinstructions.equalsZeroDoubles();
                        break;

                    case "D0<":
                        stackMicroinstructions.greaterThanZeroDoubles();
                        break;

                    case "D0>":
                        stackMicroinstructions.smallerThanZeroDoubles();
                        break;

                    case "DMOD":
                        stackMicroinstructions.modulusDoubles();
                        break;

                    case "DSQR":
                        stackMicroinstructions.squareDoubles();
                        break;

                    case "SQARE":
                        stackMicroinstructions.square();
                        break;                        

                    case "SQR":
                        stackMicroinstructions.sqrt();
                        break;

                    case "PWR":
                        stackMicroinstructions.power();
                        break;

                    case "AND":
                        stackMicroinstructions.and();
                        break;

                    case "OR":
                        stackMicroinstructions.or();
                        break;

                    case "XOR":
                        stackMicroinstructions.exor();
                        break;

                    case "RANDOM":
                        stackMicroinstructions.random();
                        break;

                    case "RND":
                        stackMicroinstructions.random();
                        break;

                    case "MAX":
                        stackMicroinstructions.max();
                        break;
                    case "MIN":
                        stackMicroinstructions.min();
                        break;
                    case "ROUND":
                        stackMicroinstructions.round();
                        break;
                    case "FLOOR":
                        stackMicroinstructions.floor();
                        break;
                    case "CEIL":
                        stackMicroinstructions.ceil();
                        break;
                    case "RADTODEG":
                        stackMicroinstructions.radiansToDegrees();
                        break;
                    case "DEGTORAD":
                        stackMicroinstructions.degreesToRadians();
                        break;
                    case "SIN":
                        stackMicroinstructions.degreesToRadians();
                        stackMicroinstructions.sin();
                        stackMicroinstructions.push(0.00000000000000006);
                        stackMicroinstructions.addDoubles();
                        break;
                    case "COS":
                        stackMicroinstructions.degreesToRadians();
                        stackMicroinstructions.cos();
                        stackMicroinstructions.push(0.0000000000000001);
                        stackMicroinstructions.subtractDoubles();
                        break;
                    case "TAN":
                        stackMicroinstructions.degreesToRadians();
                        stackMicroinstructions.tan();
                        stackMicroinstructions.push(0.0000000000000001);
                        stackMicroinstructions.addDoubles();
                        break;
                    case "LOG":
                        stackMicroinstructions.log();
                        break;
                    case "LOGBASE10":
                        stackMicroinstructions.logBase10();
                        break;
                    case "LOG10":
                        stackMicroinstructions.logBase10();
                        break;

                    case "DEC":
                        stackMicroinstructions.setModeToDecimal();
                        break;

                    case "HEX":
                        stackMicroinstructions.setModeToHex();
                        break;

                    case "BIN":
                        stackMicroinstructions.setModeToBinary();
                        break;

                    case "OCT":
                        stackMicroinstructions.setModeToOctal();
                        break;

                    case "BASE64ENCODE":
                        stackMicroinstructions.base64Encode();
                        break;

                    case "BASE64DECODE":
                        stackMicroinstructions.base64Decode();
                        break;

                    case "DECTOBINARY":
                        stackMicroinstructions.convertToBinary();
                        break;

                    case "DECTOHEX":
                        stackMicroinstructions.convertToHex();
                        break;

                    case "DECTOOCTAL":
                        stackMicroinstructions.convertToOctal();
                        break;

                    case "HEXTODEC":
                        stackMicroinstructions.convertHexToDecimal();
                        break;

                    case "OCTALTODEC":
                        stackMicroinstructions.convertOctalToDecimal();
                        break;

                    case "BINARYTODEC":
                        stackMicroinstructions.convertBinaryToDecimal();
                        break;

                    case "DROP":
                        stackMicroinstructions.drop();
                        break;

                    case "DUP":
                        stackMicroinstructions.dup();
                        break;

                    case "OVER":
                        stackMicroinstructions.over();
                        break;

                    case "ROT":
                        stackMicroinstructions.rot();
                        break;

                    case "SWAP":
                        stackMicroinstructions.swap();
                        break;

                    case "MILLISECONDS@":
                        stackMicroinstructions.push(DateMicroinstructions.milliseconds());
                        break;

                    case "DATE@":
                        stackMicroinstructions.push(DateMicroinstructions.date());
                        break;

                    case "TIME@":
                        stackMicroinstructions.push(DateMicroinstructions.dateY2k());
                        break;

                    case "TIMESTAMP@":
                        stackMicroinstructions.push(DateMicroinstructions.timeStamp());
                        break;

                    case "?TIME":
                        result.append(DateMicroinstructions.dateY2k());
                        break;

                    case "?TIMESTAMP":
                        result.append(DateMicroinstructions.timeStamp());
                        break;

                    case ".STACK":
                        result.append(stackMicroinstructions.show());
                        break;

                    case "CR":
                        result.append("\r\n");
                        break;

                    case ".DAY":
                        result.append(DateMicroinstructions.day());
                        break;

                    case ".MONTH":
                        result.append(DateMicroinstructions.month());
                        break;

                    case ".MTH":
                        result.append(DateMicroinstructions.month());
                        break;

                    case ".YEAR":
                        result.append(DateMicroinstructions.year());
                        break;

                    case ".YR":
                        result.append(DateMicroinstructions.year());
                        break;

                    case ".MILLISECONDS":
                        result.append(DateMicroinstructions.milliseconds());
                        break;

                    case ".DATE":
                        result.append(DateMicroinstructions.dateY2k());
                        break;

                    case ".DATESF":
                        result.append(DateMicroinstructions.dateY2k());
                        break;

                    case ".DATESIMPLEFORMAT":
                        result.append(DateMicroinstructions.dateY2k());
                        break;

                    case ".DATETIME":
                        result.append(DateMicroinstructions.now());
                        break;

                    case ".DATEBRITISH":
                        result.append(DateMicroinstructions.dateBritish());
                        break;
                    case ".DATEUSA":
                        result.append(DateMicroinstructions.dateUSA());
                        break;

                    case ".TIME":
                        result.append(DateMicroinstructions.time());
                        break;
                    case ".TIMESTAMP":
                        result.append(DateMicroinstructions.timeStamp());
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
                        result.append(stackMicroinstructions.getCurrentMode());
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
                        String serverUlr = stackMicroinstructions.pop();
                        result.append(Utilities.getIpAddressOfHosst(serverUlr));
                        break;

                    case ".SIP":
                        String serverUlr2 = stackMicroinstructions.pop();
                        result.append(Utilities.getIpAddressOfHosst(serverUlr2));
                        break;

                    case "!":
                        String value = stackMicroinstructions.pop();
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
                        stackMicroinstructions.push(currentvariableValue);
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
                result.append("\nThe stackMicroinstructions is empty");
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
