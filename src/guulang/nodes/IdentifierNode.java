package guulang.nodes;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.NodeField;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.FrameUtil;
import com.oracle.truffle.api.frame.FrameSlot;
import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;
import guulang.GuuLang;

@NodeInfo(description = "Represents identifier in Guu lang.")
@NodeField(name = "identifier", type = String.class)
public abstract class IdentifierNode extends GuuExpression {
    public abstract String getIdentifier();

    public FrameSlot getSlotForRead() {
       var ctx = GuuLang.getCurrentContext();
       var globalScope = ctx.getGlobalScope();
       return globalScope.getFrameDescriptor().findFrameSlot(getIdentifier());
    }

    public FrameSlot getSlotForWrite(FrameDescriptor descriptor) {
        var ctx = GuuLang.getCurrentContext();
        var globalScope = ctx.getGlobalScope();
        return globalScope.getFrameDescriptor().findOrAddFrameSlot(getIdentifier());
    }

    @Specialization(guards = "isLong()")
    protected long readLong(VirtualFrame frame) {
        var ctx = GuuLang.getCurrentContext();
        var globalScope = ctx.getGlobalScope();
        return FrameUtil.getLongSafe(globalScope, getSlotForRead());
    }

    @Specialization(replaces = {"readLong"})
    protected Object readObject(VirtualFrame frame) {
        var ctx = GuuLang.getCurrentContext();
        var globalScope = ctx.getGlobalScope();
        var slot = getSlotForRead();
        if (!globalScope.isObject(slot)) {
            CompilerDirectives.transferToInterpreter();
            Object result = globalScope.getValue(slot);
            globalScope.setObject(slot, result);
            return result;
        }
        return FrameUtil.getObjectSafe(globalScope, slot);
    }

    protected boolean isLong() {
        var ctx = GuuLang.getCurrentContext();
        var globalScope = ctx.getGlobalScope();
        return globalScope.isLong(getSlotForRead());
    }
}
