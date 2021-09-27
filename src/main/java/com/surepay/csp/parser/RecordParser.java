package com.surepay.csp.parser;

import com.surepay.csp.record.Record;

import java.util.List;

public interface RecordParser {

    List<Record> parseRecords(String inputFile);
}
