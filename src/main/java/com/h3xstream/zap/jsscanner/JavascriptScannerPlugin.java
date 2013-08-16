package com.h3xsream.zap.jsscanner;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.Source;
import org.apache.log4j.Logger;
import org.parosproxy.paros.network.HttpMessage;
import org.parosproxy.paros.network.HttpResponseHeader;
import org.zaproxy.zap.extension.pscan.PassiveScanThread;
import org.zaproxy.zap.extension.pscan.PluginPassiveScanner;

import java.util.List;

public class JavascriptScannerPlugin extends PluginPassiveScanner {

    private PassiveScanThread parent = null;

    private Logger logger = Logger.getLogger(getClass());

    @Override
    public void scanHttpRequestSend(HttpMessage httpMessage, int id) {

    }

    @Override
    public void scanHttpResponseReceive(HttpMessage httpMessage, int id, Source source) {
        HttpResponseHeader h =  httpMessage.getResponseHeader();

        if(h.isJavaScript()) {
            logger.info("JAVASCRIPT START");
            String scriptFile = httpMessage.getResponseBody().toString();
            logger.info("File Script found:"+scriptFile);
            logger.info("JAVASCRIPT END");
        }
        else if(h.isHtml()) {
            logger.info("HTML START");
            List<Element> listScript = source.getAllElements("script");
            for (Element e : listScript) {
                logger.info("Inline Script found:"+ e.getContent().toString());
            }
            logger.info("HTML END");
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
