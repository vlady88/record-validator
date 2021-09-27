package com.surepay.csp.record;

import com.surepay.csp.parser.InputParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class RecordValidator {

    private static final Logger LOGGER = LoggerFactory.getLogger(RecordValidator.class);

    /**
     * validate the records parsed from a list of files
     */
    public List<Record> validateRecords(String... filePaths) {
        List<Record> records = new ArrayList<>();
        InputParser inputParser = new InputParser();

        // parse the records from the input files
        Arrays.stream(filePaths).forEach(inputFilePath -> records.addAll(inputParser.parseRecords(inputFilePath)));

        return validateRecords(records);
    }

    /**
     * validate a list of records; all records with duplicate references and invalid balances are considered failed
     * @return the list of invalid records
     */
    public List<Record> validateRecords(List<Record> records) {
        List<Record> failedRecords = new ArrayList<>();
        Map<String, Record> goodRecords = new HashMap<>();
        Set<String> references = new HashSet<>();

        for(Record record : records) {
            if(!checkBalance(record)) {
                // invalid balance case
                record.setStatus(Record.Status.INVALID_BALANCE);
                failedRecords.add(record);
            } else if(references.contains(record.getReference())) {
                // duplicate record case
                record.setStatus(Record.Status.DUPLICATE);
                failedRecords.add(record);

                // mark all duplicates as invalid
                if(goodRecords.containsKey(record.getReference())) {
                    Record duplicateRecord = goodRecords.remove(record.getReference());
                    duplicateRecord.setStatus(Record.Status.DUPLICATE);
                    failedRecords.add(duplicateRecord);
                }
            } else {
                // valid record case
                record.setStatus(Record.Status.VALID);
                goodRecords.put(record.getReference(), record);
            }

            references.add(record.getReference());
        }

        LOGGER.info("validation complete: " + failedRecords.size() + " invalid records found");

        return failedRecords;
    }

    /**
     * @return true if the balance is valid, false otherwise
     */
    private boolean checkBalance(Record record) {
        return record.getEndBalance().compareTo(record.getStartBalance().add(record.getMutation())) == 0;
    }
}
