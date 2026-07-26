package za.co.bvr.forth.microinstructions;

public class MicroInstructions {

    private final StackMicroinstructions stackInstructions = new StackMicroinstructions();
    private final MathMicroinstructions mathInstructions = new MathMicroinstructions();

    public String execInstruction(String command) {

        return switch (command.toUpperCase()) {

            // Mathematical Functions
            case "SQRT"     -> mathInstructions.sqrt();
            case "POW"      -> mathInstructions.power();
            case "MAX"      -> mathInstructions.max();
            case "MIN"      -> mathInstructions.min();
            case "ROUND"    -> mathInstructions.round();
            case "FLOOR"    -> mathInstructions.floor();
            case "CEIL"     -> mathInstructions.ceil();

            // Trigonometry
            case "SIN"      -> mathInstructions.sin();
            case "COS"      -> mathInstructions.cos();
            case "TAN"      -> mathInstructions.tan();
            case "ASIN"     -> mathInstructions.asin();
            case "ACOS"     -> mathInstructions.acos();
            case "ATAN"     -> mathInstructions.atan();
            case "ATAN2"    -> mathInstructions.atan2();

            // Hyperbolic
            case "SINH"     -> mathInstructions.sinh();
            case "COSH"     -> mathInstructions.cosh();
            case "TANH"     -> mathInstructions.tanh();

            // Logarithms & Exponentials
            case "EXP"      -> mathInstructions.exp();
            case "LOG"      -> mathInstructions.log();
            case "LOG10"    -> mathInstructions.log10();

            // Angle Conversion
            case "DEG2RAD"  -> mathInstructions.degreesToRadians();
            case "RAD2DEG"  -> mathInstructions.radiansToDegrees();

            // Miscellaneous
            case "ABS"      -> mathInstructions.abs();
            case "SIGNUM"   -> mathInstructions.signum();
            case "HYPOT"    -> mathInstructions.hypot();
            case "CBRT"     -> mathInstructions.cbrt();
            case "REM"      -> mathInstructions.remainder();
            case "COPYSIGN" -> mathInstructions.copySign();
            case "NEXTUP"   -> mathInstructions.nextUp();
            case "NEXTDOWN" -> mathInstructions.nextDown();
            case "ULP"      -> mathInstructions.ulp();
            case "RANDOM"   -> mathInstructions.random();
            case "PI"       -> mathInstructions.pi();
            case "E"        -> mathInstructions.e();

            default         -> otherwiseMethod(command);
        };
    }

    private String otherwiseMethod(String command) {
        return "\n\nCommand " + command + " is not found";
    }
}