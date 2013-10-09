package com.h3xstream.rhinauditor.cli.output;

import com.h3xstream.rhinauditor.cli.util.MustacheHelper;
import com.h3xstream.rhinauditor.engine.api.BugInstance;

import java.io.*;
import java.text.DateFormat;
import java.util.*;

public class HtmlReport extends ReportOutput {

    private final OutputStream out;

    private String currentFileName;
    private List<BugInstance> bugInstances = new ArrayList<BugInstance>();

    public HtmlReport(OutputStream out) {
        this.out = out;
    }

    @Override
    public void beforeListing() {

        Map<String, Object> scopes = new HashMap<String, Object>();
        scopes.put("title", "Bugs report");
        scopes.put("generatedDate", DateFormat.getDateInstance().format(Calendar.getInstance().getTime()));
        scopes.put("generatedBy", System.getProperty("user.name"));
        try {

            //StringBuilder is a way to avoid till the last minute to build a string
            StringBuilder bufferScript = new StringBuilder();
            loadFileContentToBuffer("/js/shCore.js",bufferScript);
            loadFileContentToBuffer("/js/shBrushJScript.js",bufferScript);
            bufferScript.append("SyntaxHighlighter.all();");
            scopes.put("aggregateScript", bufferScript.toString());

            StringBuilder bufferStyle = new StringBuilder();
            loadFileContentToBuffer("/css/shCoreDefault.css",bufferStyle);
            loadFileContentToBuffer("/css/shThemeEclipse.css",bufferStyle);
            scopes.put("aggregateStyle",bufferStyle.toString());

            MustacheHelper.buildTemplate("/html/header.html", scopes, out);

        } catch (IOException e) {
            System.err.println(e.getMessage()); //TODO: Replace with logging
        }

        flush();
    }

    @Override
    public void afterListing() {
        Map<String, Object> scopes = new HashMap<String, Object>();
        try {
            MustacheHelper.buildTemplate("/html/footer.html", scopes, out);
        } catch (IOException e) {
            System.err.println(e.getMessage()); //TODO: Replace with logging
        }
        flush();
    }


    @Override
    public void beforeFile(String fileName) {
        bugInstances.clear();
        currentFileName = fileName;
    }

    @Override
    public void afterFile() {

        Map<String, Object> scopes = new HashMap<String, Object>();
        scopes.put("file", new File(currentFileName).getName());
        scopes.put("filePath", currentFileName);
        scopes.put("bugs", bugInstances);

        try {
            MustacheHelper.buildTemplate("/html/fileReport.html",scopes,out);
        } catch (IOException e) {
            System.err.println(e.getMessage()); //TODO: Replace with logging
        }
    }

    @Override
    public void newBugInstance(BugInstance bugInstance) {
        bugInstances.add(bugInstance);
    }


    private void loadFileContentToBuffer(String resourcePath,StringBuilder buffer) throws IOException {
        InputStream in = HtmlReport.class.getResourceAsStream(resourcePath);
        if(in == null) {
            System.err.println("File not found : "+resourcePath); //TODO: Replace with logging
            return;
        }
        Reader r = new InputStreamReader(in);

        char[] bytes = new char[1024];

        int actualLen;
        do {
            actualLen = r.read(bytes);
            if(actualLen>0) {
                buffer.append(bytes,0,actualLen);
            }
        }
        while(actualLen != -1);
    }

    private void flush() {
        try {
            out.flush();
        } catch (IOException e) {
            System.err.println(e.getMessage()); //TODO: Replace with logging
        }
    }

}
