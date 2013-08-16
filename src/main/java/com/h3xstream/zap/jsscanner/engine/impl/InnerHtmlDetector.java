package com.h3xstream.zap.jsscanner.engine.impl;

import com.h3xstream.zap.jsscanner.engine.api.AssignmentDetector;
import com.h3xstream.zap.jsscanner.engine.api.BaseDetector;
import com.h3xstream.zap.jsscanner.engine.api.Reporter;
import org.mozilla.javascript.ast.Assignment;

/**
 * innerHTML assignment could be the source of a DOM XSS
 */
public class InnerHtmlDetector extends BaseDetector implements AssignmentDetector {

    private static final String INNERHTML_ABBR = "INNERHTML";


    @Override
    public void visitAssignment(Assignment assignment) {
        String leftIdentifier = getLeftIdentifier(assignment);

        if(leftIdentifier != null && leftIdentifier.endsWith(".innerHTML")) {
            reporter.report(buildBugInstance(assignment,INNERHTML_ABBR));
        }
    }
}
