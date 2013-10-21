package com.h3xstream.rhinauditor.engine.impl;

import com.h3xstream.rhinauditor.engine.api.BaseDetector;
import com.h3xstream.rhinauditor.engine.api.FunctionCallDetector;
import org.mozilla.javascript.ast.AstNode;
import org.mozilla.javascript.ast.FunctionCall;

import java.util.List;

public class LocalStorageDetector extends BaseDetector implements FunctionCallDetector {
    private static final String LOCAL_STORAGE_ABBR = "LOCAL_STORAGE";

    @Override
    public void visitFunctionCall(FunctionCall functionCall) {
        String source = functionCall.getTarget().toSource();

        if(source != null && source.contains("localstorage.")){ //localStorage.getItem and localStorage.setItem
             bugReporter.report(buildBugInstance(functionCall,LOCAL_STORAGE_ABBR));
        }
    }
}
