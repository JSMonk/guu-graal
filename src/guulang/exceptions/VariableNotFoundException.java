package guulang.exceptions;

import com.oracle.truffle.api.nodes.Node;

public final class VariableNotFoundException extends GuuException {
    public VariableNotFoundException(String identifierName, Node location) {
        super(identifierName + " is not defined", location);
    }
}
