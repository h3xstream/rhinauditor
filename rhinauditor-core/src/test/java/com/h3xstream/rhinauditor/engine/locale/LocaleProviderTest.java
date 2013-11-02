package com.h3xstream.rhinauditor.engine.locale;

import com.h3xstream.rhinauditor.engine.DetectorConstants;
import com.h3xstream.rhinauditor.engine.api.Detector;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.testng.Assert.assertFalse;
import static org.testng.AssertJUnit.assertNotNull;

public class LocaleProviderTest {

    @Test
    public void findMissingMessage() throws IOException {
        for(Detector d : DetectorConstants.DEFAULT_DETECTORS_LIST) {
            String id = d.getId();
            String clz = d.getClass().getName();
            assertNotNull(id);

            assertFalse(LocaleProvider.getDescription(d.getId()).contains("!"), "Description is missing for the detector "+id+" ("+clz+")");
            assertFalse(LocaleProvider.getTitle(d.getId()).contains("!"), "Title is missing for the detector "+id+" ("+clz+")");
            assertFalse(LocaleProvider.getReferencesUrl(d.getId())[0].contains("!"), "URL is missing for the detector "+id+" ("+clz+")");

        }
    }
}
