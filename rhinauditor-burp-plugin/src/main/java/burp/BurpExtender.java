package burp;

import com.h3xstream.rhinauditor.engine.CollectorBugReporter;
import com.h3xstream.rhinauditor.engine.DetectorConstants;
import com.h3xstream.rhinauditor.engine.JavaScriptScanner;
import com.h3xstream.rhinauditor.engine.api.BugInstance;
import com.h3xstream.rhinauditor.engine.api.Detector;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class BurpExtender implements IBurpExtender, IScannerCheck {

    private IBurpExtenderCallbacks callbacks;
    private IExtensionHelpers helpers;

    private static final Detector[] DETECTORS = DetectorConstants.DEFAULT_DETECTORS_LIST;

    @Override
    public void registerExtenderCallbacks(IBurpExtenderCallbacks callbacks) {
        this.callbacks = callbacks;
        this.helpers = callbacks.getHelpers();
        callbacks.setExtensionName("Rhinauditor - JavaScript Scanner " + DetectorConstants.VERSION);
        callbacks.registerScannerCheck(this);
    }

    @Override
    public List<IScanIssue> doPassiveScan(IHttpRequestResponse requestResponse) {
        List<IScanIssue> issues = new ArrayList<IScanIssue>();

        byte[] respBytes = requestResponse.getResponse();

        IResponseInfo responseInfo = helpers.analyzeResponse(respBytes);
        IRequestInfo requestInfo = helpers.analyzeRequest(requestResponse.getHttpService(), requestResponse.getRequest());
        String path = HttpUtil.getPathRequested(requestInfo);


        String contentType = HttpUtil.getContentType(responseInfo);

        try {
            if (contentType.indexOf("javascript") != -1 || path.endsWith(".js")) {
                int bodyOffset = responseInfo.getBodyOffset();

                //Creating this "offset" InputStream avoid creating an additional byte array.
                ByteArrayInputStream in = new ByteArrayInputStream(respBytes, bodyOffset, respBytes.length - bodyOffset);

                issues = scanJavaScript(in, path, requestResponse, requestInfo);

                System.out.println("Body : " + new String(respBytes, bodyOffset, respBytes.length - bodyOffset));

            } else if (contentType.indexOf("html") != -1 || path.endsWith(".html")) {

                //TODO : Match <script> in the page
            }
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
            e.printStackTrace();
        }
        return issues;
    }

    @Override
    public List<IScanIssue> doActiveScan(IHttpRequestResponse baseRequestResponse, IScannerInsertionPoint insertionPoint) {
        return new ArrayList<IScanIssue>();
    }

    @Override
    public int consolidateDuplicateIssues(IScanIssue existingIssue, IScanIssue newIssue) {
        return 0;
    }

    private List<IScanIssue> scanJavaScript(InputStream inputStream, String scriptName, IHttpRequestResponse resp,IRequestInfo requestInfo) throws IOException {
        JavaScriptScanner scanner = new JavaScriptScanner();

        for (Detector d : DETECTORS) {
            scanner.addDetector(d);
        }

        CollectorBugReporter reporter = new CollectorBugReporter();
        scanner.setBugReporter(reporter);

        scanner.scan(inputStream, scriptName);

        URL urlRequested = requestInfo.getUrl();

        List<IScanIssue> issues = new ArrayList<IScanIssue>();
        for (BugInstance bug : reporter.getBugs()) {
            issues.add(BurpBugConverter.convertBugToScanIssue(bug, resp,urlRequested));
        }
        return issues;
    }

}
