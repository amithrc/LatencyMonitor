package main.java.monitor.utils;

import com.opencsv.CSVWriter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


/**
 * @author Amith
 * Helper class to write CSV fileFormat
 */
public class WriteCSV {

    private String[] header = null;
    private String fname = null;
    private CSVWriter csvWriter = null;

    /**
     * Constructors opens the csv file and writes the header
     *
     * @param header - header for the CSV file
     * @param fname  - Takes filename to create
     */
    public WriteCSV(String[] header, String fname) {
        this.header = header;
        this.fname = fname;
        try {
            csvWriter = new CSVWriter(new BufferedWriter(new FileWriter(new File(fname + ".csv"))));
        } catch (IOException e) {
            e.printStackTrace();
        }
        csvWriter.writeNext(header);
    }

    /**
     *
     * @param data
     */

    public void writeLine(String[] data) {
        csvWriter.writeNext(data);

        try {
            csvWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
