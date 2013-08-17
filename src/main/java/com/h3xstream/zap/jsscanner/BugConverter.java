package com.h3xstream.zap.jsscanner;

import com.h3xstream.zap.jsscanner.engine.api.BugInstance;
import org.parosproxy.paros.core.scanner.Alert;
import org.parosproxy.paros.network.HttpMessage;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class BugConverter {
    static Properties props = new Properties();

    static {
        InputStream in = BugConverter.class.getResourceAsStream("/locale_jsscanner/messages.properties");
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
                "1", //Param
                "2",
                "3", //More info
                "4", //Solution
                url,
                message
                );
        return alert;
    }
}
