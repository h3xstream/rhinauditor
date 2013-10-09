package com.h3xstream.rhinauditor.cli;

import com.h3xstream.rhinauditor.cli.output.HtmlReport;
import com.h3xstream.rhinauditor.cli.output.ReportOutput;
import com.h3xstream.rhinauditor.cli.output.SystemOutReport;
import com.h3xstream.rhinauditor.cli.output.XmlReport;
import com.h3xstream.rhinauditor.engine.DetectorConstants;
import com.h3xstream.rhinauditor.engine.JavaScriptScanner;
import com.h3xstream.rhinauditor.engine.api.Detector;
import com.h3xstream.rhinauditor.engine.api.BugReporter;
import org.apache.commons.cli.*;
import org.fusesource.jansi.AnsiConsole;
import org.mozilla.javascript.EvaluatorException;

import java.io.*;

public class CommandLineMain {

    //private static final Detector[] DETECTORS = DetectorConstants.DEFAULT_DETECTORS_LIST;

    /**
     * @param args
     */
    public static void main(String[] args) throws ParseException, IOException {
        try {
            Options options = buildOptions();

            CommandLineParser parser = new GnuParser();
            CommandLine cmd = parser.parse(options, args);

            if (cmd.hasOption("help")) {
                printHelp(options);
                return;
            }

            //Input
            String directory = cmd.getOptionValue('d', "");
            String script = cmd.getOptionValue('s', "");
            boolean recursive = cmd.hasOption('r');

            //Output
            ReportOutput output = null;
            if (cmd.hasOption("xml")) {
                String xmlPath = cmd.getOptionValue("xml");

                File f = createFile(xmlPath, "xml file");
                if (f != null)
                    output = new XmlReport(new FileOutputStream(f));
                else
                    return;
            } else if (cmd.hasOption("html")) {
                String htmlPath = cmd.getOptionValue("html");

                File f = createFile(htmlPath, "html file");
                if (f != null)
                    output = new HtmlReport(new FileOutputStream(f));
                else
                    return;
            } else {
                output = new SystemOutReport();
            }

            //reporter variable is valid (!= null) at this point

            AggregatorBugReporter reporter = new AggregatorBugReporter();

            if (!"".equals(script)) {
                File f = new File(script);
                if (f.exists()) {
                    printHeader();


                    scanFile(new FileInputStream(f), f.getName(), reporter);

                } else {
                    printErr(String.format("Invalid script %s (%s)", script, f.getCanonicalPath()));
                }
            } else if (!"".equals(directory)) {
                File dir = new File(directory);
                if (dir.exists()) {
                    printHeader();


                    recursiveScanDir(dir, reporter, recursive);

                } else {
                    printErr(String.format("Invalid directory %s (%s)", directory, dir.getCanonicalPath()));
                }
            } else {
                print("A directory (-d) or a single script (-s) must be specify.");
                printHelp(options);
            }

            //

            reporter.generateReport(output);

        } catch (MissingArgumentException e) {
            printErr(e.getMessage());
        }
    }

    private static File createFile(String filePath, String type) {

        try {
            File newFile = new File(filePath);
            if (!newFile.createNewFile() || newFile.exists()) {
                printErr(String.format("Unable to create the %s %s (%s)", type, filePath, newFile.getCanonicalPath()));
            }
            return newFile;
        } catch (IOException e) {
            printErr(e.getMessage());
            return null;
        }
    }


    private static boolean isWindow() {
        String osName = System.getProperty("os.name");
        return osName != null && osName.toLowerCase().indexOf("win") != -1;
    }

    private static Options buildOptions() {
        Options options = new Options();
        //Scan options
        options.addOption("d", "directory", true, "directory containing source code");
        options.addOption("r", "recur", false, "recursively scan the directory specified");
        options.addOption("s", "script", true, "single script to scan");
        //Report options
        options.addOption("xml", true, "export the result to a xml file");
        options.addOption("html", true, "export the result to a html file");
        options.addOption("out", false, "print the result to the console output stream (default report option)");
        return options;
    }


    private static void scanFile(InputStream in, String fileName, BugReporter bugReporter) throws IOException {

        JavaScriptScanner scanner = new JavaScriptScanner();

        for (Detector d : DetectorConstants.DEFAULT_DETECTORS_LIST) {
            scanner.addDetector(d);
        }

        scanner.setBugReporter(bugReporter);
        try {
            scanner.scan(in, fileName);
        } catch (EvaluatorException e) {
            printErr("[WARN] " + e.getMessage());
        }
    }

    private static void recursiveScanDir(File directory, BugReporter bugReporter, final boolean recursive) throws IOException {
        File[] files = directory.listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                return (recursive && file.isDirectory()) || file.getName().endsWith(".js");
            }
        });

        for (File f : files) {
            if (f.isDirectory()) {
                recursiveScanDir(f, bugReporter, recursive);
            } else if (f.isFile()) {
                scanFile(new FileInputStream(f), f.getCanonicalPath(), bugReporter);
            }
        }
    }


    private static void printHelp(Options options) {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp(isWindow() ? "rhinauditor.bat" : "rhinauditor.sh", options);
    }

    private static void print(String line) {
        AnsiConsole.out.println(line);
        //System.out.println(line);
    }

    private static void printErr(String line) {
        print(line);
    }

    private static void printHeader() {
        print("Rhinauditor version " + DetectorConstants.VERSION);
        print("");
    }

}
