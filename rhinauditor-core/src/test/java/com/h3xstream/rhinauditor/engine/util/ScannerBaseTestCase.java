package com.h3xstream.rhinauditor.engine.util;

import com.h3xstream.rhinauditor.engine.JavaScriptScanner;
import com.h3xstream.rhinauditor.engine.api.BugInstance;
import com.h3xstream.rhinauditor.engine.api.Reporter;
import com.h3xstream.rhinauditor.engine.impl.DocumentWriteDetector;
import com.h3xstream.rhinauditor.engine.impl.EvalDetector;
import com.h3xstream.rhinauditor.engine.impl.InnerHtmlDetector;
import org.apache.commons.io.FilenameUtils;
import org.mockito.Matchers;

import java.io.IOException;
import java.io.InputStream;

public abstract class ScannerBaseTestCase {

    public void scanScript(String path,Reporter reporter) throws IOException {
        InputStream in = getClass().getResourceAsStream(path);

        JavaScriptScanner scanner = new JavaScriptScanner();
        scanner.addDetector(new InnerHtmlDetector());
        scanner.addDetector(new DocumentWriteDetector());
        scanner.addDetector(new EvalDetector());

        scanner.setReporter(reporter);

        scanner.scan(in, FilenameUtils.getName(path));
    }

    public BugInstance bug(String bugAbbr,int lineNumber) {
        return Matchers.argThat(new BugMatcher(bugAbbr,lineNumber));
    }

    public BugInstance bug(String bugAbbr) {
        return Matchers.argThat(new BugMatcher(bugAbbr,null));
    }
}
