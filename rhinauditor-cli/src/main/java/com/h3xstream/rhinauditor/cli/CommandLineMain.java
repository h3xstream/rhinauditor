package com.h3xstream.rhinauditor.cli;

import com.h3xstream.rhinauditor.engine.CollectorReporter;
import com.h3xstream.rhinauditor.engine.JavaScriptScanner;
import com.h3xstream.rhinauditor.engine.api.BugInstance;
import org.apache.commons.cli.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class CommandLineMain {

    //private static final Detector[] DETECTORS = DetectorConstants.DEFAULT_DETECTORS_LIST;

    /**
     * @param args
     */
    public static void main(String[] args) throws ParseException {

        Options options = buildOptions();

        CommandLineParser parser = new GnuParser();
        CommandLine cmd = parser.parse( options, args);

        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp( isWindow() ? "src/main/scripts/rhinauditor.bat" : "src/main/scripts/rhinauditor.sh", options );
    }

    private static boolean isWindow() {
        String osName = System.getProperty("os.name");
        return osName != null && osName.toLowerCase().indexOf("win") != -1;
    }

    private static Options buildOptions() {
        Options options = new Options();
        //Scan options
        options.addOption("d","directory",true,"directory containing source code");
        options.addOption("r","recur",false,"recursively scan the directory specified");
        options.addOption("s","script",true,"single script to scan");
        //Report options
        options.addOption("xml",true,"export the result to a xml file");
        options.addOption("html",true,"export the result to a html file");
        options.addOption("out",false,"print the result to the console output stream");
        return options;
    }

    private static List<BugInstance> scanFile(InputStream in,String fileName) throws IOException {

        JavaScriptScanner scanner = new JavaScriptScanner();

//        for(Detector d : DETECTORS) {
//            scanner.addDetector(d);
//        }

        CollectorReporter reporter = new CollectorReporter();
        scanner.setReporter(reporter);

        scanner.scan(in,fileName);

        return reporter.getBugs();
    }
}
