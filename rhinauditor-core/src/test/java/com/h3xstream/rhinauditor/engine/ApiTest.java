package com.h3xstream.rhinauditor.engine;

import com.h3xstream.rhinauditor.engine.api.BugInstance;
import com.h3xstream.rhinauditor.engine.impl.injection.EvalDetector;
import org.testng.annotations.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import static org.testng.Assert.assertEquals;

public class ApiTest {

    @Test
    public void testCompleteApi() throws IOException {

        //Configure the scanner
        JavaScriptScanner scanner = new JavaScriptScanner();
        scanner.addDetector(new EvalDetector());
        CollectorBugReporter collector = new CollectorBugReporter();
        scanner.setBugReporter(collector);

        //Ingest some script
        String script = "var test='someCall('+document.search+')';\n\neval(test);";
        ByteArrayInputStream scriptStream = new ByteArrayInputStream(script.getBytes());
        scanner.scan(scriptStream,"test.js");

        List<BugInstance> bugs = collector.getBugs();
        assertEquals(bugs.size(),1);

        System.out.println(bugs.get(0).toString());
    }
}
