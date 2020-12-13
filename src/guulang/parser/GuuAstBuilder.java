package guulang.parser;

import com.oracle.truffle.api.Truffle;
import guulang.GuuLang;
import guulang.nodes.*;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;
import java.util.function.Function;

public class GuuAstBuilder {
    private final GuuLang language;
    private final Deque<Object> stack = new ArrayDeque<>();
    private final Function<Integer, Token> token;

    public GuuAstBuilder(GuuLang language, Function<Integer, Token> token) {
       this.language = language;
       this.token = token;
    }

    public ProgramEntry build() {
        return ProgramEntry.create(language);
    }

    public void endProcedureDeclaration() {
        var statements = new LinkedList<GuuStatement>();
        var currentNode = stack.pop();
        while (!(currentNode instanceof IdentifierNode)) {
            statements.addFirst((GuuStatement)currentNode);
            currentNode = stack.pop();
        }
        var identifier = (IdentifierNode) currentNode;
        var blockNode = GuuBlockNode.create(statements.toArray(new GuuStatement[0]));
        var currentExecutionContext = GuuLang.getCurrentContext();
        var globalScope = currentExecutionContext.getGlobalScope();
        var procedureRegistry = currentExecutionContext.getProcedureRegistry();
        var rootNode = GuuRootNode.create(language, globalScope.getFrameDescriptor(), blockNode);
        procedureRegistry.register(identifier.getIdentifier(), Truffle.getRuntime().createCallTarget(rootNode));
    }

    public void pushIdentifier() {
        var token = currentToken();
        var identifier = IdentifierNodeGen.create(token.image);
        stack.push(identifier);
    }

    public void pushAssignmentStatement() {
        var value = (GuuExpression) stack.pop();
        var identifier = (IdentifierNode) stack.pop();
        var currentContext = GuuLang.getCurrentContext();
        var globalScope = currentContext.getGlobalScope();
        var assignment = AssignmentStatementNodeGen.create(
                value,
                identifier.getSlotForWrite(globalScope.getFrameDescriptor())
        );
        stack.push(assignment);
    }

    public void pushCallStatement() {
        var identifier = (IdentifierNode) stack.pop();
        var currentExecutionContext = GuuLang.getCurrentContext();
        var procedureRegistry = currentExecutionContext.getProcedureRegistry();
        var name = ProcedureIdentifierNode.create(
                procedureRegistry,
                identifier.getIdentifier()
        );
        var callStatement = CallStatement.create(name);
        stack.push(callStatement);
    }

    public void pushPrintStatement() {
        var value = (GuuExpression) stack.pop();
        var printStatement = PrintStatementNodeGen.create(value);
        stack.push(printStatement);
    }

    public void pushIntLiteral() {
        var token = currentToken();
        var intLiteral = IntLiteralNode.create(Long.parseLong(token.image));
        stack.push(intLiteral);
    }

    public void pushStringLiteral() {
        var token = currentToken();
        var stringLiteral = StringLiteralNode.create(token.image);
        stack.push(stringLiteral);
    }

    private Token currentToken() {
        return token.apply(0);
    }
}
