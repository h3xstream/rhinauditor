package com.h3xstream.rhinauditor.engine;

import com.h3xstream.rhinauditor.engine.api.BugInstance;
import com.h3xstream.rhinauditor.engine.api.Reporter;

import java.util.ArrayList;
import java.util.List;

public class CollectorReporter  implements Reporter {

    private List<BugInstance> bugs = new ArrayList<BugInstance>();

    public void report(BugInstance bugInstance) {
        bugs.add(bugInstance);
    }

    public List<BugInstance> getBugs() {
        return bugs;
    }

}
