package com.h3xstream.rhinauditor.engine.api;

public class BugInstance {

    private final String file;
    private final int line;
    private final String abbrev;
    private final String code;


    public BugInstance(String file, int line, String abbrev, String code) {
        this.file = file;
        this.line = line;
        this.abbrev = abbrev;
        this.code = code;
    }

    public String getFile() {
        return file;
    }

    public int getLine() {
        return line;
    }

    public String getAbbrev() {
        return abbrev;
    }

    public String getCode() {
        return code;
    }


    @Override
    public String toString() {
        return ""+file+ ":" + line+" => "+abbrev;
    }

}
