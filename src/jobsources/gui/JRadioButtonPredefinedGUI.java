package jobsources.gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JRadioButtonPredefinedGUI {
    private JRadioButton jRadioButtonPredefined;
    private JComboBox jComboBoxPredefinedSearch;
    private JTextField jTextFieldSearchLink;

    public JRadioButtonPredefinedGUI(JRadioButton jRadioButtonPredefined)
    {
        this.jRadioButtonPredefined = jRadioButtonPredefined;
        setActionListener();
    }

    public void select() {
        jRadioButtonPredefined.setSelected(true);
    }

    private void setActionListener(){
        jRadioButtonPredefined.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (jRadioButtonPredefined.isSelected()) {
                    jComboBoxPredefinedSearch.setEnabled(true);
                    jTextFieldSearchLink.setEnabled(false);
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
