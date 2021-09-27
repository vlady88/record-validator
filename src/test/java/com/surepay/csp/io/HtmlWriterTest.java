package com.surepay.csp.io;

import com.surepay.csp.record.Record;
import freemarker.template.TemplateException;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class HtmlWriterTest {

    private final static String OUTPUT_FILE = "output/failed-records.html";

    @Test
    public void testSuccessfulWrite() throws TemplateException, IOException {
        HtmlWriter htmlWriter = new HtmlWriter();
        List<Record> records = new ArrayList<>();

        Record record1 = new Record("112806", "NL91RABO0315273637", "Clothes from Jan Bakker", "21.6", "-41.83", "-20.23");
        Record record2 = new Record("112806", "NL27SNSB0917829871", "Clothes for Willem Dekker", "91.23", "+15.57", "106.8");
        record1.setStatus(Record.Status.DUPLICATE);
        record2.setStatus(Record.Status.DUPLICATE);
        records.add(record1);
        records.add(record2);

        htmlWriter.writeFailedRecords(records, OUTPUT_FILE);
        String content = Files.readString(Paths.get(OUTPUT_FILE), StandardCharsets.UTF_8);

        Assert.assertTrue(content.contains(record1.getReference()));
        Assert.assertTrue(content.contains(record1.getDescription()));
        Assert.assertTrue(content.contains(record1.getStatus().toString()));
        Assert.assertTrue(content.contains(record2.getReference()));
        Assert.assertTrue(content.contains(record2.getDescription()));
        Assert.assertTrue(content.contains(record2.getStatus().toString()));
    }

    /**
     * fails because records don't have the status set
     */
    @Test
    public void testUnsuccessfulWrite() throws TemplateException, IOException {
        HtmlWriter htmlWriter = new HtmlWriter();
        List<Record> records = new ArrayList<>();

        Record record1 = new Record("112806", "NL91RABO0315273637", "Clothes from Jan Bakker", "21.6", "-41.83", "-20.23");
        Record record2 = new Record("112806", "NL27SNSB0917829871", "Clothes for Willem Dekker", "91.23", "+15.57", "106.8");
        records.add(record1);
        records.add(record2);

        htmlWriter.writeFailedRecords(records, OUTPUT_FILE);
        String content = Files.readString(Paths.get(OUTPUT_FILE), StandardCharsets.UTF_8);

        Assert.assertEquals(0, content.length());
    }
}
