package ui.components;

import data.types.attributes.Enumerator;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class EnumCellRenderer extends DefaultTableCellRenderer {

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

        if (value instanceof Enumerator) {
            Enumerator enumerator = (Enumerator) value;
            setText(enumerator.getCurrentValue());
        }

        return this;

    }

}
