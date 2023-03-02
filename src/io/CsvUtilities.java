package io;

import data.AttendanceTableModel;
import data.MathUtilities;
import data.types.Attendee;
import data.types.attributes.Attendance;
import data.types.attributes.Sex;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class CsvUtilities {

    public static ArrayList<Attendee> getAttendeeListFromCSVWithDefaultParameters(String filepath) throws IOException {

        ArrayList<Attendee> returnVal = new ArrayList<Attendee>();
        BufferedReader csvReader = new BufferedReader(new FileReader(filepath));
        csvReader.readLine(); //Skips first line

        String nextRow;
        while ((nextRow = csvReader.readLine()) != null) {
            String[] parsedData = nextRow.split(",");

            if (parsedData.length != 9) {throw new IOException("CSV Incomplete");}

            Attendee newEntry = new Attendee(parsedData[0],
                    parsedData[1],
                    MathUtilities.tryParseInt(parsedData[2]),
                    MathUtilities.tryParseInt(parsedData[3]),
                    new Sex(parsedData[4]),
                    parsedData[5],
                    MathUtilities.tryParseInt(parsedData[6]),
                    new Attendance(parsedData[7])
            );

            newEntry.setQRData(parsedData[8]);
            returnVal.add(newEntry);

            
        }

        return returnVal;
    }

    public static void writeCSVDefaultParameters(AttendanceTableModel source, File target) throws IOException {

        FileWriter csvWriter = new FileWriter(target + ".csv");

        String[] parametersList = Arrays.copyOfRange(source.getAttendeeAt(0).getParameterList(), 1, source.getAttendeeAt(0).getParameterList().length);

        csvWriter.append(String.join(",", parametersList));
        csvWriter.append("\n");

        for (int i = 0; i < source.getAttendeeList().size(); i++) {

            String rawAttendeeData = (source.getAttendeeAt(i).toString());
            csvWriter.append(rawAttendeeData.substring(rawAttendeeData.indexOf(",")+1));
            csvWriter.append("," + source.getAttendeeAt(i).getQRContents());
            csvWriter.append("\n");

        }

        csvWriter.flush();
        csvWriter.close();

        System.out.println("done");

    }






}
