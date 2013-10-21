package com.h3xstream.rhinauditor.engine.impl.redirect;

import com.h3xstream.rhinauditor.engine.PrinterBugReporter;
import com.h3xstream.rhinauditor.engine.util.ScannerBaseTestCase;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Arrays;

import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class WindowLocationDetectorTest extends ScannerBaseTestCase {

    @Test
    public void findRedirect() throws IOException {

        PrinterBugReporter reporter = spy(new PrinterBugReporter());

        scanScript("/scripts/test/window_location.js", reporter);

        for(int line : Arrays.asList(2,4,6,7,8,13,14,15)) {
            verify(reporter).report(bug("WINDOW_LOCATION", line));
        }
        verify(reporter,times(8)).report(bug("WINDOW_LOCATION"));
    }

}
