package io;

import com.github.sarxos.webcam.Webcam;
import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import data.AttendanceTableModel;
import data.types.Attendee;
import data.types.AttendeeComparator;
import ui.panels.subpanels.EventKioskDataDisplayPanel;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import java.awt.image.BufferedImage;
import java.util.Collections;
import java.util.List;

public class WebcamHandler extends Thread {

    private String previousData;
    private Webcam webcam;
    private EventKioskDataDisplayPanel dataDisplay;
    private AttendanceTableModel attendanceTableModel;
    private AttendeeComparator qrComparator = new AttendeeComparator(-1);

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

                        if (attendanceTableModel.needsKioskRefresh()) previousData = "";
                        //^^ Need this flag to handle cases where a new project is opened without restarting the thread
                        //If a qr code is scanned before the new project is opened, and then scanned again after the new project is opened,
                        //the second conditional in the statement below will cause the app to ignore the previously-already-scanned qr code

                        if (result.getText() != null && result.getText() != previousData) /* added second conditional to improve efficiency*/ {
                            previousData = result.getText();
                            Attendee dummy = new Attendee();
                            dummy.setQRData(previousData);

                            int x = Collections.binarySearch(attendanceTableModel.getSortedAttendeeList(-1), dummy, qrComparator);
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
