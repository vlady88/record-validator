
Records Validator
=================

Requirements: Maven, Java 11

Implementation Remarks
----------------------

- Records are parsed from the input files with the help of `CSVParser` and `JsonParser` classes, which implement the `RecordParser` interface. `CSVParser` makes use of the OpenCSV library for parsing CSVs and `JsonParser` uses Jackson library for parsing JSON.
- Input validation is done with the help of `commons.validator` library for checking IBANs. In case of invalid input, an error message is logged.
- For best accuracy, `BigDecimal` objects are used for checking the balances of the records.
- For record validation, if multiple records have the same reference, then all of them are reported as invalid.
- Output is written to an HTML file with the help of Freemarker templating library. The output has the same styling as `instructions.html`. The output is written to `output/failed-records.html`.
- Logging is done with the help of Logback library. Logs are written both to the console and to `logs/application.log`.

Usage
-----

- Compile and run the program with default input files: `mvn compile exec:java`
- Compile and run the program with custom input files: `mvn compile exec:java "-Dexec.args=input/records.csv input/records.json"` (tested on Windows)
- Run the tests: `mvn test`