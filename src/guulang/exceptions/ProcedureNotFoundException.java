package guulang.exceptions;

import com.oracle.truffle.api.nodes.Node;

public class ProcedureNotFoundException extends GuuException {
    public ProcedureNotFoundException(String identifierName, Node location) {
        super(identifierName + " is not defined", location);
    }
}
