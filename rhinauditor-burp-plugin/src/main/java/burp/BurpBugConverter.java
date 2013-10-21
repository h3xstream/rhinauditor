package burp;

import com.h3xstream.rhinauditor.engine.api.BugInstance;

import java.net.URL;

public class BurpBugConverter {


    public static IScanIssue convertBugToScanIssue(BugInstance bug, IHttpRequestResponse resp,URL url) {
        IScanIssue issue = new CustomScanIssue(resp.getHttpService(),
                url,
                new IHttpRequestResponse[] {resp},
                bug.getAbbrev(),
                bug.getFile()+":"+bug.getLine(),
                "Low",
                "Tentative");
        return issue;
    }
}
