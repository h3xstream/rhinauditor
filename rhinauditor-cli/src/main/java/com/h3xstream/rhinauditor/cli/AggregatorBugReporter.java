package com.h3xstream.rhinauditor.cli;

import com.h3xstream.rhinauditor.cli.output.ReportOutput;
import com.h3xstream.rhinauditor.engine.api.BugInstance;
import com.h3xstream.rhinauditor.engine.api.BugReporter;

import java.util.*;

public class AggregatorBugReporter implements BugReporter {

    Map<String,String> hashes = new HashMap<String,String>();

    LinkedHashMap<String,ArrayList<BugInstance>> allBugs = new LinkedHashMap<String,ArrayList<BugInstance>>();

    @Override
    public void report(BugInstance bugInstance) {
        String fileId = bugInstance.getFile();
        ArrayList<BugInstance> bugs = allBugs.get(fileId);
        if(bugs == null) {
            bugs = new ArrayList<BugInstance>();
            allBugs.put(fileId, bugs);
        }
        bugs.add(bugInstance);
    }

    public void generateReport(ReportOutput output) {
        output.beforeListing();
        Iterator<Map.Entry<String,ArrayList<BugInstance>>> it = allBugs.entrySet().iterator();
        while(it.hasNext()) {
            Map.Entry<String,ArrayList<BugInstance>> fileEntry = it.next();
            ArrayList<BugInstance> fileBugs = fileEntry.getValue();
            output.beforeFile(fileEntry.getKey());
            for(BugInstance bug : fileBugs) {
                output.newBugInstance(bug);
            }
            output.afterFile();

            fileEntry.setValue(null);
            //allBugs.remove(fileEntry.getKey());
        }
        output.afterListing();
    }
}
