package com.h3xstream.rhinauditor.engine;

import com.h3xstream.rhinauditor.engine.api.BugInstance;
import com.h3xstream.rhinauditor.engine.api.BugReporter;

import java.util.ArrayList;
import java.util.List;

public class CollectorBugReporter implements BugReporter {

    private List<BugInstance> bugs = new ArrayList<BugInstance>();

    @Override
    public void report(BugInstance bugInstance) {
        bugs.add(bugInstance);
    }

    public List<BugInstance> getBugs() {
        return bugs;
    }

}
