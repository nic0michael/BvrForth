package za.co.bvr.forth.microinstructions;

import za.co.bvr.forth.exceptions.StackIsEmptyException;
import za.co.bvr.forth.stack.ForthStack;

public class MathMicroinstructions {


    private final ForthStack stack = ForthStack.INSTANCE;

    public String sqrt() {
        try {
            double value = stack.popDouble();
            stack.push(Math.sqrt(value));
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
            stack.push(Math.pow(base, exponent));
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
            stack.push(Math.max(first, second));
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
            stack.push(Math.min(first, second));
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
            stack.push(Math.round(value));
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
            stack.push(Math.floor(value));
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
            stack.push(Math.ceil(value));
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
            stack.push(Math.sin(value));
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
            stack.push(Math.cos(value));
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
            stack.push(Math.tan(value));
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
            stack.push(Math.asin(value));
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
            stack.push(Math.acos(value));
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
            stack.push(Math.atan(value));
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
            stack.push(Math.atan2(y, x));
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
            stack.push(Math.sinh(value));
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
            stack.push(Math.cosh(value));
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
            stack.push(Math.tanh(value));
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
            stack.push(Math.exp(value));
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
            stack.push(Math.log(value));
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
            stack.push(Math.log10(value));
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
            stack.push(Math.toRadians(value));
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
            stack.push(Math.toDegrees(value));
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
            stack.push(Math.abs(value));
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
            stack.push(Math.signum(value));
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
            stack.push(Math.hypot(x, y));
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
            stack.push(Math.cbrt(value));
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
            stack.push(Math.IEEEremainder(first, second));
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
            stack.push(Math.copySign(magnitude, sign));
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
            stack.push(Math.nextUp(value));
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
            stack.push(Math.nextDown(value));
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
            stack.push(Math.ulp(value));
            return "";
        } catch (StackIsEmptyException e) {
            return "\n\nStackIsEmpty";
        } catch (NumberFormatException e) {
            return "\n\nNumberFormatException";
        }
    }

    public String random() {
        stack.push(Math.random());
        return "";
    }

    public String pi() {
        stack.push(Math.PI);
        return "";
    }

    public String e() {
        stack.push(Math.E);
        return "";
    }
}
