package com.h3xstream.rhinauditor.cli.output;

import com.h3xstream.rhinauditor.engine.api.BugInstance;

public abstract class ReportOutput {
    public abstract void newBugInstance(BugInstance bugInstance);

    public void beforeListing() {

    }

    public void afterListing() {

    }

    public void beforeFile(String fileName) {

    }

    public void afterFile() {

    }

}
