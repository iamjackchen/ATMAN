package ui.panels;

import com.github.sarxos.webcam.*;
import ui.handlers.WebcamHandler;
import ui.panels.subpanels.EventKioskDataDisplayPanel;


import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class EventKiosk extends JPanel {

    Webcam webcam;
    WebcamPanel webcamPanel;
    WebcamHandler qrHandler;

    public EventKiosk(boolean firstTab) {
        this.setLayout(new GridLayout(1, 2));
        Webcam webcam = Webcam.getDefault();


        webcam.open(true);
        webcamPanel = new WebcamPanel(webcam);

        if (!firstTab) webcamPanel.stop();

        webcamPanel.setMirrored(false);
        webcamPanel.setSize(WebcamResolution.VGA.getSize());

        EventKioskDataDisplayPanel dataDisplay = new EventKioskDataDisplayPanel();
        dataDisplay.setBorder(new EmptyBorder(20, 20, 20, 20));

        qrHandler = new WebcamHandler(this.webcam, dataDisplay);
        qrHandler.start();

        this.add(webcamPanel);
        this.add(dataDisplay);

        webcam.addWebcamListener(new WebcamListener() {
            @Override
            public void webcamOpen(WebcamEvent webcamEvent) {

                qrHandler = new WebcamHandler(webcam, dataDisplay);
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
