package ui.components.menus;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamDiscoveryEvent;
import com.github.sarxos.webcam.WebcamDiscoveryListener;
import ui.panels.EventKiosk;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Settings extends JMenu {

    private final String[] themeNames = {"Vanilla Dark", "Vanilla Light", "Darcula", "IntelliJ"};
    private final LookAndFeel[] themesContainer = {new FlatDarkLaf(), new FlatLightLaf(), new FlatDarculaLaf(), new FlatIntelliJLaf()};


    public Settings(ArrayList<JPanel> panels) {
        super("Settings");

        JMenu themes= new JMenu("Themes  ");
        this.add(themes);

        ButtonGroup themeOptions = new ButtonGroup();

        for (int i = 0; i < themesContainer.length; i++) {
            JMenuItem x = new JRadioButtonMenuItem(themeNames[i]);
            int I = i;
            x.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {

                    try {
                        UIManager.setLookAndFeel(themesContainer[I]);
                    } catch (UnsupportedLookAndFeelException e) {
                        e.printStackTrace();
                    }

                    for (java.awt.Window window : JFrame.getWindows()) SwingUtilities.updateComponentTreeUI(window);

                }

            });

            if (themesContainer[i] instanceof FlatDarkLaf) x.setSelected(true); //sets Default (currently FlatDarkLaf) to selected

            themeOptions.add(x);
            themes.add(x);

        }

        ButtonGroup webcamOptions = new ButtonGroup();

        EventKiosk eventKiosk = (EventKiosk) panels.get(0);

        JMenu webcamSelect = new JMenu("Select Webcam");
        for (Webcam w : Webcam.getWebcams()) {
            JMenuItem x = new JRadioButtonMenuItem(w.getName());
            x.setName(w.getName());
            x.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    eventKiosk.refresh(w);
                }
            });

            if (w == Webcam.getDefault()) x.setSelected(true);

            webcamOptions.add(x);
            webcamSelect.add(x);

        }

        Webcam.addDiscoveryListener(new WebcamDiscoveryListener() {
            @Override
            public void webcamFound(WebcamDiscoveryEvent webcamDiscoveryEvent) {

                Webcam newWebcam = webcamDiscoveryEvent.getWebcam();
                String newWebcamName = newWebcam.getName();
                JMenuItem x = new JRadioButtonMenuItem(newWebcamName);
                x.setName(newWebcamName);
                x.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        eventKiosk.refresh(newWebcam);
                    }
                });

                webcamOptions.add(x);
                webcamSelect.add(x);
            }

            @Override
            public void webcamGone(WebcamDiscoveryEvent webcamDiscoveryEvent) {

                //Note: JMenuRadioButtonMenuItem is a subclass of JMenuItem, which is a subclass of AbstractButton
                for (Component component : webcamSelect.getMenuComponents()) {
                    if ((component instanceof AbstractButton) && component.getName().equals(webcamDiscoveryEvent.getWebcam().getName())) {
                        webcamSelect.remove(component);
                        webcamOptions.remove((AbstractButton) component);
                    }
                }

            }
        });

        this.add(webcamSelect);

    }

}
