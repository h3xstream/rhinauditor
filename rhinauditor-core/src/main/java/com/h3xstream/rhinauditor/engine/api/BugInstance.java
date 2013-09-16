package com.h3xstream.rhinauditor.engine.api;

public class BugInstance {

    private final String file;
    private final int line;
    private final String abbrev;

    public BugInstance(String file, int line, String abbrev) {
        this.file = file;
        this.line = line;
        this.abbrev = abbrev;
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

    @Override
    public String toString() {
        return ""+file+ ":" + line+" => "+abbrev;
    }

}
