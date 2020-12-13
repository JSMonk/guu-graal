package guulang;


import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.frame.FrameDescriptor;
import guulang.nodes.GuuRootNode;
import guulang.parser.GuuParser;
import guulang.runtime.GuuContext;
import guulang.runtime.GuuProcedureRegistry;

@TruffleLanguage.Registration(
        id = GuuLang.ID,
        name = GuuLang.NAME,
        characterMimeTypes = GuuLang.MIME_TYPE
)
public final class GuuLang extends TruffleLanguage<GuuContext> {
    public static final String ID = "guu";
    public static final String NAME = "Guu";
    public static final String MIME_TYPE ="x-application/guu";
    public static final String FILE_EXTENSION =".guu";
    public static final String MAIN_PROCEDURE_NAME = "main";

    @Override
    protected GuuContext createContext(Env env) {
        return new GuuContext(this, new GuuProcedureRegistry());
    }

    public static GuuContext getCurrentContext() {
        return getCurrentContext(GuuLang.class);
    }

    @Override
    protected CallTarget parse(ParsingRequest request) throws Exception {
        var source = request.getSource();
        var mainProcedureCall = GuuParser.parse(this, source);
        var rootNode = GuuRootNode.create(this, new FrameDescriptor(), mainProcedureCall);
        return Truffle.getRuntime().createCallTarget(rootNode);
    }
}
