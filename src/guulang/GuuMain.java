package guulang;

import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.PolyglotException;
import org.graalvm.polyglot.Source;

import java.io.File;
import java.io.IOException;

public abstract class GuuMain {
    public static void main (String[] args) throws IOException {
        if (args.length == 0) {
            System.err.println("filepath is required argument!");
            System.exit(1);
            return;
        }
        var filepath = args[0];
        System.exit(executeSource(filepath));
    }

    private static int executeSource(String filepath) throws IOException {
       try (Context ctx = Context.newBuilder(GuuLang.ID).out(System.out).build()) {
           var source = Source.newBuilder(GuuLang.ID, new File(filepath)).build();
           ctx.eval(source);
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
}
