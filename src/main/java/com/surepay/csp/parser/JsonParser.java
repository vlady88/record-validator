package com.surepay.csp.parser;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.surepay.csp.io.InputValidator;
import com.surepay.csp.record.Record;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JsonParser implements RecordParser {

    private static final Logger LOGGER = LoggerFactory.getLogger(JsonParser.class);
    private final InputValidator inputValidator = new InputValidator();

    /**
     * parses the records from a JSON file and validates the input
     * @return the list of parsed records
     */
    @Override
    public List<Record> parseRecords(String inputFile) {
        List<Record> records = new ArrayList<>();

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(new File(inputFile));

            for (JsonNode resultNode : jsonNode) {
                Record record = parseRecord(resultNode);

                if(record != null) {
                    records.add(record);
                }
            }
        } catch (IOException e) {
            LOGGER.error("couldn't parse file: " + inputFile);
        }

        return records;
    }

    private Record parseRecord(JsonNode recordNode) {
        String reference = recordNode.findPath("reference").asText();
        String iban = recordNode.findPath("accountNumber").asText();
        String description = recordNode.findPath("description").asText();
        String startBalance = recordNode.findPath("startBalance").asText();
        String mutation = recordNode.findPath("mutation").asText();
        String endBalance = recordNode.findPath("endBalance").asText();

        if(inputValidator.validateInput(reference, iban, startBalance, mutation, endBalance)) {
            return new Record(reference, iban, description, startBalance, mutation, endBalance);
        } else {
            LOGGER.error("couldn't parse record data: " + reference + ", " + iban + ", "
                    + description + ", " + startBalance + ", " + mutation + ", " + endBalance);
            return null;
        }
    }
}
