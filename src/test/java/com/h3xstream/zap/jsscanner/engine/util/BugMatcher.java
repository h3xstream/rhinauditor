package com.h3xstream.zap.jsscanner.engine.util;

import com.h3xstream.zap.jsscanner.engine.api.BugInstance;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

public class BugMatcher extends BaseMatcher<BugInstance> {

    private String bugAbbr;
    private Integer lineNumber;

    public BugMatcher(String bugAbbr, Integer lineNumber) {
        this.bugAbbr = bugAbbr;
        this.lineNumber = lineNumber;
    }



    @Override
    public boolean matches(Object obj) {
        if (obj instanceof BugInstance) {
            BugInstance bugInstance = (BugInstance) obj;

            boolean criteriaMatches = true;

            if(bugAbbr != null && !bugAbbr.equals(bugInstance.getAbbrev())) {
                return false;
            }
            if(lineNumber != null && !lineNumber.equals(bugInstance.getLine())) {
                return false;
            }

            return true;
        }

        return false;
    }

    @Override
    public void describeTo(Description description) {

    }
}
