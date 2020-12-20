import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatDarkLaf;
import ui.UIApplication;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {

            try {
                UIManager.setLookAndFeel(new FlatDarkLaf());
            } catch (Exception e) {
                e.printStackTrace();
            }

            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    new UIApplication();
                }
            });

    }


}
