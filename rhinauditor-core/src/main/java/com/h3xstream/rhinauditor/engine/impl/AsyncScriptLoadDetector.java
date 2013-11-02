package com.h3xstream.rhinauditor.engine.impl;

import com.h3xstream.rhinauditor.engine.api.BaseDetector;
import com.h3xstream.rhinauditor.engine.api.FunctionCallDetector;
import org.mozilla.javascript.ast.FunctionCall;

public class AsyncScriptLoadDetector extends BaseDetector implements FunctionCallDetector {

    @Override
    public String getId() {
        return null;
    }

    @Override
    public void visitFunctionCall(FunctionCall functionCall) {

    }

}
