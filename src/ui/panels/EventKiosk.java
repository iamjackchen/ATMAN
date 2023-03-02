package ui.panels;

import com.github.sarxos.webcam.*;
import data.AttendanceTableModel;
import io.WebcamHandler;
import ui.panels.subpanels.EventKioskDataDisplayPanel;


import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class EventKiosk extends JPanel {

    private Webcam webcam;
    private WebcamHandler qrHandler;
    private AttendanceTableModel model;

    private EventKioskDataDisplayPanel dataDisplay;


    public EventKiosk(AttendanceTableModel model) {
        this.setLayout(new GridLayout(1, 2));
        this.model = model;

        this.dataDisplay = new EventKioskDataDisplayPanel();
        dataDisplay.setBorder(new EmptyBorder(20, 20, 20, 20));

        this.add(dataDisplay);
        this.add(new JPanel()); //dummy component (so that the first refresh call does not remove the dataDisplay)

        this.webcam = null;
        refresh(Webcam.getDefault());




    }

    public void refresh(Webcam newWebcam) {
        if (newWebcam == this.webcam) {return;}

        if (this.webcam != null) {this.webcam.close();}

        this.webcam = newWebcam;
        this.webcam.open(true);

        WebcamPanel webcamPanel = new WebcamPanel(this.webcam);

        webcamPanel.setMirrored(false);
        webcamPanel.setSize(WebcamResolution.VGA.getSize());

        qrHandler = new WebcamHandler(this.webcam, this.dataDisplay, this.model);
        qrHandler.start();

        this.remove(1);
        this.add(webcamPanel);
        this.revalidate();
        this.repaint();

        this.webcam.addWebcamListener(new WebcamListener() {
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
