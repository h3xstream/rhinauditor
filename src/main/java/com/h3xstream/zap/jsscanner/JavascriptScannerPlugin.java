package com.h3xstream.zap.jsscanner;

import com.h3xstream.zap.jsscanner.BugConverter;
import com.h3xstream.zap.jsscanner.engine.CollectorReporter;
import com.h3xstream.zap.jsscanner.engine.JavaScriptScanner;
import com.h3xstream.zap.jsscanner.engine.api.BugInstance;
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

    @Override

    public void scanHttpRequestSend(HttpMessage httpMessage, int id) {

    }

    @Override
    public void scanHttpResponseReceive(HttpMessage httpMessage, int refId, Source source) {
        HttpResponseHeader h =  httpMessage.getResponseHeader();

        if(h.isJavaScript()) {
            logger.info("JAVASCRIPT START");
            String scriptFile = httpMessage.getResponseBody().toString();
            logger.info("File Script found:"+scriptFile.substring(0,Math.min(10,scriptFile.length())));
            logger.info("JAVASCRIPT END");
        }
        else if(h.isHtml()) {
            logger.info("HTML START");
            List<Element> listScript = source.getAllElements("script");
            for (Element e : listScript) {
                String inlineScript = e.getContent().toString();
                logger.info("Inline Script found:"+ inlineScript.substring(0,Math.min(10,inlineScript.length())));
            }
            logger.info("HTML END");
        }
    }

    private void scanJavaScript(String script,int refId) {
        JavaScriptScanner scanner = new JavaScriptScanner();
        CollectorReporter reporter = new CollectorReporter();
        scanner.setReporter(reporter);

        for(BugInstance bug : reporter.getBugs()) {
            Alert newAlert = BugConverter.convertBugToAlert(PLUGIN_ID,bug);
            this.parent.raiseAlert(refId, newAlert);
        }
    }

    @Override
    public void setParent(PassiveScanThread thread) {
        this.parent = thread;
    }

    @Override
    public String getName() {
        return null;
    }
}
