package com.h3xstream.rhinauditor.engine.impl;

import com.h3xstream.rhinauditor.engine.api.AssignmentDetector;
import com.h3xstream.rhinauditor.engine.api.BaseDetector;
import com.h3xstream.rhinauditor.engine.api.FunctionCallDetector;
import org.mozilla.javascript.ast.Assignment;
import org.mozilla.javascript.ast.FunctionCall;

public class RedirectionDetector extends BaseDetector implements AssignmentDetector, FunctionCallDetector {

    @Override
    public void visitAssignment(Assignment assignment) {

    }

    @Override
    public void visitFunctionCall(FunctionCall functionCall) {

    }
}
