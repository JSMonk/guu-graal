package guulang.runtime;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.nodes.Node;
import guulang.exceptions.ProcedureNotFoundException;

import java.util.Map;
import java.util.HashMap;

public final class GuuProcedureRegistry {
    private final Map<String, CallTarget> registry = new HashMap<>();

    public void register(String identifier, CallTarget callTarget) {
       registry.put(identifier, callTarget);
    }

    @CompilerDirectives.TruffleBoundary
    public CallTarget lookup(String name, Node location) {
        if (!registry.containsKey(name)) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            throw new ProcedureNotFoundException(name, location);
        }
        return registry.get(name);
    }
}
