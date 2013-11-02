package com.h3xstream.rhinauditor.engine.impl.injection;

import com.h3xstream.rhinauditor.engine.api.BaseDetector;
import com.h3xstream.rhinauditor.engine.api.FunctionCallDetector;
import org.mozilla.javascript.ast.AstNode;
import org.mozilla.javascript.ast.FunctionCall;

import java.util.List;

public class SetTimeOutDetector extends BaseDetector implements FunctionCallDetector {

    private static final String SET_TIMEOUT_ABBR = "SET_TIMEOUT";

    @Override
    public String getId() {
        return SET_TIMEOUT_ABBR;
    }

    @Override
    public void visitFunctionCall(FunctionCall functionCall) {
        String source = functionCall.getTarget().toSource();

        List<AstNode> args = functionCall.getArguments();

        if("setTimeout".equals(source) && args.size() >1){
            AstNode firstParam = args.get(0);
            if(!isConstantString(firstParam) && !isFunction(firstParam)) {
                bugReporter.report(buildBugInstance(functionCall, SET_TIMEOUT_ABBR));
            }
        }
    }
}
