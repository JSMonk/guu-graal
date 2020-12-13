package guulang.nodes;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;
import guulang.GuuLang;

@NodeInfo(description = "Node which represent 'main' procedure call and start of the program")
public class ProgramEntry extends GuuExpression {
    private final GuuLang language;

    protected ProgramEntry(GuuLang language) {
        this.language = language;
    }

    @Override
    public long executeLong(VirtualFrame frame) {
        var currentExecutionContext = GuuLang.getCurrentContext();
        var procedureRegistry = currentExecutionContext.getProcedureRegistry();
        var mainProcedureIdentifier = new ProcedureIdentifierNode(
                procedureRegistry,
                GuuLang.MAIN_PROCEDURE_NAME
        );
        var callMain = new CallStatement(mainProcedureIdentifier);
        callMain.executeVoid(frame);
        return 0;
    }

    @Override
    public Object execute(VirtualFrame frame) {
        return executeLong(frame);
    }

    public static ProgramEntry create(GuuLang language) {
        return new ProgramEntry(language);
    }
}
