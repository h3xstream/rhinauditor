package com.h3xstream.zap.jsscanner.engine.api;

import org.mozilla.javascript.ast.FunctionCall;

public interface FunctionCallDetector extends Detector {
    void visitFunctionCall(FunctionCall functionCall);
}
