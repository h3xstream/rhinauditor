package com.h3xstream.rhinauditor.engine.api;

import org.mozilla.javascript.ast.*;

public abstract class BaseDetector implements Detector {

    protected BugReporter bugReporter;

    public void setBugReporter(BugReporter bugReporter) {
        this.bugReporter = bugReporter;
    }

    protected BugInstance buildBugInstance(AstNode node, String abbrev) {
        return new BugInstance(
                node.getAstRoot().getTop().getSourceName(),
                node.getLineno(),
                abbrev,
                node.toSource()
                );
    }

    protected boolean isConstantString(AstNode node) {

        //System.out.println("class="+node.getClass());
        //System.out.println(node.toSource());

        if(node instanceof StringLiteral) {
            return true;
        }

        else if(node instanceof InfixExpression) {
            InfixExpression infix = (InfixExpression) node;
            return isConstantString(infix.getLeft()) && isConstantString(infix.getRight());
        }

        else if(node instanceof ParenthesizedExpression) {
            ParenthesizedExpression par = (ParenthesizedExpression) node;
            return isConstantString(par.getExpression());
        }

        return false;
    }

    protected boolean isFunction(AstNode firstParam) {
        return firstParam instanceof FunctionNode;
    }
}
