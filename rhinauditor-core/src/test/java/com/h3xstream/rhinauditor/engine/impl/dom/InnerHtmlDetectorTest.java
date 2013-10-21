package com.h3xstream.rhinauditor.engine.impl.dom;

import com.h3xstream.rhinauditor.engine.PrinterBugReporter;
import com.h3xstream.rhinauditor.engine.util.ScannerBaseTestCase;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.mockito.Mockito.*;

public class InnerHtmlDetectorTest extends ScannerBaseTestCase {

    @Test
    public void scanPositiveSamples() throws IOException {
        PrinterBugReporter reporter = spy(new PrinterBugReporter());

        scanScript("/scripts/test/dom_xss_innerhtml.js", reporter);


        verify(reporter).report(bug("INNER_HTML", 4));
        verify(reporter).report(bug("INNER_HTML", 10));

        verify(reporter,times(2)).report(bug("INNER_HTML"));
    }

    @Test
    public void scanFalsePositiveSamples() throws IOException {
        PrinterBugReporter reporter = spy(new PrinterBugReporter());

        scanScript("/scripts/test/dom_xss_innerhtml_false_positive.js", reporter);


        verify(reporter,never()).report(bug("INNERHTML"));
    }
}
