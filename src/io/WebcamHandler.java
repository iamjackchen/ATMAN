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

/*Need a thread to handle webcam image scanning because
 - webcam doesn't have built-in function for looped image scanning
 - incorporating loop directly into UI code may prevent UI code from working properly
     - There needs to be 2 threads running at the same time, 1 to handle image scanning and 1 to handle image display in the UI panel


*/

public class WebcamHandler extends Thread {
    
    //class members
    private String previousData;
    private Webcam webcam;
    private EventKioskDataDisplayPanel dataDisplay;
    private AttendanceTableModel attendanceTableModel;
    private AttendeeComparator qrComparator = new AttendeeComparator(-1);

    private boolean running;

    public void kill() {running = false;}
    
    //constructor
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
                    
                    //attempts to get image
                    image = webcam.getImage();

                    if (image != null) {
                        
                        //image processing
                        LuminanceSource source = new BufferedImageLuminanceSource(image);
                        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
                        Result result = new MultiFormatReader().decode(bitmap);

                        if (attendanceTableModel.needsKioskRefresh()) previousData = "";
                        //^^ Need this flag to handle cases where a new project is opened without restarting the thread
                        //If a qr code is scanned before the new project is opened, and then scanned again after the new project is opened,
                        //the second conditional in the statement below will cause the app to ignore the previously-already-scanned qr code

                        //if processed image contains text or not
                        if (result.getText() != null && result.getText() != previousData) /* added second conditional to improve efficiency, effectively caches result*/ {
                            
                            previousData = result.getText();
                            Attendee dummy = new Attendee();
                            dummy.setQRData(previousData);
                            
                            //does a binary search through the attendancetablemodel for an attendee that has the same QR data as scanned (via searching for a dummy Attendee
                            int x = Collections.binarySearch(attendanceTableModel.getSortedAttendeeList(-1), dummy, qrComparator);
                            dataDisplay.update(attendanceTableModel.getSortedAttendeeList(-1).get(x));


                        }
                    }
                }

            }catch (NotFoundException e) {
                //pass - nothing detected
            } catch (Exception e) {
                e.printStackTrace();
            }

        } while (running);

    }

}
