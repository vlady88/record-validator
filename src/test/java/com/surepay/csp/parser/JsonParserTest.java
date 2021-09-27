package com.surepay.csp.parser;

import com.surepay.csp.record.Record;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class JsonParserTest {

    private static final String GOOD_INPUT_FILE = "src/test/resources/records.json";
    private static final String BAD_INPUT_FILE = "src/test/resources/records.csv";
    private static final String NON_EXISTENT_INPUT_FILE = "src/test/resources/records";

    @Test
    public void testValidInput() {
        RecordParser parser = new JsonParser();
        List<Record> records = parser.parseRecords(GOOD_INPUT_FILE);
        Assert.assertEquals(records.size(), 10);
    }

    @Test
    public void testInvalidInput() {
        RecordParser parser = new JsonParser();
        List<Record> records = parser.parseRecords(BAD_INPUT_FILE);
        Assert.assertEquals(records.size(), 0);
    }

    @Test
    public void testInputFileNotFound() {
        RecordParser parser = new JsonParser();
        List<Record> records = parser.parseRecords(NON_EXISTENT_INPUT_FILE);
        Assert.assertEquals(records.size(), 0);
    }
}
