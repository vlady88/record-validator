package com.surepay.csp.parser;

import au.com.bytecode.opencsv.CSVReader;
import com.surepay.csp.io.InputValidator;
import com.surepay.csp.record.Record;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVParser implements RecordParser {

    private static final Logger LOGGER = LoggerFactory.getLogger(CSVParser.class);
    private final InputValidator inputValidator = new InputValidator();

    /**
     * parses the records from a CSV file and validates the input
     * @return the list of parsed records
     */
    @Override
    public List<Record> parseRecords(String inputFile) {
        List<Record> records = new ArrayList<>();

        try (CSVReader csvReader = new CSVReader(new FileReader(inputFile))) {
            String[] values;
            csvReader.readNext(); // skip the header line

            while ((values = csvReader.readNext()) != null) {
                if(values.length == 6 && inputValidator.validateInput(
                        values[0], values[1], values[3], values[4], values[5])) {
                    records.add(new Record(values[0], values[1], values[2], values[3], values[4], values[5]));
                } else {
                    LOGGER.error("couldn't parse record data: " + values);
                }
            }
        } catch (IOException e) {
            LOGGER.error("couldn't parse file: " + inputFile);
        }

        return records;
    }
}
