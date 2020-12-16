package guulang.nodes;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.frame.VirtualFrame;
import guulang.exceptions.ProcedureNotFoundException;
import guulang.runtime.GuuProcedureRegistry;

public class ProcedureIdentifierNode extends GuuExpression {
    private GuuProcedureRegistry procedureRegistry;
    private String identifier;

    protected ProcedureIdentifierNode(
            GuuProcedureRegistry procedureRegistry,
            String identifier
    ) {
        this.procedureRegistry = procedureRegistry;
        this.identifier = identifier;
    }

    @Override
    public CallTarget execute(VirtualFrame frame) {
        var existedCallTarget = procedureRegistry.lookup(identifier, this);
        if (existedCallTarget == null) {
            CompilerDirectives.transferToInterpreter();
            throw new ProcedureNotFoundException(identifier, this);
        }
        return existedCallTarget;
    }

    public static ProcedureIdentifierNode create(
            GuuProcedureRegistry procedureRegistry,
            String identifier
    ) {
        return new ProcedureIdentifierNode(procedureRegistry, identifier);
    }
}
