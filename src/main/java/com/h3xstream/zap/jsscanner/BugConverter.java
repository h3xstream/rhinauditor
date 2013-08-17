package com.h3xstream.zap.jsscanner;

import com.h3xstream.zap.jsscanner.engine.api.BugInstance;
import org.parosproxy.paros.core.scanner.Alert;

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

    public static Alert convertBugToAlert(int pluginId,BugInstance bug) {
        String description = props.getProperty(bug.getAbbrev()+".description");
        if(description == null) {
            description = "Key ["+bug.getAbbrev()+".description] not found !";
        }
        String url = props.getProperty(bug.getAbbrev()+".url");

        //TODO: Give some alert a different risk for the most important ones
        return new Alert(pluginId, Alert.RISK_LOW, Alert.SUSPICIOUS, description);
    }
}
