package com.h3xstream.zap.jsscanner.engine;

import com.h3xstream.zap.jsscanner.engine.util.ScannerBaseTestCase;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.mockito.Mockito.spy;

public class RealWorldTest extends ScannerBaseTestCase {

    @Test
    public void scanMega() throws IOException {
        System.setProperty("file.encodin","UTF-8");

        PrinterReporter reporter = spy(new PrinterReporter());

        scanScript("/scripts/realworld/mega_secureboot.js", reporter);
    }
}
