package com.h3xstream.zap.jsscanner.engine.api;

import org.mozilla.javascript.ast.Assignment;

public interface AssignmentDetector extends Detector {

    void visitAssignment(Assignment assignment);
}
