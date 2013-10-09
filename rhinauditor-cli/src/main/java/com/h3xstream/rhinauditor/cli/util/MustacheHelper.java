package com.h3xstream.rhinauditor.cli.util;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import com.h3xstream.rhinauditor.cli.output.HtmlReport;

import java.io.*;

public class MustacheHelper {
    public static void buildTemplate(String pathTemplate,Object ctx,OutputStream out) throws IOException {
        MustacheFactory mf = new DefaultMustacheFactory();

        Reader r = new InputStreamReader(HtmlReport.class.getResourceAsStream(pathTemplate));
        Mustache mustache = mf.compile(r,"");
        mustache.execute(new PrintWriter(out),ctx).flush();

    }
}
