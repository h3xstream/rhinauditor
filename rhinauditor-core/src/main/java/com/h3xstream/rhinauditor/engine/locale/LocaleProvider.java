package com.h3xstream.rhinauditor.engine.locale;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class LocaleProvider {

    static Properties props = new Properties();

    static {
        InputStream in = LocaleProvider.class.getResourceAsStream("/rhinauditor_i18n/messages.properties");
        try {
            props.load(in);
        } catch (IOException e) {
            //Oups
        }
    }

    public static String getSourceLocation() {
        return getValue("source_location");
    }

    public static String getAdditionalReferences() {
        return getValue("additional_references");
    }

    public static Object getVulnerableCode() {
        return getValue("vulnerable_code");
    }

    public static String getTitle(String abbr) {
        return getValue(abbr + ".title");
    }

    public static String getDescription(String abbr) {
        return getValue(abbr + ".description");
    }

    public static String[] getReferencesUrl(String abbr) {
        return getValue(abbr + ".url").split(",");
    }

    private static String getValue(String key) {
        String description = props.getProperty(key);
        return description == null ? "!"+key+"!" : description;
    }

}
