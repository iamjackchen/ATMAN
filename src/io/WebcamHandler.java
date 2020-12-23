package io;

import com.github.sarxos.webcam.Webcam;
import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import data.AttendanceTableModel;
import data.types.Attendee;
import data.types.AttendeeComparator;
import ui.panels.subpanels.EventKioskDataDisplayPanel;

import javax.swing.table.AbstractTableModel;
import java.awt.image.BufferedImage;
import java.util.Collections;
import java.util.List;

public class WebcamHandler extends Thread {

    private String previousData;
    private Webcam webcam;
    private EventKioskDataDisplayPanel dataDisplay;
    private AttendanceTableModel attendanceTableModel;

    private boolean running;

    public void kill() {running = false;}

    public WebcamHandler(Webcam webcam, EventKioskDataDisplayPanel dataDisplay, AttendanceTableModel attendanceTableModel) {
        this.attendanceTableModel = attendanceTableModel;
        this.dataDisplay = dataDisplay;
        this.webcam = webcam;
        running = true;

    }

    public void run() {

        running = true;

        do {

            try {
                BufferedImage image = null;
                if (webcam != null) {
                    image = webcam.getImage();

                    if (image != null) {
                        LuminanceSource source = new BufferedImageLuminanceSource(image);
                        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
                        Result result = new MultiFormatReader().decode(bitmap);
                        if (result.getText() != null && result.getText() != previousData) {
                            previousData = result.getText();
                            Attendee dummy = new Attendee("");
                            dummy.setQRData(previousData);

                            int x = Collections.binarySearch(attendanceTableModel.getSortedAttendeeList(-1), dummy, new AttendeeComparator(-1));
                            dataDisplay.update(attendanceTableModel.getSortedAttendeeList(-1).get(x));



                        }
                    }
                }

            }catch (NotFoundException e) {
                //pass
            } catch (Exception e) {
                e.printStackTrace();
            }

        } while (running);

    }


}
