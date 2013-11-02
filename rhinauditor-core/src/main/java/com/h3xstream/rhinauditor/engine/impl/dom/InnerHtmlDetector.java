package com.h3xstream.rhinauditor.engine.impl.dom;

import com.h3xstream.rhinauditor.engine.api.AssignmentDetector;
import com.h3xstream.rhinauditor.engine.api.BaseDetector;
import org.mozilla.javascript.ast.Assignment;
import org.mozilla.javascript.ast.AstNode;

/**
 * innerHTML assignment could be the source of a DOM XSS
 */
public class InnerHtmlDetector extends BaseDetector implements AssignmentDetector {

    private static final String INNER_HTML_ABBR = "INNER_HTML";

    @Override
    public String getId() {
        return INNER_HTML_ABBR;
    }

    @Override
    public void visitAssignment(Assignment assignment) {

        AstNode leftNode = assignment.getLeft();
        String leftIdentifier = leftNode.toSource();

        if(leftIdentifier != null && leftIdentifier.endsWith(".innerHTML") && !isConstantString(assignment.getRight())) {
            bugReporter.report(buildBugInstance(assignment,INNER_HTML_ABBR));
        }
    }
}
