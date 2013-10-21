package com.h3xstream.rhinauditor.zap;

import com.h3xstream.rhinauditor.engine.CollectorBugReporter;
import com.h3xstream.rhinauditor.engine.DetectorConstants;
import com.h3xstream.rhinauditor.engine.JavaScriptScanner;
import com.h3xstream.rhinauditor.engine.api.BugInstance;
import com.h3xstream.rhinauditor.engine.api.Detector;
import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.Source;
import org.apache.log4j.Logger;
import org.parosproxy.paros.core.scanner.Alert;
import org.parosproxy.paros.network.HttpMessage;
import org.parosproxy.paros.network.HttpResponseHeader;
import org.zaproxy.zap.extension.pscan.PassiveScanThread;
import org.zaproxy.zap.extension.pscan.PluginPassiveScanner;

import java.util.List;

public class JavascriptScannerPlugin extends PluginPassiveScanner {

    private PassiveScanThread parent = null;

    private static int PLUGIN_ID = 0x1337BABE;

    private Logger logger = Logger.getLogger(JavascriptScannerPlugin.class);

    private static final Detector[] DETECTORS = DetectorConstants.DEFAULT_DETECTORS_LIST;

    @Override
    public void scanHttpRequestSend(HttpMessage httpMessage, int id) {

    }

    @Override
    public void scanHttpResponseReceive(HttpMessage httpMessage, int refId, Source source) {
        HttpResponseHeader h =  httpMessage.getResponseHeader();
        String url = httpMessage.getRequestHeader().getURI().toString();
        if(h.isJavaScript() || url.endsWith(".js")) {
            logger.info("JAVASCRIPT START");
            String scriptFile = httpMessage.getResponseBody().toString();
            logger.info("File Script found:"+scriptFile.substring(0,Math.min(10,scriptFile.length())));

            scanJavaScript(scriptFile, refId, httpMessage);
            logger.info("JAVASCRIPT END");
        }
        else if(h.isHtml()) {
            logger.info("HTML START");
            List<Element> listScript = source.getAllElements("script");
            for (Element e : listScript) {
                String inlineScript = e.getContent().toString();
                logger.info("Inline Script found:"+ inlineScript.substring(0,Math.min(10,inlineScript.length())));

                scanJavaScript(inlineScript, refId, httpMessage);
            }
            logger.info("HTML END");
        }
    }

    private void scanJavaScript(String script,int refId,HttpMessage httpMessage) {
        JavaScriptScanner scanner = new JavaScriptScanner();

        for(Detector d : DETECTORS) {
            scanner.addDetector(d);
        }

        CollectorBugReporter reporter = new CollectorBugReporter();
        scanner.setBugReporter(reporter);

        String uri = httpMessage.getRequestHeader().getURI().toString();
        scanner.scan(script,uri);

        for(BugInstance bug : reporter.getBugs()) {
            Alert newAlert = ZapBugConverter.convertBugToAlert(PLUGIN_ID, bug, httpMessage);
            this.parent.raiseAlert(refId, newAlert);
        }
    }

    @Override
    public void setParent(PassiveScanThread thread) {
        this.parent = thread;
    }

    @Override
    public String getName() {
        return "JavaScript Scanner";
    }
}
