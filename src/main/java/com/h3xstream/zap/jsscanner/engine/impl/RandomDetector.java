package com.h3xstream.zap.jsscanner.engine.impl;

import com.h3xstream.zap.jsscanner.engine.api.AssignmentDetector;
import com.h3xstream.zap.jsscanner.engine.api.BaseDetector;
import com.h3xstream.zap.jsscanner.engine.api.FunctionCallDetector;
import org.mozilla.javascript.ast.Assignment;
import org.mozilla.javascript.ast.FunctionCall;

public class RandomDetector extends BaseDetector implements FunctionCallDetector {

    @Override
    public void visitFunctionCall(FunctionCall functionCall) {

    }
}
