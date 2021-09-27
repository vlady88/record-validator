package com.surepay.csp;

import com.surepay.csp.io.HtmlWriter;
import com.surepay.csp.record.RecordValidator;

public class Main {

    private static final String OUTPUT_FILE = "output/failed-records.html";

    public static void main(String[] args) {
        // validate the records and write invalid ones to output file
        new HtmlWriter().writeFailedRecords(new RecordValidator().validateRecords(args), OUTPUT_FILE);
    }
}
