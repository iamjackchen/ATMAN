package ui.containers;

import com.formdev.flatlaf.icons.FlatTabbedPaneCloseIcon;
import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;


public class CloseableJTabbedPane extends JTabbedPane {

    public CloseableJTabbedPane() {
        super();
        this.getModel().addChangeListener(new selectedTabListener(this));

    }

    @Override
    public void addTab(String title, Icon icon, Component component, String tip) {
        super.addTab(title, icon, component, tip);
        int count = this.getTabCount() - 1;
        setTabComponentAt(count, new CloseButtonTab(component, title, icon));
    }

    @Override
    public void addTab(String title, Icon icon, Component component) {
        addTab(title, icon, component, null);
    }

    @Override
    public void addTab(String title, Component component) {
        addTab(title, null, component);
    }

    public void addTabNoExit(String title, Icon icon, Component component, String tip) {
        super.addTab(title, icon, component, tip);
    }

    public void addTabNoExit(String title, Icon icon, Component component) {
        addTabNoExit(title, icon, component, null);
    }

    public void addTabNoExit(String title, Component component) {
        addTabNoExit(title, null, component);
    }

    public class CloseButtonTab extends JPanel {
        private Component tab;

        public CloseButtonTab(final Component tab, String title, Icon icon) {
            this.tab = tab;
            setOpaque(false);
            FlowLayout flowLayout = new FlowLayout(FlowLayout.CENTER, 3, 3);
            setLayout(flowLayout);

            JLabel jLabel = new JLabel(title + "  ");
            jLabel.setIcon(icon);
            add(jLabel);

            JButton button = new JButton(new FlatTabbedPaneCloseIcon());
            button.addMouseListener(new CloseListener(tab));
            add(button);

        }
    }

    public class CloseListener implements MouseListener
    {
        private Component tab;

        public CloseListener(Component tab){
            this.tab=tab;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            if(e.getSource() instanceof JButton){
                JButton clickedButton = (JButton) e.getSource();
                JTabbedPane tabbedPane = (JTabbedPane) clickedButton.getParent().getParent().getParent();

                //Checks for any webcam panels open in the component in the closed tab. Necessary, otherwise webcams will remain in use post-tab-close
                if (tab instanceof JPanel) {
                    Component[] components = ((JPanel)tab).getComponents();
                    for (Component component : components)
                        if (component instanceof WebcamPanel)
                            ((WebcamPanel) component).stop();

                }

                tabbedPane.remove(tab);
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {}

        @Override
        public void mouseReleased(MouseEvent e) {}

        @Override
        public void mouseEntered(MouseEvent e) {
            if(e.getSource() instanceof JButton){
                JButton clickedButton = (JButton) e.getSource();
            }
        }

        @Override
        public void mouseExited(MouseEvent e) {
            if(e.getSource() instanceof JButton){
                JButton clickedButton = (JButton) e.getSource();
            }
        }
    }


    public class selectedTabListener implements ChangeListener {

        private CloseableJTabbedPane pane;

        public selectedTabListener(CloseableJTabbedPane pane) {
            this.pane = pane;
        }

        @Override
        public void stateChanged(ChangeEvent e) {

            //Security feature: enables webcams only on the currently open tab. Prevents webcams from being on when its UI panel is defocused
            //Loops through all components in the tabbed pane and only enables webcams
            //I feel like this is a bit inefficient though, but I have yet to find a better implementation
            Component[] tabs = (pane.getComponents());
            for (Component tab : tabs) {
                if (tab != pane.getSelectedComponent()) {
                    if (tab instanceof JPanel) {
                        Component[] components = ((JPanel) tab).getComponents();
                        for (Component component : components)
                            if (component instanceof WebcamPanel)
                                ((WebcamPanel) component).stop();
                    }
                } else {
                    if (tab instanceof JPanel) {
                        Component[] components = ((JPanel) tab).getComponents();
                        for (Component component : components) {
                            if (component instanceof WebcamPanel)
                                ((WebcamPanel) component).start();
                        }
                    }
                }
            }

        }
    }

}