package guulang.nodes;

import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.api.frame.VirtualFrame;
import guulang.GuuLang;

@NodeInfo(
        language = GuuLang.NAME,
        description = "Abstract base node for all statements in Guu language."
)
public abstract class GuuStatement extends GuuNode {
    public abstract void executeVoid(VirtualFrame frame);

    @Override
    public final Object execute(VirtualFrame frame) {
        executeVoid(frame);
        return null;
    }
}
