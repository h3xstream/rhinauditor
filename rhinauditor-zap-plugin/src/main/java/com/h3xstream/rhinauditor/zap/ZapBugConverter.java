package com.h3xstream.rhinauditor.zap;

import com.h3xstream.rhinauditor.engine.api.BugInstance;
import com.h3xstream.rhinauditor.engine.locale.LocaleProvider;
import org.parosproxy.paros.core.scanner.Alert;
import org.parosproxy.paros.network.HttpMessage;

/**
 * This utility class convert the @code{BugInstance} generate by the core engine to @code{Alert}.
 * 
 */
public class ZapBugConverter {


    public static Alert convertBugToAlert(int pluginId,BugInstance bug,HttpMessage message) {
        String description = LocaleProvider.getDescription(bug.getAbbrev());
        String[] urls = LocaleProvider.getReferencesUrl(bug.getAbbrev());

        //TODO: Give some alert a different risk for the most important ones
        Alert alert = new Alert(pluginId, Alert.RISK_LOW, Alert.SUSPICIOUS, description);
        alert.setDetail(description,
                message.getRequestHeader().getURI().toString(),
                bug.getFile()+":"+bug.getLine(), //Param
                "", //Attack
                bug.getCode(), //Other info
                "", //Solution
                urls[0],
                message
                );
        return alert;
    }
}
