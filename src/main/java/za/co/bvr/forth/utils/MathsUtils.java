package za.co.bvr.forth.utils;

/**
 * Simple wrapper around java.lang.Math so it can be instantiated
 * and used by the Forth interpreter.
 */
public class MathsUtils {

    public double sqrt(double value) {
        return Math.sqrt(value);
    }

    public double pow(double base, double exponent) {
        return Math.pow(base, exponent);
    }

    public int max(int first, int second) {
        return Math.max(first, second);
    }

    public double max(double first, double second) {
        return Math.max(first, second);
    }

    public int min(int first, int second) {
        return Math.min(first, second);
    }

    public double min(double first, double second) {
        return Math.min(first, second);
    }

    public long round(double value) {
        return Math.round(value);
    }

    public double floor(double value) {
        return Math.floor(value);
    }

    public double ceil(double value) {
        return Math.ceil(value);
    }

    public double sin(double radians) {
        return Math.sin(radians);
    }

    public double cos(double radians) {
        return Math.cos(radians);
    }

    public double tan(double radians) {
        return Math.tan(radians);
    }

    public double asin(double value) {
        return Math.asin(value);
    }

    public double acos(double value) {
        return Math.acos(value);
    }

    public double atan(double value) {
        return Math.atan(value);
    }

    public double atan2(double y, double x) {
        return Math.atan2(y, x);
    }

    public double sinh(double value) {
        return Math.sinh(value);
    }

    public double cosh(double value) {
        return Math.cosh(value);
    }

    public double tanh(double value) {
        return Math.tanh(value);
    }

    public double exp(double value) {
        return Math.exp(value);
    }

    public double log(double value) {
        return Math.log(value);
    }

    public double log10(double value) {
        return Math.log10(value);
    }

    public double abs(double value) {
        return Math.abs(value);
    }

    public int abs(int value) {
        return Math.abs(value);
    }

    public double signum(double value) {
        return Math.signum(value);
    }

    public int signum(int value) {
        return Integer.signum(value);
    }

    public double toRadians(double degrees) {
        return Math.toRadians(degrees);
    }

    public double toDegrees(double radians) {
        return Math.toDegrees(radians);
    }

    public double random() {
        return Math.random();
    }

    public double hypot(double x, double y) {
        return Math.hypot(x, y);
    }

    public double cbrt(double value) {
        return Math.cbrt(value);
    }

    public double remainder(double first, double second) {
        return Math.IEEEremainder(first, second);
    }

    public double copySign(double magnitude, double sign) {
        return Math.copySign(magnitude, sign);
    }

    public double nextUp(double value) {
        return Math.nextUp(value);
    }

    public double nextDown(double value) {
        return Math.nextDown(value);
    }

    public double ulp(double value) {
        return Math.ulp(value);
    }

    public double pi() {
        return Math.PI;
    }

    public double e() {
        return Math.E;
    }
}
