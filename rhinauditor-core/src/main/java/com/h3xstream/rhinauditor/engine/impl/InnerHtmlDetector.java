package com.h3xstream.rhinauditor.engine.impl;

import com.h3xstream.rhinauditor.engine.api.AssignmentDetector;
import com.h3xstream.rhinauditor.engine.api.BaseDetector;
import org.mozilla.javascript.ast.Assignment;
import org.mozilla.javascript.ast.AstNode;

/**
 * innerHTML assignment could be the source of a DOM XSS
 */
public class InnerHtmlDetector extends BaseDetector implements AssignmentDetector {

    private static final String INNERHTML_ABBR = "INNERHTML";


    @Override
    public void visitAssignment(Assignment assignment) {

        AstNode leftNode = assignment.getLeft();
        String leftIdentifier = leftNode.toSource();

        if(leftIdentifier != null && leftIdentifier.endsWith(".innerHTML") && !isConstantString(assignment.getRight())) {
            reporter.report(buildBugInstance(assignment,INNERHTML_ABBR));
        }
    }
}
