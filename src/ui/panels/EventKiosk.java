package ui.panels;

import com.github.sarxos.webcam.*;
import com.github.sarxos.webcam.util.jh.JHFlipFilter;
import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;


import javax.swing.*;
import java.awt.*;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.util.Arrays;

public class EventKiosk extends JPanel {

    Webcam webcam;
    WebcamPanel webcamPanel;

    public EventKiosk() {
        this.setLayout(new GridLayout(1, 2));
        webcam = Webcam.getDefault();

        webcam.setCustomViewSizes(WebcamResolution.VGA.getSize());
        webcam.setViewSize(WebcamResolution.VGA.getSize());
        webcamPanel = new WebcamPanel(webcam);
        webcamPanel.setMirrored(false);
        webcamPanel.setSize(WebcamResolution.VGA.getSize());


        this.add(webcamPanel);
        this.add(new JButton());

        webcam.addWebcamListener(new WebcamListener() {
            @Override
            public void webcamOpen(WebcamEvent webcamEvent) {

            }

            @Override
            public void webcamClosed(WebcamEvent webcamEvent) {

            }

            @Override
            public void webcamDisposed(WebcamEvent webcamEvent) {

            }

            @Override
            public void webcamImageObtained(WebcamEvent webcamEvent) {

            }
        });

    }

}
