package com.h3xstream.rhinauditor.engine.impl.redirect;

import com.h3xstream.rhinauditor.engine.api.AssignmentDetector;
import com.h3xstream.rhinauditor.engine.api.BaseDetector;
import com.h3xstream.rhinauditor.engine.api.FunctionCallDetector;
import org.mozilla.javascript.ast.Assignment;
import org.mozilla.javascript.ast.AstNode;
import org.mozilla.javascript.ast.FunctionCall;

/**
 * Cover :
 * - window.location = ...
 * - window.location.href =
 * - window.location.search =
 * - window.location.hash =
 * - document.location.** =
 * - top.location
 * - parent.location
 */
public class WindowLocationDetector extends BaseDetector implements AssignmentDetector {

    private static final String WINDOW_LOCATION_ABBR = "WINDOW_LOCATION";

    @Override
    public String getId() {
        return WINDOW_LOCATION_ABBR;
    }

    @Override
    public void visitAssignment(Assignment assignment) {
        AstNode leftNode = assignment.getLeft();
        String leftIdentifier = leftNode.toSource();

        if(leftIdentifier != null && //
                (leftIdentifier.endsWith(".location") || // window.location = "..." + ..;
                leftIdentifier.endsWith(".href") || leftIdentifier.endsWith(".search") || leftIdentifier.endsWith(".hash")) // window.location.[prop] = "..." + ..;
                && !isConstantString(assignment.getRight())) {
            bugReporter.report(buildBugInstance(assignment,WINDOW_LOCATION_ABBR));
        }
    }
}
