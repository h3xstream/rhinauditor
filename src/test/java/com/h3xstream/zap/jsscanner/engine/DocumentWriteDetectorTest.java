package com.h3xstream.zap.jsscanner.engine;

import com.h3xstream.zap.jsscanner.engine.util.ScannerBaseTestCase;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.mockito.Mockito.spy;

public class DocumentWriteDetectorTest extends ScannerBaseTestCase {

    @Test
    public void scanPositiveSamples() throws IOException {
        PrinterReporter reporter = spy(new PrinterReporter());

        scanScript("/scripts/test/document_write.js", reporter);
    }

//    @Test
//    public void scanFalsePositiveSamples() throws IOException {
//        PrinterReporter reporter = spy(new PrinterReporter());
//
//        scanScript("/scripts/test/document_write_false_positive.js", reporter);
//
//    }
}
