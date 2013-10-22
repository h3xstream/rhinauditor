package burp;

import com.h3xstream.rhinauditor.engine.api.BugInstance;
import com.h3xstream.rhinauditor.engine.locale.LocaleProvider;

import java.net.URL;

public class BurpBugConverter {


    public static IScanIssue convertBugToScanIssue(BugInstance bug, IHttpRequestResponse resp,URL url) {
        String title = LocaleProvider.getTitle(bug.getAbbrev());
        String description = LocaleProvider.getDescription(bug.getAbbrev());
        String[] refs = LocaleProvider.getReferencesUrl(bug.getAbbrev());

        String htmlDesc = description+"<br/><br/>"+
                "<b>"+LocaleProvider.getSourceLocation()+"</b>: "+bug.getFile()+":"+bug.getLine()+"<br/><br/>"+
                "<b>"+LocaleProvider.getVulnerableCode()+"</b>: <br/><code><pre>"+bug.getCode()+"</pre></code><br/><br/>"+
                "<b>"+LocaleProvider.getAdditionalReferences()+"</b>:<br/>";

        for(String ref : refs) {
            htmlDesc += "<li><a href=\""+ref+"\">"+ref+"</a></li><br/>";
        }

        IScanIssue issue = new CustomScanIssue(resp.getHttpService(),
                url,
                new IHttpRequestResponse[] {resp},
                title,
                htmlDesc,
                "Low",
                "Tentative");
        return issue;
    }
}
