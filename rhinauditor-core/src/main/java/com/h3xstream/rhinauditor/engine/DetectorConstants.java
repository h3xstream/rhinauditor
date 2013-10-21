package com.h3xstream.rhinauditor.engine;

import com.h3xstream.rhinauditor.engine.api.Detector;
import com.h3xstream.rhinauditor.engine.impl.*;
import com.h3xstream.rhinauditor.engine.impl.crypto.RandomDetector;
import com.h3xstream.rhinauditor.engine.impl.dom.DocumentWriteDetector;
import com.h3xstream.rhinauditor.engine.impl.dom.InnerHtmlDetector;
import com.h3xstream.rhinauditor.engine.impl.injection.EvalDetector;
import com.h3xstream.rhinauditor.engine.impl.injection.SetTimeOutDetector;
import com.h3xstream.rhinauditor.engine.impl.redirect.WindowLocationDetector;

public class DetectorConstants {
    public static final String VERSION = "1.0.0";

    public static final Detector[] DEFAULT_DETECTORS_LIST = {
            new DocumentWriteDetector(),
            new EvalDetector(),
            new InnerHtmlDetector(),
            new LocalStorageDetector(),
            new RandomDetector(),
            new WindowLocationDetector(),
            new SetTimeOutDetector(),
    };

}
