package guulang.nodes;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.api.nodes.RootNode;
import guulang.GuuLang;

@NodeInfo(description = "The root of all Guu execution tree.")
public class GuuRootNode extends RootNode {
    @Child
    private GuuNode body;

    protected GuuRootNode(GuuLang lang, FrameDescriptor frameDescriptor, GuuNode body) {
        super(lang, frameDescriptor);
        this.body = body;
    }

    public Object execute(VirtualFrame frame) {
       return body.execute(frame);
    }

    public static GuuRootNode create(GuuLang lang, FrameDescriptor frameDescriptor, GuuNode body) {
        return new GuuRootNode(lang, frameDescriptor, body);
    }
}
