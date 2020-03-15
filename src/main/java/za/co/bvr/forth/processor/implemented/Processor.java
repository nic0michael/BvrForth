package za.co.bvr.forth.processor.implemented;

import lombok.extern.java.Log;
import org.apache.commons.lang3.StringUtils;
import za.co.bvr.forth.compiler.ForthCompiler;
import za.co.bvr.forth.dictionary.ForthDictionary;
import za.co.bvr.forth.dictionary.Verb;
import za.co.bvr.forth.exceptions.LineIsEmptyException;
import za.co.bvr.forth.exceptions.VerbNotInDictionaryException;
import za.co.bvr.forth.stack.ForthStack;
import za.co.bvr.forth.variables.VariablesStore;

/**
 *
 * @author nicm
 */
@Log
public class Processor {

    private final String VERB = "VERB";
    private final String VARIABLE = "VARIABLE";
    private final String CONSTANT = "CONSTANT";
    private final String NUMBER = "NUMBER";
    private final String QUOTE = "\"";
    private final String DEFINING_VERB = ":";
    private final String END_DEFINING_VERB = ";";
    private final String IF_TRUE = "1";
    private final String IF = "IF";
    private final String ELSE = "ELSE";
    private final String THEN = "THEN";
    private final String LOOP_DO = "DO";
    private final String LOOP_WHILE = "WHILE";
    private final String LOOP = "LOOP";

    private final ForthDictionary dictionary = ForthDictionary.INSTANCE;
    private final ForthStack stack = ForthStack.INSTANCE;
    private final VariablesStore variables = VariablesStore.INSTANCE;
    private final ForthCompiler compiler = new ForthCompiler();

    private boolean definingNewVerb = false;
    private boolean definingLoop = false;

    private boolean definingVariable = false;
    private boolean definingStringVariable = false;

    private boolean definingConstant = false;
    private boolean definingNumericConstant = false;
    private boolean definingStringConstant = false;

    private boolean definingString = false;
    private boolean lastIfStatementResult = false;
    private boolean canProcessLine = true;

    private StringBuffer verbDefinition = new StringBuffer();
    private StringBuffer loopDefinition = new StringBuffer();
    private StringBuffer stringDefinition = new StringBuffer();
    private StringBuffer ifStatementDefinition = new StringBuffer();

    private int verbDefinitionStep;
    private String verbName;
    private String variableName;
    private String constantName;

    public String process(String line) throws Exception {

        String result = null;
        StringBuffer processedLine = new StringBuffer();
        processedLine = preProcess(line);
//        log.info("Processor processedLine : " + processedLine);

        result = postProcess(processedLine);

        return result;
    }

    public StringBuffer preProcess(String line) throws Exception {
        StringBuffer preProcessedLine = new StringBuffer();
        stringDefinition = new StringBuffer();

//        log.info("Processor processing line : " + line + "\n\n");
        if (StringUtils.isEmpty(line) || " ".equals(line)) {
            return new StringBuffer("");
        }

        canProcessLine = true;
        String[] lineItems = line.split(" ");
        int theCount = 1;
        int stringCount = 1;

        for (String lineItem : lineItems) {
//            log.info("Processor preProcess lineItem: " + lineItem);

            if (IF.equalsIgnoreCase(lineItem)) {
                definingIfStatement(stack.pop());

            } else if (ELSE.equalsIgnoreCase(lineItem)) {
                definingElseIfStatement();

            } else if (THEN.equalsIgnoreCase(lineItem)) {
                canProcessLine = true;

            } else if (!canProcessLine) {
                log.info("Processor preProcess CAN NOT PROCESS lineItem: " + lineItem);

            } else if (DEFINING_VERB.equals(lineItem)) {
                startDictionaryDefinition();

            } else if (END_DEFINING_VERB.equals(lineItem)) {
                endDictionaryDefinition(lineItem);

            } else if (definingNewVerb) {
                definingNewVerb(lineItem);

            } else if (StringUtils.isNumeric(lineItem)) {
                stack.push(lineItem);

            } else if (StringUtils.isNumeric(lineItem)) {

            } else if (VARIABLE.equals(lineItem)) {
                definingVariable = true;

            } else if (definingVariable) {
                definingVariable(lineItem);

            } else if (definingStringVariable) {
                definingStringVariable(lineItem);

            } else if (CONSTANT.equals(lineItem)) {
                definingConstant = true;

            } else if (definingConstant) {
                definingConstant(lineItem);

            } else if (definingStringConstant) {
                definingStringConstant(lineItem);

            } else if (definingNumericConstant) {
                definingNumericConstant(lineItem);

            }  else if (lineItemIsString(lineItem)) {
                definingString = !definingString;                
                if (stringCount > 1) {
                    stringDefinition.append(" ");
                    stack.pop();
                }
                stringDefinition.append(lineItem);
                definingString(stringDefinition.toString());
                stringCount++;

            }else if (definingString) {
                if (stringCount > 1) {
                    stringDefinition.append(" ");
                    stack.pop();
                }
                stringDefinition.append(lineItem);
                
                stringCount++;
                definingString(stringDefinition.toString());

            } else {
//                log.info("LineProcessor lineItem : "+lineItem);
                String def = dictionary.getCompiledDefinition(lineItem).toUpperCase();
                String[] definitions = def.split(" ");
                int count = 1;
                for (String definition : definitions) {
//                    log.info("LineProcessor definition"+count+" : "+definition);

                    if (StringUtils.isNumeric(definition)) {
//                        log.info("LineProcessor is numeric : "+definition);
                        stack.push(lineItem);

                    } else if (variables.isVariable(definition.toUpperCase())) {
//                        log.info("LineProcessor is variable : "+definition);

                    } else {
//                        log.info("LineProcessor is verb"+count+" : "+definition);
                        if (count > 1 || theCount > 1) {
                            preProcessedLine.append(" ");
                        }
                        preProcessedLine.append(definition);
                    }
                    count++;
                    theCount++;
                }
            }
        }

//        log.info("Processor executions : " + executions);
        return preProcessedLine;
    }

