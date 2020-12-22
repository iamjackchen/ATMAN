package ui.handlers;

import com.github.sarxos.webcam.Webcam;
import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import ui.panels.subpanels.EventKioskDataDisplayPanel;

import java.awt.image.BufferedImage;

public class WebcamHandler extends Thread {

    private String returnData;
    private Webcam webcam;
    private EventKioskDataDisplayPanel dataDisplay;
    public String getReturnData() {return returnData;}

    private boolean running;

    public void kill() {running = false;}

    public WebcamHandler(Webcam webcam, EventKioskDataDisplayPanel dataDisplay) {
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
                        if (result.getText() != null) {
                            returnData = result.getText();
                            dataDisplay.getParent().validate();
                        }
                    }
                }

            }catch (NotFoundException e) {
                //pass
            }

        } while (running);

    }


}
