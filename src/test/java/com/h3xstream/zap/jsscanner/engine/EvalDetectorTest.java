package com.h3xstream.zap.jsscanner.engine;

import com.h3xstream.zap.jsscanner.engine.util.ScannerBaseTestCase;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.mockito.Mockito.*;

public class EvalDetectorTest extends ScannerBaseTestCase {

    @Test
    public void scanPositiveSamples() throws IOException {
        PrinterReporter reporter = spy(new PrinterReporter());

        scanScript("/scripts/test/eval.js", reporter);

        verify(reporter).report(bug("EVAL", 8));
        verify(reporter,times(1)).report(bug("EVAL"));
    }

    @Test
    public void scanFalsePositiveSamples() throws IOException {
        PrinterReporter reporter = spy(new PrinterReporter());

        scanScript("/scripts/test/eval_false_positive.js", reporter);

        verify(reporter, never()).report(bug("EVAL"));
    }
}
