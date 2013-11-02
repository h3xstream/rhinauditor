package com.h3xstream.rhinauditor.engine.impl.dom;

import com.h3xstream.rhinauditor.engine.api.BaseDetector;
import com.h3xstream.rhinauditor.engine.api.FunctionCallDetector;
import org.mozilla.javascript.ast.AstNode;
import org.mozilla.javascript.ast.FunctionCall;

import java.util.List;

public class DocumentWriteDetector extends BaseDetector implements FunctionCallDetector {

    private static final String DOCUMENT_WRITE_ABBR = "DOC_WRITE";

    @Override
    public String getId() {
        return DOCUMENT_WRITE_ABBR;
    }

    @Override
    public void visitFunctionCall(FunctionCall functionCall) {
        String source = functionCall.getTarget().toSource();

        List<AstNode> args = functionCall.getArguments();

        if(source.endsWith(".write") && args.size() == 1 && !isConstantString(args.get(0))) {
            bugReporter.report(buildBugInstance(functionCall,DOCUMENT_WRITE_ABBR));
        }
    }

}
