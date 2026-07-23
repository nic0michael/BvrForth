package za.co.bvr.forth.microinstructions;

import za.co.bvr.forth.exceptions.StackIsEmptyException;
import za.co.bvr.forth.stack.ForthStack;
import za.co.bvr.forth.utils.MathsUtils;

public class MathMicroinstructions {

    private final MathsUtils mathsUtils = new MathsUtils();
    private final ForthStack stack = ForthStack.INSTANCE;

    public String sqrt() {
        try {
            double value = stack.popDouble();
            stack.push(mathsUtils.sqrt(value));
            return "";
        } catch (StackIsEmptyException e) {
            return "\n\nStackIsEmpty";
        } catch (NumberFormatException e) {
            return "\n\nNumberFormatException";
        }
    }

    public String power() {
        try {
            double exponent = stack.popDouble();
            double base = stack.popDouble();
            stack.push(mathsUtils.pow(base, exponent));
            return "";
        } catch (StackIsEmptyException e) {
            return "\n\nStackIsEmpty";
        } catch (NumberFormatException e) {
            return "\n\nNumberFormatException";
        }
    }

    public String max() {
        try {
            double second = stack.popDouble();
            double first = stack.popDouble();
            stack.push(mathsUtils.max(first, second));
            return "";
        } catch (StackIsEmptyException e) {
            return "\n\nStackIsEmpty";
        } catch (NumberFormatException e) {
            return "\n\nNumberFormatException";
        }
    }

    public String min() {
        try {
            double second = stack.popDouble();
            double first = stack.popDouble();
            stack.push(mathsUtils.min(first, second));
            return "";
        } catch (StackIsEmptyException e) {
            return "\n\nStackIsEmpty";
        } catch (NumberFormatException e) {
            return "\n\nNumberFormatException";
        }
    }

    public String round() {
        try {
            double value = stack.popDouble();
            stack.push(mathsUtils.round(value));
            return "";
        } catch (StackIsEmptyException e) {
            return "\n\nStackIsEmpty";
        } catch (NumberFormatException e) {
            return "\n\nNumberFormatException";
        }
    }

    public String floor() {
        try {
            double value = stack.popDouble();
            stack.push(mathsUtils.floor(value));
            return "";
        } catch (StackIsEmptyException e) {
            return "\n\nStackIsEmpty";
        } catch (NumberFormatException e) {
            return "\n\nNumberFormatException";
        }
    }

    public String ceil() {
        try {
            double value = stack.popDouble();
            stack.push(mathsUtils.ceil(value));
            return "";
        } catch (StackIsEmptyException e) {
            return "\n\nStackIsEmpty";
        } catch (NumberFormatException e) {
            return "\n\nNumberFormatException";
        }
    }

    public String sin() {
        try {
            double value = stack.popDouble();
            stack.push(mathsUtils.sin(value));
            return "";
        } catch (StackIsEmptyException e) {
            return "\n\nStackIsEmpty";
        } catch (NumberFormatException e) {
            return "\n\nNumberFormatException";
        }
    }

    public String cos() {
        try {
            double value = stack.popDouble();
            stack.push(mathsUtils.cos(value));
            return "";
        } catch (StackIsEmptyException e) {
            return "\n\nStackIsEmpty";
        } catch (NumberFormatException e) {
            return "\n\nNumberFormatException";
        }
    }

    public String tan() {
        try {
            double value = stack.popDouble();
            stack.push(mathsUtils.tan(value));
            return "";
        } catch (StackIsEmptyException e) {
            return "\n\nStackIsEmpty";
        } catch (NumberFormatException e) {
            return "\n\nNumberFormatException";
        }
    }

    public String asin() {
        try {
            double value = stack.popDouble();
            stack.push(mathsUtils.asin(value));
            return "";
        } catch (StackIsEmptyException e) {
            return "\n\nStackIsEmpty";
        } catch (NumberFormatException e) {
            return "\n\nNumberFormatException";
        }
    }

    public String acos() {
        try {
            double value = stack.popDouble();
            stack.push(mathsUtils.acos(value));
            return "";
        } catch (StackIsEmptyException e) {
            return "\n\nStackIsEmpty";
        } catch (NumberFormatException e) {
            return "\n\nNumberFormatException";
        }
    }

    public String atan() {
        try {
            double value = stack.popDouble();
            stack.push(mathsUtils.atan(value));
            return "";
        } catch (StackIsEmptyException e) {
            return "\n\nStackIsEmpty";
        } catch (NumberFormatException e) {
            return "\n\nNumberFormatException";
        }
    }

    public String atan2() {
        try {
            double x = stack.popDouble();
            double y = stack.popDouble();
            stack.push(mathsUtils.atan2(y, x));
            return "";
        } catch (StackIsEmptyException e) {
            return "\n\nStackIsEmpty";
        } catch (NumberFormatException e) {
            return "\n\nNumberFormatException";
        }
    }

    public String sinh() {
        try {
            double value = stack.popDouble();
            stack.push(mathsUtils.sinh(value));
            return "";
        } catch (StackIsEmptyException e) {
            return "\n\nStackIsEmpty";
        } catch (NumberFormatException e) {
            return "\n\nNumberFormatException";
        }
    }

