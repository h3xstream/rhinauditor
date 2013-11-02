package com.h3xstream.rhinauditor.engine.impl.crypto;

import com.h3xstream.rhinauditor.engine.api.BaseDetector;
import com.h3xstream.rhinauditor.engine.api.FunctionCallDetector;
import org.mozilla.javascript.ast.Assignment;
import org.mozilla.javascript.ast.FunctionCall;

public class RandomDetector extends BaseDetector implements FunctionCallDetector {
    private static final String RANDOM_ABBR = "RANDOM";

    @Override
    public String getId() {
        return RANDOM_ABBR;
    }

    @Override
    public void visitFunctionCall(FunctionCall functionCall) {
        String source = functionCall.getTarget().toSource();

        if(source != null && source.endsWith(".random")){ //Math.random and possibly alternative api
            bugReporter.report(buildBugInstance(functionCall,RANDOM_ABBR));
        }
    }
}
