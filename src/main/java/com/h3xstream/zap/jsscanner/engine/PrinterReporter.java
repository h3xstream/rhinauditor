package com.h3xstream.zap.jsscanner.engine;

import com.h3xstream.zap.jsscanner.engine.api.BugInstance;
import com.h3xstream.zap.jsscanner.engine.api.Reporter;

public class PrinterReporter implements Reporter {

    public void report(BugInstance bugInstance) {

        System.out.printf("%s:%d : %s\n", //
                bugInstance.getFile(), //
                bugInstance.getLine(), //
                bugInstance.getAbbrev());

    }
}
