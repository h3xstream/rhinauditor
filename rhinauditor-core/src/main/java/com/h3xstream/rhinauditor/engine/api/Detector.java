package com.h3xstream.rhinauditor.engine.api;

/**
 * Marker interface
 */
public interface Detector {

    String getId();

    void setBugReporter(BugReporter bugReporter);
}
