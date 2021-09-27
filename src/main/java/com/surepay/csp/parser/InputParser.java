package com.surepay.csp.parser;

import com.surepay.csp.record.Record;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * generic class that chooses the right parser for a specific input file
 */
public class InputParser {

    private static final Logger LOGGER = LoggerFactory.getLogger(InputParser.class);

    private final RecordParser csvParser = new CSVParser();
    private final RecordParser jsonParser = new JsonParser();

    public List<Record> parseRecords(String inputFilePath) {
        if(inputFilePath.endsWith(".csv")) {
            return csvParser.parseRecords(inputFilePath);
        } else if(inputFilePath.endsWith(".json")) {
            return jsonParser.parseRecords(inputFilePath);
        } else {
            LOGGER.error("invalid input file format: " + inputFilePath);
            return new ArrayList<>();
        }
    }
}
