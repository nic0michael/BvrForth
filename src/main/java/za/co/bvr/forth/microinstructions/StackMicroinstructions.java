package za.co.bvr.forth.microinstructions;

import org.apache.commons.codec.binary.Base64;
import za.co.bvr.forth.exceptions.StackIsEmptyException;
import za.co.bvr.forth.stack.ForthStack;
import za.co.bvr.forth.utils.Utilities;

import java.util.Stack;
import java.util.concurrent.ThreadLocalRandom;

public class StackMicroinstructions {

    private final ForthStack stack = ForthStack.INSTANCE;

    public String dot() {
        try {
            return stack.pop();
        } catch (StackIsEmptyException e) {
            return "\n\nStackIsEmpty";
        }
    }


    public int size() {
        return stack.size();
    }

    public void push(String value) {
        stack.push(value);
    }

    public void push(byte[] value) {
        push(new String(value));
    }

    public void push(int value) {
        stack.push(value);
    }

    public void push(double value) {
        stack.push(value);
    }

    public String pop() throws StackIsEmptyException {
        return stack.pop();
    }

    public int popInt() throws StackIsEmptyException, NumberFormatException {
        return stack.popInt();
    }

    public double popDouble() throws StackIsEmptyException, NumberFormatException {
        return stack.popDouble();
    }

    public void clear() {
        stack.clear();
    }

    public void drop() throws StackIsEmptyException {
        stack.drop();
    }

    public void swap() throws StackIsEmptyException { // 1 2 -> 2 1
        stack.swap();
    }

    public void rot() throws StackIsEmptyException { // 1 2 3    ->  2 3 1
        stack.rot();
    }

    public String over() throws StackIsEmptyException, NumberFormatException { // 1 2     ->  1 2 1
        return stack.over();
    }

    public void dup() throws StackIsEmptyException { // 1 2     ->  1 2 2
        stack.dup();
    }

    public void qdup() { // ?dup
       stack.qdup();
    }

    public void add() throws StackIsEmptyException {
        stack.add();
    }

    public void subtract() throws StackIsEmptyException {
        stack.subtract();
    }

    public void multiply() throws StackIsEmptyException {
        stack.multiply();
    }

    public void divide() throws StackIsEmptyException {
        stack.divide();
    }

    public void modulus() throws StackIsEmptyException {
        stack.modulus();
    }

     public void addDoubles() throws StackIsEmptyException {
        stack.addDoubles();
    }

    public void subtractDoubles() throws StackIsEmptyException {
        stack.subtractDoubles();
    }

    public void multiplyDoubles() throws StackIsEmptyException {
        stack.multiplyDoubles();
    }

    public void divideDoubles() throws StackIsEmptyException {
        stack.divideDoubles();
    }

    public void modulusDoubles() throws StackIsEmptyException {
        stack.modulusDoubles();
    }

    public void squareDoubles() throws StackIsEmptyException {
        stack.squareDoubles();
    }

    public void and() throws StackIsEmptyException {
        stack.and();
    }

    public void or() throws StackIsEmptyException {
        stack.or();
    }

    public void exor() throws StackIsEmptyException {
        stack.exor();
    }

    public void square() throws StackIsEmptyException {
       stack.square();
    }

    public void power() throws StackIsEmptyException {
        stack.power();
    }

    public void max() throws StackIsEmptyException {
        stack.max();
    }

    public void min() throws StackIsEmptyException {
        stack.min();
    }

    public void sqrt() throws StackIsEmptyException {
        stack.sqrt();
    }

    public void round() throws StackIsEmptyException {
        stack.round();
    }

    public void floor() throws StackIsEmptyException {
        double value = popDouble();
        push(Math.floor(value));
    }

    public void ceil() throws StackIsEmptyException {
        stack.ceil();
    }

    public void radiansToDegrees() throws StackIsEmptyException {
        stack.radiansToDegrees();
    }

    public void degreesToRadians() throws StackIsEmptyException {
        stack.degreesToRadians();
    }

    public void sin() throws StackIsEmptyException {
        stack.sin();
    }

    public void cos() throws StackIsEmptyException {
        stack.cos();
    }

    public void tan() throws StackIsEmptyException {
        stack.tan();
    }

    public void log() throws StackIsEmptyException {
        stack.log();
    }

    public void logBase10() throws StackIsEmptyException {
        stack.logBase10();
    }

    public void random() throws StackIsEmptyException {
        stack.random();
    }

