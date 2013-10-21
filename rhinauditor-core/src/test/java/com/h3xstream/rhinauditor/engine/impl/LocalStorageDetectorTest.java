package com.h3xstream.rhinauditor.engine.impl;

import com.h3xstream.rhinauditor.engine.PrinterBugReporter;
import com.h3xstream.rhinauditor.engine.util.ScannerBaseTestCase;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class LocalStorageDetectorTest extends ScannerBaseTestCase {

    @Test
    public void scanPositiveSamples() throws IOException {
        PrinterBugReporter reporter = spy(new PrinterBugReporter());

        scanScript("/scripts/test/local_storage.js", reporter);


        verify(reporter).report(bug("LOCAL_STORAGE", 2));
        verify(reporter).report(bug("LOCAL_STORAGE", 4));

        verify(reporter,times(2)).report(bug("LOCAL_STORAGE"));
    }

}
