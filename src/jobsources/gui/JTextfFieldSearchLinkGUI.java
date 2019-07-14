package jobsources.gui;

import javax.swing.*;

public class JTextfFieldSearchLinkGUI {

    JTextField jTextFieldSearchLink;

    public JTextfFieldSearchLinkGUI(JTextField jTextFieldSearchLink)
    {
        this.jTextFieldSearchLink = jTextFieldSearchLink;
    }


    public void disable() {
        jTextFieldSearchLink.setEnabled(false);
    }
}
