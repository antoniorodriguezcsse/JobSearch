package jobsources.gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JRadioButtonEnterMainLinkGUI {
    private JRadioButton jRadioButtonEnterMainLink;
    private JComboBox jComboBoxPredefinedSearch;
    private JTextField jTextFieldSearchLink;

    public JRadioButtonEnterMainLinkGUI(JRadioButton jRadioButtonEnterMainLink)
    {
        this.jRadioButtonEnterMainLink = jRadioButtonEnterMainLink;
        setActionListener();
    }

    public void select() {
        jRadioButtonEnterMainLink.setSelected(true);
    }

    private void setActionListener(){
        jRadioButtonEnterMainLink.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (jRadioButtonEnterMainLink.isSelected()) {
                    jComboBoxPredefinedSearch.setEnabled(false);
                    jTextFieldSearchLink.setEnabled(true);
                }
            }
        });
    }


    public void setJcomboboxPredefinedSearch(JComboBox jComboBoxPredefinedSearch)
    {
        this.jComboBoxPredefinedSearch = jComboBoxPredefinedSearch;
    }

    public void setJtextFieldSearchLink(JTextField jTextFieldSearchLink)
    {
        this.jTextFieldSearchLink = jTextFieldSearchLink;
    }
}
