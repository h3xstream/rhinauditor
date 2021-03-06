package com.h3xstream.rhinauditor.engine;

import com.h3xstream.rhinauditor.engine.api.*;
import org.mozilla.javascript.*;
import org.mozilla.javascript.ast.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;


public class JavaScriptScanner {

    private List<Detector> nodeDetectors = new ArrayList<Detector>();
    private List<Detector> functionCallDetectors = new ArrayList<Detector>();
    private List<Detector> assignmentDetectors = new ArrayList<Detector>();

    private List<Detector> allDetectors = new ArrayList<Detector>();

    private BugReporter bugReporter = new PrinterBugReporter();

    public void addDetector(Detector detector) {
        allDetectors.add(detector);

        //A detector can watch different kind of nodes

        if(detector instanceof AssignmentDetector) {
            assignmentDetectors.add(detector);
        }
        if(detector instanceof FunctionCallDetector) {
            functionCallDetectors.add(detector);
        }
        if(detector instanceof NodeDetector) {
            nodeDetectors.add(detector);
        }
    }

    public void setBugReporter(BugReporter bugReporter) {
        this.bugReporter = bugReporter;
    }

    public void scan(String script,String filename) {
        InputStream in = new ByteArrayInputStream(script.getBytes());
        try {
            scan(in,filename);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void scan(InputStream inputStream,String filename) throws IOException {

        for(Detector d : allDetectors) {
            d.setBugReporter(bugReporter);
        }


        //Configuration to include comments in the parsing
        CompilerEnvirons env = new CompilerEnvirons();

        env.setRecordingLocalJsDocComments(true);
        env.setAllowSharpComments(true);
        env.setRecordingComments(true);

        InputStreamReader reader = new InputStreamReader(inputStream);
        try {

            AstNode node = new Parser(env).parse(reader, filename, 1);
            node.visit(new DelegationNodeVisitor());
        }
        finally {
            if(reader != null)
                reader.close();
        }

    }

    class DelegationNodeVisitor implements NodeVisitor {

        public boolean visit(AstNode node) {
            try {
                visitNode(node);
            } catch (Exception e) {
                //FIXME: Log or stop ?
            }

            return true;
        }

        public void visitNode(AstNode node) throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {

            //System.out.println(node.getClass().toString());
            //System.out.println(node.toSource());


            for(Detector d : JavaScriptScanner.this.nodeDetectors ) {
                NodeDetector detector = (NodeDetector) d;
                detector.visitNode(node);
            }

            if (node instanceof FunctionCall) {
                //All function calls intercept ( [something]() )
                FunctionCall call = (FunctionCall) node;

                for(Detector d : JavaScriptScanner.this.functionCallDetectors ) {
                    FunctionCallDetector detector = (FunctionCallDetector) d;
                    detector.visitFunctionCall(call);
                }
            }
            else if(node instanceof Assignment) {
                //All assignment ( [left] = [right]  )
                Assignment assignment = (Assignment) node;


                for(Detector d : JavaScriptScanner.this.assignmentDetectors ) {
                    AssignmentDetector detector = (AssignmentDetector) d;
                    detector.visitAssignment(assignment);
                }
            }

        }
    }
}
