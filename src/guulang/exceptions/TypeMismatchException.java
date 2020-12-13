package guulang.exceptions;

import com.oracle.truffle.api.nodes.Node;

public final class TypeMismatchException extends GuuException {
    public TypeMismatchException(String message, Node location) {
        super(message, location);
    }
}
