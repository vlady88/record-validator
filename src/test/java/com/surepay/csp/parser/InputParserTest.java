package com.surepay.csp.parser;

import com.surepay.csp.record.Record;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class InputParserTest {

    private static final String JSON_INPUT_FILE = "src/test/resources/records.json";
    private static final String CSV_INPUT_FILE = "src/test/resources/records.csv";
    private static final String NON_EXISTENT_INPUT_FILE = "src/test/resources/records";

    @Test
    public void testJsonInput() {
        InputParser parser = new InputParser();
        List<Record> records = parser.parseRecords(JSON_INPUT_FILE);
        Assert.assertFalse(records.isEmpty());
    }

    @Test
    public void testCsvInput() {
        InputParser parser = new InputParser();
        List<Record> records = parser.parseRecords(CSV_INPUT_FILE);
        Assert.assertFalse(records.isEmpty());
    }

    @Test
    public void testInvalidInput() {
        InputParser parser = new InputParser();
        List<Record> records = parser.parseRecords(NON_EXISTENT_INPUT_FILE);
        Assert.assertTrue(records.isEmpty());
    }
}
