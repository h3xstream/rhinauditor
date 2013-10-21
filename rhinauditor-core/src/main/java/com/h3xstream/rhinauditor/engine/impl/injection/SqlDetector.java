package com.h3xstream.rhinauditor.engine.impl.injection;

import com.h3xstream.rhinauditor.engine.api.BaseDetector;
import com.h3xstream.rhinauditor.engine.api.FunctionCallDetector;
import org.mozilla.javascript.ast.FunctionCall;

public class SqlDetector extends BaseDetector implements FunctionCallDetector {
    @Override
    public void visitFunctionCall(FunctionCall functionCall) {
        String source = functionCall.getTarget().toSource();

        if(source != null && source.endsWith("executeSql")) {

        }
    }
}
