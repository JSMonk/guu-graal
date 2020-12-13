package guulang.nodes;

import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.NodeField;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.FrameSlot;
import com.oracle.truffle.api.frame.FrameSlotKind;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;
import guulang.GuuLang;

@NodeInfo(description = "Node which is responsible for assignment statement.")
@NodeChild(value = "valueNode", type = GuuExpression.class)
@NodeField(name = "slot", type = FrameSlot.class)
public abstract class AssignmentStatement extends GuuStatement {
    protected abstract FrameSlot getSlot();
    protected abstract GuuExpression getValueNode();
    public abstract void executeWrite(VirtualFrame frame, Object value);

    @Specialization(guards = "isLongOrIllegal()")
    protected void writeLong(VirtualFrame frame, long value) {
        var ctx = GuuLang.getCurrentContext();
        var globalScope = ctx.getGlobalScope();
        var slot = getSlot();
        globalScope.getFrameDescriptor().setFrameSlotKind(slot, FrameSlotKind.Long);
        globalScope.setLong(slot, value);
    }

    @Specialization(replaces = {"writeLong"})
    protected void write(VirtualFrame frame, Object value) {
        var ctx = GuuLang.getCurrentContext();
        var globalScope = ctx.getGlobalScope();
        var slot = getSlot();
        globalScope.getFrameDescriptor().setFrameSlotKind(slot, FrameSlotKind.Object);
        globalScope.setObject(slot, value);
    }

    protected boolean isLongOrIllegal() {
        var ctx = GuuLang.getCurrentContext();
        var globalScope = ctx.getGlobalScope();
        final FrameSlotKind kind = globalScope.getFrameDescriptor().getFrameSlotKind(getSlot());
        return kind == FrameSlotKind.Long || kind == FrameSlotKind.Illegal;
    }
}
