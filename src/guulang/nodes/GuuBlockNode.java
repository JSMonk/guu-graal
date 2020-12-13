package guulang.nodes;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.BlockNode;
import com.oracle.truffle.api.nodes.BlockNode.ElementExecutor;
import com.oracle.truffle.api.nodes.NodeInfo;

@NodeInfo(shortName = "block", description = "The node implementing a source code block")
public class GuuBlockNode extends GuuStatement implements BlockNode.ElementExecutor<GuuStatement> {
    @Child private BlockNode<GuuStatement> block;

    protected GuuBlockNode(GuuStatement[] statements) {
        this.block = statements.length > 0
            ? BlockNode.create(statements, this)
            : null;
    }

    @Override
    public void executeVoid(VirtualFrame frame) {
       if (this.block == null) return;
       this.block.executeVoid(frame, BlockNode.NO_ARGUMENT);
    }

    @Override
    public void executeVoid(VirtualFrame frame, GuuStatement node, int index, int argument) {
        node.executeVoid(frame);
    }

    public static GuuBlockNode create(GuuStatement[] statements) {
        return new GuuBlockNode(statements);
    }
}
