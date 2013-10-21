package burp;

import java.net.URL;

class CustomScanIssue implements IScanIssue {

    private IHttpService httpService;
    private URL url;
    private IHttpRequestResponse[] httpMessages;
    private String name;
    private String detail;
    private String severity;
    private String confidence;

    /**
     *
     * @param httpService Can be extract from HttpRequestResponse
     * @param url
     * @param httpMessages
     * @param name
     * @param detail
     * @param severity "High", "Medium", "Low", "Information" or "False positive"
     * @param confidence "Certain", "Firm" or "Tentative"
     */
    public CustomScanIssue(IHttpService httpService, URL url, IHttpRequestResponse[] httpMessages, String name, String detail, String severity,String confidence) {
        this.url = url;
        this.name = name;
        this.detail = detail;
        this.severity = severity;
        this.httpService = httpService;
        this.httpMessages = httpMessages;
        this.confidence = confidence;
    }

    @Override
    public URL getUrl() {
        return url;
    }

    @Override
    public String getIssueName() {
        return name;
    }

    @Override
    public int getIssueType() {
        return 0;
    }

    @Override
    public String getSeverity() {
        return severity;
    }

    @Override
    public String getConfidence() {
        return confidence;
    }

    @Override
    public String getIssueBackground() {
        return null;
    }

    @Override
    public String getRemediationBackground() {
        return null;
    }

    @Override
    public String getIssueDetail() {
        return detail;
    }

    @Override
    public String getRemediationDetail() {
        return null;
    }

    @Override
    public IHttpRequestResponse[] getHttpMessages() {
        return httpMessages;
    }

    @Override
    public IHttpService getHttpService() {
        return httpService;
    }
}