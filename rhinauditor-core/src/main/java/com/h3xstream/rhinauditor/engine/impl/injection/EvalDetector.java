package com.h3xstream.rhinauditor.engine.impl.injection;

import com.h3xstream.rhinauditor.engine.api.BaseDetector;
import com.h3xstream.rhinauditor.engine.api.FunctionCallDetector;
import org.mozilla.javascript.ast.AstNode;
import org.mozilla.javascript.ast.FunctionCall;

import java.util.List;

public class EvalDetector extends BaseDetector implements FunctionCallDetector {

    private static final String EVAL_ABBR = "EVAL";

    @Override
    public String getId() {
        return EVAL_ABBR;
    }

    @Override
    public void visitFunctionCall(FunctionCall functionCall) {
        String source = functionCall.getTarget().toSource();

        List<AstNode> args = functionCall.getArguments();

        if("eval".equals(source) && args.size() == 1 && !isConstantString(args.get(0))) {
            bugReporter.report(buildBugInstance(functionCall,EVAL_ABBR));
        }
    }

}
