package com.h3xstream.zap.jsscanner.engine.api;

import org.mozilla.javascript.ast.Assignment;
import org.mozilla.javascript.ast.AstNode;
import org.mozilla.javascript.ast.StringLiteral;
import org.mozilla.javascript.ast.InfixExpression;

public abstract class BaseDetector implements Detector {

    protected Reporter reporter;

    public void setReporter(Reporter reporter) {
        this.reporter = reporter;
    }

    protected BugInstance buildBugInstance(AstNode node, String abbrev) {
        return new BugInstance(
                node.getAstRoot().getTop().getSourceName(),
                node.getLineno(),
                abbrev
                );
    }

    protected boolean isConstantString(AstNode node) {

        //System.out.println("class="+node.getClass());
        //System.out.println(node.toSource());

        if(node instanceof StringLiteral) {
            return true;
        }

        if(node instanceof InfixExpression) {
            InfixExpression infix = (InfixExpression) node;

            return isConstantString(infix.getLeft()) && isConstantString(infix.getRight());
        }

        return false;
    }
}
