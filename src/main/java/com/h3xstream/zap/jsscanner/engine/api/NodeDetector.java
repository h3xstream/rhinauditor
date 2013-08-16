package com.h3xstream.zap.jsscanner.engine.api;

import org.mozilla.javascript.ast.AstNode;

public interface NodeDetector extends Detector {
    void visitNode(AstNode node);
}
