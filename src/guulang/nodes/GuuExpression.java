package guulang.nodes;

import com.oracle.truffle.api.dsl.TypeSystemReference;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.api.nodes.UnexpectedResultException;

@TypeSystemReference(GuuTypes.class)
@NodeInfo(description = "Abstract base node for all expressions.")
public abstract class GuuExpression extends GuuNode {
    public abstract Object execute(VirtualFrame frame);

    public long executeLong(VirtualFrame frame) throws UnexpectedResultException {
        return GuuTypesGen.expectLong(execute(frame));
    }

    public String executeString(VirtualFrame frame) throws UnexpectedResultException {
        return GuuTypesGen.expectString(execute(frame));
    }
}