    public void base64Encode() throws StackIsEmptyException {
        stack.base64Encode();
    }

    public void base64Decode() throws StackIsEmptyException {
            stack.base64Decode();
    }

    public void convertToBinary() throws StackIsEmptyException {
            stack.convertToBinary();
    }

    public void convertToHex() throws StackIsEmptyException {
            stack.convertToHex();
    }

    public void convertToOctal() throws StackIsEmptyException {
            stack.convertToOctal();
    }

    public void convertHexToDecimal() throws StackIsEmptyException {
            stack.convertHexToDecimal();
    }

    public void convertOctalToDecimal() throws StackIsEmptyException {
            stack.convertOctalToDecimal();
    }

    public void convertBinaryToDecimal() throws StackIsEmptyException {
        stack.convertBinaryToDecimal();
    }

    public void count() {

    }

    public String get() {
        return stack.get();
    }

    public int getInt() {
        return stack.getInt();
    }

    public String show() {
        return stack.show();
    }

    public void setModeToDecimal() {
        stack.setModeToDecimal();
    }

    public void setModeToHex() {
       stack.setModeToHex();
    }

    public void setModeToOctal() {
        stack.setModeToOctal();
    }

    public void setModeToBinary() {
        stack.setModeToBinary();
    }

    public String getCurrentMode(){
        return stack.getCurrentMode();
    }

    public boolean currentModeIsDecimal(){
        return stack.currentModeIsDecimal();
    }

    String convertToCurrentMode(String value) {
        return stack.getCurrentMode();
    }

    public void StringtoAscii() throws StackIsEmptyException {
        stack.StringtoAscii();
    }

    public void intToChar() throws StackIsEmptyException {
        stack.intToChar();
    }

    public void equals() throws StackIsEmptyException {
        stack.equals();
    }

    public void equalsZero() throws StackIsEmptyException {
        stack.equalsZero();
    }

    public void smallerThanZero() throws StackIsEmptyException {
        stack.smallerThanZero();
    }



    public void greaterThanZero() throws StackIsEmptyException {
        stack.greaterThanZero();
    }

    public void greaterThan() throws StackIsEmptyException {
        stack.greaterThan();
    }

    public void smallerThan() throws StackIsEmptyException {
        stack.smallerThan();
    }

    public void equalsOrGreaterThan() throws StackIsEmptyException {
        stack.equalsOrGreaterThan();
    }

    public void smallerThanOrEquals() throws StackIsEmptyException {
        stack.smallerThanOrEquals();
    }

    public void not() throws StackIsEmptyException {
        stack.not();
    }

    public void equalsDoubles() throws StackIsEmptyException {
        stack.equalsDoubles();
    }

    public void equalsZeroDoubles() throws StackIsEmptyException {
        stack.equalsZeroDoubles();
    }

    public void smallerThanZeroDoubles() throws StackIsEmptyException {
        stack.smallerThanZeroDoubles();
    }

    public void greaterThanZeroDoubles() throws StackIsEmptyException {
        stack.greaterThanZeroDoubles();
    }

    public void greaterThanDoubles() throws StackIsEmptyException {
        stack.greaterThanDoubles();
    }

    public void smallerThanDoubles() throws StackIsEmptyException {
        stack.smallerThanDoubles();
    }


    public void equalsOrGreaterThanDoubles() throws StackIsEmptyException {
        stack.equalsOrGreaterThanDoubles();
    }

    public void smallerThanOrEqualsDoubles() throws StackIsEmptyException {
        stack.smallerThanOrEqualsDoubles();
    }

    public void notDoubles() throws StackIsEmptyException {
        stack.notDoubles();
    }


    public String onePlus() throws StackIsEmptyException {
        int value = popInt();
        value = 1 + value;
        return "" + value;
    }

    public String oneMinis() throws StackIsEmptyException {
        int value = popInt();
        value = value - 1;
        return "" + value;
    }

    public String twoPlus() throws StackIsEmptyException {
        int value = popInt();
        value = value + 2;
        return "" + value;
    }

    public String twoMinus() throws StackIsEmptyException {
        int value = popInt();
        value = value - 2;
        return "" + value;
    }

    public String twoDivide() throws StackIsEmptyException {
        int value = popInt();
        value = value / 2;
        return "" + value;
    }

    public String twoTimes() throws StackIsEmptyException {
        int value = popInt();
        value = value * 2;
        return "" + value;
    }
}