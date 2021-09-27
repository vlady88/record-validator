package com.surepay.csp.record;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class RecordValidatorTest {

    private static final String JSON_INPUT_FILE = "src/test/resources/records.json";
    private static final String CSV_INPUT_FILE = "src/test/resources/records.csv";

    private RecordValidator recordValidator;

    @Before
    public void init() {
        recordValidator = new RecordValidator();
    }

    @Test
    public void testValidRecordsFromFiles() {
        Assert.assertEquals(5, recordValidator.validateRecords(JSON_INPUT_FILE, CSV_INPUT_FILE).size());
    }

    @Test
    public void testValidRecords() {
        List<Record> records = new ArrayList<>();
        records.add(new Record("194261", "NL91RABO0315273637", "Clothes from Jan Bakker", "21.6", "-41.83", "-20.23"));
        records.add(new Record("112806", "NL27SNSB0917829871", "Clothes for Willem Dekker", "91.23", "+15.57", "106.8"));
        Assert.assertTrue(recordValidator.validateRecords(records).isEmpty());
    }

    @Test
    public void testInvalidBalance() {
        List<Record> records = new ArrayList<>();
        records.add(new Record("194261", "NL91RABO0315273637", "Clothes from Jan Bakker", "21.6", "-41.83", "-20.23"));
        records.add(new Record("112806", "NL27SNSB0917829871", "Clothes for Willem Dekker", "91.23", "+15.57", "106.9"));
        List<Record> invalidRecords = recordValidator.validateRecords(records);
        Assert.assertFalse(invalidRecords.isEmpty());
        Assert.assertEquals("112806", invalidRecords.get(0).getReference());
        Assert.assertEquals(Record.Status.INVALID_BALANCE, invalidRecords.get(0).getStatus());
    }

    @Test
    public void testDuplicateReferences() {
        List<Record> records = new ArrayList<>();
        records.add(new Record("112806", "NL91RABO0315273637", "Clothes from Jan Bakker", "21.6", "-41.83", "-20.23"));
        records.add(new Record("112806", "NL27SNSB0917829871", "Clothes for Willem Dekker", "91.23", "+15.57", "106.8"));
        List<Record> invalidRecords = recordValidator.validateRecords(records);
        Assert.assertEquals(invalidRecords.size(), 2);
        Assert.assertEquals(Record.Status.DUPLICATE, invalidRecords.get(0).getStatus());
        Assert.assertEquals(Record.Status.DUPLICATE, invalidRecords.get(1).getStatus());
    }
}
