package guulang.nodes;

import com.oracle.truffle.api.frame.VirtualFrame;

public final class IntLiteralNode extends GuuExpression {
    public final long value;

    protected IntLiteralNode(long value) {
        this.value = value;
    }

    @Override
    public long executeLong(VirtualFrame frame) {
        return value;
    }

    @Override
    public Object execute(VirtualFrame frame) {
        return value;
    }

    public static IntLiteralNode create(long value) {
        return new IntLiteralNode(value);
    }
}