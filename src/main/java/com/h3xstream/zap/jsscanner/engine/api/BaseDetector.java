package com.h3xstream.zap.jsscanner.engine.api;

import org.mozilla.javascript.ast.Assignment;
import org.mozilla.javascript.ast.AstNode;

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

    protected String getLeftIdentifier(Assignment assignment) {
        AstNode leftNode = assignment.getLeft();
        return leftNode.toSource();
        /*if(leftNode instanceof Name) {
            Name leftNodeName = (Name) leftNode;
            return leftNodeName.getIdentifier();
        }*/

    }
}
