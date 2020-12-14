package guulang.runtime;

public final class REPL {
    public static final String PREFIX = "guu>> ";
    public static final String EXIT_COMMAND = ":q";
    private static final String RESET = "\u001B[0m";
    private static final String COLOR_WRAPPER = "\u001B[33m";

    public static String formatExpressionResult(Object result) {
        var builder = new StringBuilder();
        builder.append(COLOR_WRAPPER);
        builder.append(result.toString());
        builder.append(RESET);
        return builder.toString();
    }

    private REPL() throws IllegalAccessException {
        throw new IllegalAccessException("REPL constructor should not be called.");
    }
}