    public String cosh() {
        try {
            double value = stack.popDouble();
            stack.push(mathsUtils.cosh(value));
            return "";
        } catch (StackIsEmptyException e) {
            return "\n\nStackIsEmpty";
        } catch (NumberFormatException e) {
            return "\n\nNumberFormatException";
        }
    }

    public String tanh() {
        try {
            double value = stack.popDouble();
            stack.push(mathsUtils.tanh(value));
            return "";
        } catch (StackIsEmptyException e) {
            return "\n\nStackIsEmpty";
        } catch (NumberFormatException e) {
            return "\n\nNumberFormatException";
        }
    }

    public String exp() {
        try {
            double value = stack.popDouble();
            stack.push(mathsUtils.exp(value));
            return "";
        } catch (StackIsEmptyException e) {
            return "\n\nStackIsEmpty";
        } catch (NumberFormatException e) {
            return "\n\nNumberFormatException";
        }
    }

    public String log() {
        try {
            double value = stack.popDouble();
            stack.push(mathsUtils.log(value));
            return "";
        } catch (StackIsEmptyException e) {
            return "\n\nStackIsEmpty";
        } catch (NumberFormatException e) {
            return "\n\nNumberFormatException";
        }
    }

    public String log10() {
        try {
            double value = stack.popDouble();
            stack.push(mathsUtils.log10(value));
            return "";
        } catch (StackIsEmptyException e) {
            return "\n\nStackIsEmpty";
        } catch (NumberFormatException e) {
            return "\n\nNumberFormatException";
        }
    }

    public String degreesToRadians() {
        try {
            double value = stack.popDouble();
            stack.push(mathsUtils.toRadians(value));
            return "";
        } catch (StackIsEmptyException e) {
            return "\n\nStackIsEmpty";
        } catch (NumberFormatException e) {
            return "\n\nNumberFormatException";
        }
    }

    public String radiansToDegrees() {
        try {
            double value = stack.popDouble();
            stack.push(mathsUtils.toDegrees(value));
            return "";
        } catch (StackIsEmptyException e) {
            return "\n\nStackIsEmpty";
        } catch (NumberFormatException e) {
            return "\n\nNumberFormatException";
        }
    }

    public String abs() {
        try {
            double value = stack.popDouble();
            stack.push(mathsUtils.abs(value));
            return "";
        } catch (StackIsEmptyException e) {
            return "\n\nStackIsEmpty";
        } catch (NumberFormatException e) {
            return "\n\nNumberFormatException";
        }
    }

    public String signum() {
        try {
            double value = stack.popDouble();
            stack.push(mathsUtils.signum(value));
            return "";
        } catch (StackIsEmptyException e) {
            return "\n\nStackIsEmpty";
        } catch (NumberFormatException e) {
            return "\n\nNumberFormatException";
        }
    }

    public String hypot() {
        try {
            double y = stack.popDouble();
            double x = stack.popDouble();
            stack.push(mathsUtils.hypot(x, y));
            return "";
        } catch (StackIsEmptyException e) {
            return "\n\nStackIsEmpty";
        } catch (NumberFormatException e) {
            return "\n\nNumberFormatException";
        }
    }

    public String cbrt() {
        try {
            double value = stack.popDouble();
            stack.push(mathsUtils.cbrt(value));
            return "";
        } catch (StackIsEmptyException e) {
            return "\n\nStackIsEmpty";
        } catch (NumberFormatException e) {
            return "\n\nNumberFormatException";
        }
    }

    public String remainder() {
        try {
            double second = stack.popDouble();
            double first = stack.popDouble();
            stack.push(mathsUtils.remainder(first, second));
            return "";
        } catch (StackIsEmptyException e) {
            return "\n\nStackIsEmpty";
        } catch (NumberFormatException e) {
            return "\n\nNumberFormatException";
        }
    }

    public String copySign() {
        try {
            double sign = stack.popDouble();
            double magnitude = stack.popDouble();
            stack.push(mathsUtils.copySign(magnitude, sign));
            return "";
        } catch (StackIsEmptyException e) {
            return "\n\nStackIsEmpty";
        } catch (NumberFormatException e) {
            return "\n\nNumberFormatException";
        }
    }

    public String nextUp() {
        try {
            double value = stack.popDouble();
            stack.push(mathsUtils.nextUp(value));
            return "";
        } catch (StackIsEmptyException e) {
            return "\n\nStackIsEmpty";
        } catch (NumberFormatException e) {
            return "\n\nNumberFormatException";
        }
    }

    public String nextDown() {
        try {
            double value = stack.popDouble();
            stack.push(mathsUtils.nextDown(value));
            return "";
        } catch (StackIsEmptyException e) {
            return "\n\nStackIsEmpty";
        } catch (NumberFormatException e) {
            return "\n\nNumberFormatException";
        }
    }

    public String ulp() {
        try {
            double value = stack.popDouble();
            stack.push(mathsUtils.ulp(value));
            return "";
        } catch (StackIsEmptyException e) {
            return "\n\nStackIsEmpty";
        } catch (NumberFormatException e) {
            return "\n\nNumberFormatException";
        }
    }

    public String random() {
        stack.push(mathsUtils.random());
        return "";
    }

    public String pi() {
        stack.push(mathsUtils.pi());
        return "";
    }

    public String e() {
        stack.push(mathsUtils.e());
        return "";
    }
}
