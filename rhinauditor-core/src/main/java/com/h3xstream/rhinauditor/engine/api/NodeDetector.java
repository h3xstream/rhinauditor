package com.h3xstream.rhinauditor.engine.api;

import org.mozilla.javascript.ast.AstNode;

public interface NodeDetector extends Detector {
    void visitNode(AstNode node);
}
