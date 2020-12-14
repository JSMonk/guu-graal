package guulang;

import guulang.exceptions.ProcedureNotFoundException;
import guulang.exceptions.VariableNotFoundException;
import guulang.parser.ParseException;
import guulang.runtime.REPL;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.PolyglotException;
import org.graalvm.polyglot.Source;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.Scanner;

public abstract class GuuMain {
    public static void main (String[] args) throws IOException {
        Optional<String> filepath = args.length >= 1
                ? Optional.of(args[0])
                : Optional.empty();
        System.exit(executeSource(filepath));
    }

    private static int executeSource(Optional<String> filepath) throws IOException {
       try (var ctx = Context.newBuilder(GuuLang.ID).out(System.out).build()) {
           if (filepath.isEmpty()) {
               runREPL(ctx);
           } else {
               executeFile(ctx, filepath.get());
           }
           return 0;
       } catch (IllegalArgumentException e) {
           System.err.println(e.getMessage());
           return 1;
       } catch (PolyglotException ex) {
           if (ex.isInternalError()) {
               ex.printStackTrace();
           } else {
               System.err.println(ex.getMessage());
           }
           return 1;
       }
    }

    private static void runREPL(Context context) throws IOException {
        var io = new Scanner(System.in);

        while (true) {
            System.out.print(REPL.PREFIX);
            var code = io.nextLine().trim();

            if (code.equalsIgnoreCase(REPL.EXIT_COMMAND)) {
                break;
            }
            var source = Source.newBuilder(GuuLang.ID, code, "<stdin>").build();
            try {
                var expressionResult = context.eval(source);
                System.out.println(REPL.formatExpressionResult(expressionResult));
            } catch (Exception e) {
               System.err.println(e.getMessage());
            }
        }
    }

    private static void executeFile(Context context, String filepath) throws IOException {
        var source = Source.newBuilder(GuuLang.ID, new File(filepath)).build();
        context.eval(source);
    }
}
