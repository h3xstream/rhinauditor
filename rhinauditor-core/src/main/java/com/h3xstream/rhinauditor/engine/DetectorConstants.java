package com.h3xstream.rhinauditor.engine;

import com.h3xstream.rhinauditor.engine.api.Detector;
import com.h3xstream.rhinauditor.engine.impl.DocumentWriteDetector;
import com.h3xstream.rhinauditor.engine.impl.EvalDetector;
import com.h3xstream.rhinauditor.engine.impl.InnerHtmlDetector;

public class DetectorConstants {
    public static final Detector[] DEFAULT_DETECTORS_LIST = {
            new InnerHtmlDetector(),
            new EvalDetector(),
            new DocumentWriteDetector()
    };

}
