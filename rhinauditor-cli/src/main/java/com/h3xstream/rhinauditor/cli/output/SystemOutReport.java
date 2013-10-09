package com.h3xstream.rhinauditor.cli.output;

import com.h3xstream.rhinauditor.engine.api.BugInstance;
import com.h3xstream.rhinauditor.engine.api.BugReporter;
import org.fusesource.jansi.AnsiConsole;

public class SystemOutReport extends ReportOutput {

    private int nbBugs = 0;

    @Override
    public void newBugInstance(BugInstance bugInstance) {
        print(" {");
        print("   Type:\t"+bugInstance.getAbbrev());
        print("   Location:\t"+bugInstance.getFile()+":"+bugInstance.getLine());
        print("   Description: No description");
        print(" }");
        nbBugs++;
    }

    @Override
    public void beforeFile(String fileName) {
        print(" - " + fileName);
    }

    @Override
    public void afterListing() {
        print("");
        print("Scan completed. "+nbBugs+" potential bugs were found.");
        print("");
    }

    private void print(String line) {
        AnsiConsole.out.println(line);
        //System.out.println(line);
    }
}
