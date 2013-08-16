package com.h3xstream.zap.jsscanner.engine.impl;

import com.h3xstream.zap.jsscanner.engine.api.BaseDetector;
import com.h3xstream.zap.jsscanner.engine.api.FunctionCallDetector;
import com.h3xstream.zap.jsscanner.engine.api.Reporter;
import org.mozilla.javascript.ast.FunctionCall;

public class AsyncScriptLoadDetector extends BaseDetector implements FunctionCallDetector {

    @Override
    public void visitFunctionCall(FunctionCall functionCall) {

    }

    @Override
    public void setReporter(Reporter reporter) {

    }
}
