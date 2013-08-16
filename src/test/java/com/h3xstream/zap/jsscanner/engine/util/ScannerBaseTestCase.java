package com.h3xstream.zap.jsscanner.engine.util;

import com.h3xstream.zap.jsscanner.engine.JavaScriptScanner;
import com.h3xstream.zap.jsscanner.engine.api.BugInstance;
import com.h3xstream.zap.jsscanner.engine.api.Reporter;
import com.h3xstream.zap.jsscanner.engine.impl.DocumentWriteDetector;
import com.h3xstream.zap.jsscanner.engine.impl.InnerHtmlDetector;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public abstract class ScannerBaseTestCase {

    private static final Logger log = Logger.getLogger(ScannerBaseTestCase.class);

    public void scanScript(String path,Reporter reporter) throws IOException {
        InputStream in = getClass().getResourceAsStream(path);

        JavaScriptScanner scanner = new JavaScriptScanner();
        scanner.addDetector(new InnerHtmlDetector());
        scanner.addDetector(new DocumentWriteDetector());

        scanner.setReporter(reporter);

        scanner.scan(in, "mega_secureboot.js");
    }

}
