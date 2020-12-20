package ui.components;

import data.types.attributes.Enumerator;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class EnumCellEditor extends AbstractCellEditor implements TableCellEditor, ActionListener {

    private Enumerator enumerator;
    private Class enumeratorType;
    private String[] enumeratorOptions;


    public EnumCellEditor(String[] enumeratorOptions, Class enumeratorType) {
        this.enumeratorOptions = enumeratorOptions;
        this.enumeratorType = enumeratorType;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {

        JComboBox<String> enumeratorSelect = (JComboBox<String>) actionEvent.getSource();
        try {
            this.enumerator.setCurrentValue((String) enumeratorSelect.getSelectedItem());
        } catch (Exception e) {e.printStackTrace();}

    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {

        if (value instanceof Enumerator) { this.enumerator = (Enumerator) value; }
        JComboBox<String> enumeratorSelect = new JComboBox<String>();

        for (int i = 0; i < enumeratorOptions.length; i++) {
            enumeratorSelect.addItem(enumeratorOptions[i]);
        }

        enumeratorSelect.setSelectedItem(enumerator.getCurrentValue());
        enumeratorSelect.addActionListener(this);



        return enumeratorSelect;
    }

    @Override
    public Object getCellEditorValue() {
        return this.enumerator;
    }
}
