package com.h3xstream.zap.jsscanner.engine.impl;

import com.h3xstream.zap.jsscanner.engine.api.BaseDetector;
import com.h3xstream.zap.jsscanner.engine.api.FunctionCallDetector;
import com.h3xstream.zap.jsscanner.engine.api.Reporter;
import org.mozilla.javascript.ast.FunctionCall;

public class DocumentWriteDetector extends BaseDetector implements FunctionCallDetector {

    private static final String DOCUMENT_WRITE_ABBR = "DOCWRITE";

    @Override
    public void visitFunctionCall(FunctionCall functionCall) {
        String source = functionCall.getTarget().toSource();

        if(source.endsWith(".write")) {
            reporter.report(buildBugInstance(functionCall,DOCUMENT_WRITE_ABBR));
        }
    }

}
