package com.h3xstream.rhinauditor.engine;

import com.h3xstream.rhinauditor.engine.PrinterReporter;
import com.h3xstream.rhinauditor.engine.util.ScannerBaseTestCase;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.mockito.Mockito.*;

public class InnerHtmlDetectorTest extends ScannerBaseTestCase {

    @Test
    public void scanPositiveSamples() throws IOException {
        PrinterReporter reporter = spy(new PrinterReporter());

        scanScript("/scripts/test/dom_xss_innerhtml.js", reporter);


        verify(reporter).report(bug("INNERHTML", 4));
        verify(reporter).report(bug("INNERHTML", 10));

        verify(reporter,times(2)).report(bug("INNERHTML"));
    }

    @Test
    public void scanFalsePositiveSamples() throws IOException {
        PrinterReporter reporter = spy(new PrinterReporter());

        scanScript("/scripts/test/dom_xss_innerhtml_false_positive.js", reporter);


        verify(reporter,never()).report(bug("INNERHTML"));
    }
}
