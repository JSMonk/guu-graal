package guulang.runtime;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.RootCallTarget;
import com.oracle.truffle.api.nodes.Node;
import guulang.exceptions.ProcedureNotFoundException;

import java.util.Map;
import java.util.HashMap;

public final class GuuProcedureRegistry {
    private final Map<String, RootCallTarget> registry = new HashMap<>();

    public void register(String identifier, RootCallTarget callTarget) {
       registry.put(identifier, callTarget);
    }

    @CompilerDirectives.TruffleBoundary
    public RootCallTarget lookup(String name, Node location) {
        if (!registry.containsKey(name)) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            throw new ProcedureNotFoundException(name, location);
        }
        return registry.get(name);
    }
}
