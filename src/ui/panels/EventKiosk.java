package ui.panels;

import com.github.sarxos.webcam.*;
import data.AttendanceTableModel;
import io.WebcamHandler;
import ui.panels.subpanels.EventKioskDataDisplayPanel;


import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class EventKiosk extends JPanel {

    Webcam webcam;
    WebcamPanel webcamPanel;
    WebcamHandler qrHandler;
    AttendanceTableModel model;

    public EventKiosk(boolean firstTab, AttendanceTableModel model) {
        this.setLayout(new GridLayout(1, 2));
        this.model = model;
        Webcam webcam = Webcam.getDefault();


        webcam.open(true);
        webcamPanel = new WebcamPanel(webcam);

        if (!firstTab) webcamPanel.stop();

        webcamPanel.setMirrored(false);
        webcamPanel.setSize(WebcamResolution.VGA.getSize());

        EventKioskDataDisplayPanel dataDisplay = new EventKioskDataDisplayPanel();
        dataDisplay.setBorder(new EmptyBorder(20, 20, 20, 20));

        qrHandler = new WebcamHandler(this.webcam, dataDisplay, model);
        qrHandler.start();

        this.add(webcamPanel);
        this.add(dataDisplay);

        webcam.addWebcamListener(new WebcamListener() {
            @Override
            public void webcamOpen(WebcamEvent webcamEvent) {

                qrHandler = new WebcamHandler(webcam, dataDisplay, model);
                qrHandler.start();

            }

            @Override
            public void webcamClosed(WebcamEvent webcamEvent) {

                qrHandler.kill();

            }

            @Override
            public void webcamDisposed(WebcamEvent webcamEvent) {

                qrHandler.kill();

            }

            @Override
            public void webcamImageObtained(WebcamEvent webcamEvent) {

            }
        });

    }

}
