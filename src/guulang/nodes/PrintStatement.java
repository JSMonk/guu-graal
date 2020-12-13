package guulang.nodes;

import com.oracle.truffle.api.dsl.Fallback;
import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.NodeInfo;
import guulang.exceptions.TypeMismatchException;

@NodeInfo(description = "Node which represents 'print' statement.")
@NodeChild(value = "target", type = GuuExpression.class)
public abstract class PrintStatement extends GuuStatement {
    @Specialization
    protected void printLong(long target) {
        System.out.println(target);
    }

    @Specialization
    protected void printString(String target) {
        System.out.println(target);
    }

    @Fallback
    protected void typeError(Object target) {
        throw new TypeMismatchException("Target of print statement should be integer literal, string literal or variable", this);
    }
}
