package guulang.nodes;

import com.oracle.truffle.api.frame.VirtualFrame;

public class StringLiteralNode extends GuuExpression {
    private final String value;

    protected StringLiteralNode(String value) {
        this.value = value;
    }

    @Override
    public String executeString(VirtualFrame frame) {
        return value;
    }

    @Override
    public Object execute(VirtualFrame frame) {
        return executeString(frame);
    }

    public static StringLiteralNode create(String value) {
        return new StringLiteralNode(value);
    }
}
