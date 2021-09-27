package com.surepay.csp.io;

import com.surepay.csp.Main;
import com.surepay.csp.record.Record;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HtmlWriter {

    private static final Logger LOGGER = LoggerFactory.getLogger(HtmlWriter.class);

    /**
     * prints a list of records to a file
     */
    public void writeFailedRecords(List<Record> failedRecords, String outputFilePath) {
        try {
            PrintWriter printWriter = new PrintWriter(outputFilePath);
            printWriter.print(recordsToHtml(failedRecords));
            printWriter.close();
        } catch (IOException | TemplateException e) {
            LOGGER.error("couldn't write output to file: " + outputFilePath);
        }
    }

    /**
     * @return a string with the json representation of a list of records
     */
    private String recordsToHtml(List<Record> records) throws IOException, TemplateException {
        String html = "";

        /* Create and adjust the configuration singleton */
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_29);
        cfg.setDirectoryForTemplateLoading(new File("src/main/resources/templates/"));
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        cfg.setLogTemplateExceptions(false);
        cfg.setWrapUncheckedExceptions(true);
        cfg.setFallbackOnNullLoopVariable(false);

        /* populate the data-model */
        Map<String, Object> root = new HashMap<>();
        root.put("failedRecords", records);

        /* Get the template (uses cache internally) */
        Template temp = cfg.getTemplate("output.ftl");

        /* Merge data-model with template */
        Writer out = new StringWriter();
        temp.process(root, out);
        html = out.toString();
        out.close();

        return html;
    }
}
