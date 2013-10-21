package com.h3xstream.rhinauditor.engine.util;

import com.h3xstream.rhinauditor.engine.DetectorConstants;
import com.h3xstream.rhinauditor.engine.JavaScriptScanner;
import com.h3xstream.rhinauditor.engine.api.BugInstance;
import com.h3xstream.rhinauditor.engine.api.BugReporter;
import com.h3xstream.rhinauditor.engine.api.Detector;
import org.apache.commons.io.FilenameUtils;
import org.mockito.Matchers;

import java.io.IOException;
import java.io.InputStream;

public abstract class ScannerBaseTestCase {

    public void scanScript(String path,BugReporter bugReporter) throws IOException {
        InputStream in = getClass().getResourceAsStream(path);

        JavaScriptScanner scanner = new JavaScriptScanner();

        for(Detector d : DetectorConstants.DEFAULT_DETECTORS_LIST) {
            scanner.addDetector(d);
        }
        scanner.setBugReporter(bugReporter);

        scanner.scan(in, FilenameUtils.getName(path));
    }

    public BugInstance bug(String bugAbbr,int lineNumber) {
        return Matchers.argThat(new BugMatcher(bugAbbr,lineNumber));
    }

    public BugInstance bug(String bugAbbr) {
        return Matchers.argThat(new BugMatcher(bugAbbr,null));
    }
}
