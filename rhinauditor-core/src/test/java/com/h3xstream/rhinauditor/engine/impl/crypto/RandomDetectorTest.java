package com.h3xstream.rhinauditor.engine.impl.crypto;

import com.h3xstream.rhinauditor.engine.PrinterBugReporter;
import com.h3xstream.rhinauditor.engine.util.ScannerBaseTestCase;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class RandomDetectorTest  extends ScannerBaseTestCase {

    @Test
    public void findRandomUsage() throws IOException {
        PrinterBugReporter reporter = spy(new PrinterBugReporter());

        scanScript("/scripts/test/random.js", reporter);

        verify(reporter).report(bug("RANDOM", 3));

        verify(reporter,times(1)).report(bug("RANDOM"));
    }
}
