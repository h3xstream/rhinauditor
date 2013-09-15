package com.h3xstream.rhinauditor.engine.api;

import org.mozilla.javascript.ast.FunctionCall;

public interface FunctionCallDetector extends Detector {
    void visitFunctionCall(FunctionCall functionCall);
}
