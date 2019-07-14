package jobsources.gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JComboBoxGUI {

    private JComboBox jComboBox;
    private String itemSelected;

    public JComboBoxGUI(JComboBox jComboBox) {
        this.jComboBox = jComboBox;
        setActionListener();
    }

    private void setActionListener() {
        jComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                itemSelected = (String) jComboBox.getSelectedItem();
            }
        });
    }

    public JComboBox getThisComBoBox()
    {
        return this.jComboBox;
    }

    public String getSelectedItem()
    {
        return (String) jComboBox.getSelectedItem();
    }

}