    public String postProcess(StringBuffer processedLine) throws Exception {

        if (StringUtils.isEmpty(processedLine) || " ".equals(processedLine.toString())) {
            return " ";
        }

        StringBuilder postProcessLine = new StringBuilder();
        VerbPreProcessor verbProcessor = new VerbPreProcessor();

        String preProcessedLine = processedLine.toString();
        String[] lineItems = preProcessedLine.split(" ");

        for (String lineItem : lineItems) {
//            log.info("Processor postProcess lineItem : " + lineItem);

            String verbResult = verbProcessor.process(lineItem);
            postProcessLine.append(verbResult);

        }

        return postProcessLine.toString();
    }

    private boolean variableNameIsString(String lineItem) {
        return lineItem.contains("$");
    }

    private boolean lineItemIsString(String lineItem) {
        return lineItem.contains(QUOTE);
    }

    private void definingString(String lineItem) {
        if (StringUtils.isEmpty(lineItem) || " ".equals(lineItem)) {
            return;
        }
        String string = lineItem.replace(QUOTE, "");
        stack.push(string);
    }

    private String complileVerb(String verbCode) {
        String compiledDefinition = null;
        try {
            compiledDefinition = compiler.compile(verbCode);
//            log.info("Processor complileVerb verbCode: " + verbCode + " compiledDefinition : " + compiledDefinition);

        } catch (VerbNotInDictionaryException ex) {
            compiledDefinition = verbCode;
            log.severe("Caught VerbNotInDictionaryException");

        } catch (LineIsEmptyException ex) {
            compiledDefinition = verbCode;
            log.severe("Caught LineIsEmptyException");
        }
        return compiledDefinition;
    }

    private void endDictionaryDefinition(String lineItem) {
//        log.info("Processor preProcess END_DEFINING_VERB lineItem: " + lineItem);
        definingNewVerb = false;
        String verbCode = verbDefinition.toString();
        String compiledVerbCode;
        compiledVerbCode = complileVerb(verbCode);
//        log.info("Processor postProcess Verb( verbName: " + verbName + " verbCode: " + verbCode + " compiledVerbCode : " + compiledVerbCode);
        Verb verb = new Verb(verbName, verbCode, compiledVerbCode);
        dictionary.addVerbToDictionary(verb);

    }

    private void startDictionaryDefinition() {
//        log.info("Processor preProcess DEFINING_VERB lineItem: " + lineItem);
        verbDefinitionStep = 0;
        definingNewVerb = true;
        verbDefinition = new StringBuffer();
    }

    private void definingNewVerb(String lineItem) {
        if (verbDefinitionStep == 0) {
            verbName = lineItem;
        } else {
            if (verbDefinitionStep > 1) {
                verbDefinition.append(" ");
            }
            verbDefinition.append(lineItem);
        }
        verbDefinitionStep++;
    }

    private void definingVariable(String lineItem) {

        if (variableNameIsString(lineItem)) {
            variableName = lineItem;
            definingStringVariable = true;
        } else {
            variables.addVariable(lineItem);
            definingVariable = false;
        }
    }

    private void definingStringVariable(String lineItem) {

        variables.addStringVariable(variableName, lineItem);
        definingStringVariable = false;
        definingVariable = false;
    }

    private void definingConstant(String lineItem) {

        if (variableNameIsString(lineItem)) {
            definingStringConstant = true;
            definingNumericConstant = false;
        } else {
            definingNumericConstant = true;
            definingStringConstant = false;
        }
        definingConstant = false;
        constantName = lineItem;
    }

    private void definingStringConstant(String lineItem) {

        variables.addStringConstant(variableName, lineItem);
        definingConstant = false;
        definingNumericConstant = false;
        definingStringConstant = false;
    }

    private void definingNumericConstant(String lineItem) {
        variables.addConstant(variableName, lineItem);
        definingConstant = false;
        definingNumericConstant = false;
        definingStringConstant = false;
    }

    private void definingIfStatement(String isTrue) {
        if (IF_TRUE.equals(isTrue)) {
            canProcessLine = true;
            lastIfStatementResult = true;
        } else {
            canProcessLine = false;
            lastIfStatementResult = false;
        }
    }

    private void definingElseIfStatement() {
        if (lastIfStatementResult) {
            canProcessLine = false;
        } else {
            canProcessLine = true;
        }
    }

}
