package com.h3xstream.rhinauditor.engine;

import com.h3xstream.rhinauditor.engine.api.BugInstance;
import com.h3xstream.rhinauditor.engine.api.BugReporter;

public class PrinterBugReporter implements BugReporter {



    public void report(BugInstance bugInstance) {

        System.out.printf("%s:%d : %s\n", //
                bugInstance.getFile(), //
                bugInstance.getLine(), //
                bugInstance.getAbbrev());

    }

}
