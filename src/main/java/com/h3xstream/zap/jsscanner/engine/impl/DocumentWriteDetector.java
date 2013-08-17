package com.h3xstream.zap.jsscanner.engine.impl;

import com.h3xstream.zap.jsscanner.engine.api.BaseDetector;
import com.h3xstream.zap.jsscanner.engine.api.FunctionCallDetector;
import com.h3xstream.zap.jsscanner.engine.api.Reporter;
import org.mozilla.javascript.ast.AstNode;
import org.mozilla.javascript.ast.FunctionCall;

import java.util.List;

public class DocumentWriteDetector extends BaseDetector implements FunctionCallDetector {

    private static final String DOCUMENT_WRITE_ABBR = "DOCWRITE";

    @Override
    public void visitFunctionCall(FunctionCall functionCall) {
        String source = functionCall.getTarget().toSource();

        List<AstNode> args = functionCall.getArguments();

        if(source.endsWith(".write") && args.size() == 1 && !isConstantString(args.get(0))) {
            reporter.report(buildBugInstance(functionCall,DOCUMENT_WRITE_ABBR));
        }
    }

}
