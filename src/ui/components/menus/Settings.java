package ui.components.menus;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Settings extends JMenu {

    private final String[] themeNames = {"Vanilla Dark", "Vanilla Light", "Darcula", "IntelliJ"};
    private final LookAndFeel[] themesContainer = {new FlatDarkLaf(), new FlatLightLaf(), new FlatDarculaLaf(), new FlatIntelliJLaf()};


    public Settings() {
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

            if (themesContainer[i] instanceof FlatDarkLaf) x.setSelected(true);

            themeOptions.add(x);
            themes.add(x);

        }

        JMenu webcamSelect = new JMenu("Select Webcam");









    }

}
