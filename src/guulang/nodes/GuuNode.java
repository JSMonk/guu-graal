package guulang.nodes;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeInfo;

@NodeInfo(description = "Base abstract node in Guu language.")
public abstract class GuuNode extends Node {
    public abstract Object execute(VirtualFrame frame);
}
