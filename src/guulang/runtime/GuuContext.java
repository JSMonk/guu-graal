package guulang.runtime;

import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.frame.FrameDescriptor;
import guulang.GuuLang;

public final class GuuContext {
    private final GuuLang language;
    private final GuuProcedureRegistry procedureRegistry;
    private final VirtualFrame globalVariablesFrame = Truffle.getRuntime()
            .createVirtualFrame(new Object[0], new FrameDescriptor());

    public GuuContext(GuuLang language, GuuProcedureRegistry procedureRegistry) {
        this.language = language;
        this. procedureRegistry = procedureRegistry;
    }

    public GuuProcedureRegistry getProcedureRegistry() {
        return procedureRegistry;
    }

    public VirtualFrame getGlobalScope() {
        return globalVariablesFrame;
    }

    public static GuuContext getCurrentContext() {
        return GuuLang.getCurrentContext();
    }

}
