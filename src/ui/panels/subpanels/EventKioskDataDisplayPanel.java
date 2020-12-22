package ui.panels.subpanels;


import javax.swing.*;

public class EventKioskDataDisplayPanel extends JPanel {

    public EventKioskDataDisplayPanel() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JPanel attendeeInfo = new JPanel();
        attendeeInfo.setBorder(BorderFactory.createTitledBorder("Attendee Information"));

        this.add(attendeeInfo);


    }



}
