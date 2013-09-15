package com.h3xstream.rhinauditor.engine;

import com.h3xstream.rhinauditor.engine.util.ScannerBaseTestCase;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.mockito.Mockito.*;

public class EvalDetectorTest extends ScannerBaseTestCase {

    @Test
    public void evalPositiveSamples() throws IOException {
        PrinterReporter reporter = spy(new PrinterReporter());

        scanScript("/scripts/test/eval.js", reporter);

        verify(reporter).report(bug("EVAL", 8));
        verify(reporter,times(1)).report(bug("EVAL"));
    }

    @Test
    public void evalFalsePositiveSamples() throws IOException {
        PrinterReporter reporter = spy(new PrinterReporter());

        scanScript("/scripts/test/eval_false_positive.js", reporter);

        verify(reporter,never()).report(bug("SETTIMEOUT"));
        verify(reporter,never()).report(bug("EVAL"));
    }


    @Test
    public void setTimeOutSample() throws IOException {
        PrinterReporter reporter = spy(new PrinterReporter());

        scanScript("/scripts/test/setTimeout.js", reporter);

        verify(reporter).report(bug("SETTIMEOUT", 9));
        verify(reporter).report(bug("SETTIMEOUT", 18));
        verify(reporter,times(2)).report(bug("SETTIMEOUT"));
    }

    @Test
    public void setTimeOutSampleFalsePositive() throws IOException {
        PrinterReporter reporter = spy(new PrinterReporter());

        scanScript("/scripts/test/setTimeout_false_positive.js", reporter);

        verify(reporter,never()).report(bug("SETTIMEOUT"));
        verify(reporter,never()).report(bug("EVAL"));
    }
}
