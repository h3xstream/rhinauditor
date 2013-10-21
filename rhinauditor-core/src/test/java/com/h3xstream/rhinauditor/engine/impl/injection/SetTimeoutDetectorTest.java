package com.h3xstream.rhinauditor.engine.impl.injection;

import com.h3xstream.rhinauditor.engine.PrinterBugReporter;
import com.h3xstream.rhinauditor.engine.util.ScannerBaseTestCase;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

public class SetTimeoutDetectorTest  extends ScannerBaseTestCase {

    @Test
    public void setTimeOutSample() throws IOException {
        PrinterBugReporter reporter = spy(new PrinterBugReporter());

        scanScript("/scripts/test/setTimeout.js", reporter);

        verify(reporter).report(bug("SET_TIMEOUT", 9));
        verify(reporter).report(bug("SET_TIMEOUT", 18));
        verify(reporter,times(2)).report(bug("SET_TIMEOUT"));
    }

    @Test
    public void setTimeOutSampleFalsePositive() throws IOException {
        PrinterBugReporter reporter = spy(new PrinterBugReporter());

        scanScript("/scripts/test/setTimeout_false_positive.js", reporter);

        verify(reporter,never()).report(bug("SET_TIMEOUT"));
        verify(reporter,never()).report(bug("EVAL"));
    }
}
