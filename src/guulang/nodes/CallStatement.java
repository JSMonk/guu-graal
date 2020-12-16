package guulang.nodes;

import com.oracle.truffle.api.frame.VirtualFrame;

public class CallStatement extends GuuStatement {
    private ProcedureIdentifierNode procedureIdentifier;

    protected CallStatement(ProcedureIdentifierNode procedureIdentifier) {
        this.procedureIdentifier = procedureIdentifier;
    }

    @Override
    public void executeVoid(VirtualFrame frame) {
        var callTarget = procedureIdentifier.execute(frame);
        callTarget.call();
    }

    public static CallStatement create(ProcedureIdentifierNode procedureIdentifier) {
        return new CallStatement(procedureIdentifier);
    }
}
