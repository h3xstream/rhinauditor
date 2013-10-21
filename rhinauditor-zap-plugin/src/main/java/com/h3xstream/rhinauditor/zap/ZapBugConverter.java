package com.h3xstream.rhinauditor.zap;

import com.h3xstream.rhinauditor.engine.api.BugInstance;
import org.parosproxy.paros.core.scanner.Alert;
import org.parosproxy.paros.network.HttpMessage;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * This utility class convert the @code{BugInstance} generate by the core engine to @code{Alert}.
 * 
 */
public class ZapBugConverter {
    static Properties props = new Properties();

    static {
        InputStream in = ZapBugConverter.class.getResourceAsStream("/locale_rhinauditor_plugin/messages.properties");
        try {
            props.load(in);
        } catch (IOException e) {
            //Oups
        }
    }

    public static Alert convertBugToAlert(int pluginId,BugInstance bug,HttpMessage message) {
        String description = props.getProperty(bug.getAbbrev()+".description");
        if(description == null) {
            description = "Key ["+bug.getAbbrev()+".description] not found !";
        }
        String url = props.getProperty(bug.getAbbrev()+".url");
        if(url == null) {
            url = "";
        }

        //TODO: Give some alert a different risk for the most important ones
        Alert alert = new Alert(pluginId, Alert.RISK_LOW, Alert.SUSPICIOUS, description);
        alert.setDetail(description,
                message.getRequestHeader().getURI().toString(),
                bug.getFile()+":"+bug.getLine(), //Param
                "2", //Attack
                bug.getCode(), //Other info
                "4", //Solution
                url,
                message
                );
        return alert;
    }
}
