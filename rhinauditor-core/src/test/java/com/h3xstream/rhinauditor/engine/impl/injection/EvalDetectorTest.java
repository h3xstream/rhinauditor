package com.h3xstream.rhinauditor.engine.impl.injection;

import com.h3xstream.rhinauditor.engine.PrinterBugReporter;
import com.h3xstream.rhinauditor.engine.util.ScannerBaseTestCase;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.mockito.Mockito.*;

public class EvalDetectorTest extends ScannerBaseTestCase {

    @Test
    public void evalPositiveSamples() throws IOException {
        PrinterBugReporter reporter = spy(new PrinterBugReporter());

        scanScript("/scripts/test/eval.js", reporter);

        verify(reporter).report(bug("EVAL", 8));
        verify(reporter,times(1)).report(bug("EVAL"));
    }

    @Test
    public void evalFalsePositiveSamples() throws IOException {
        PrinterBugReporter reporter = spy(new PrinterBugReporter());

        scanScript("/scripts/test/eval_false_positive.js", reporter);

        verify(reporter,never()).report(bug("SET_TIMEOUT"));
        verify(reporter,never()).report(bug("EVAL"));
    }



}
