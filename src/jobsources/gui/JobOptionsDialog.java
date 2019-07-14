package jobsources.gui;

import javax.swing.*;
import java.awt.event.*;

public class JobOptionsDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonApplyandDontShowAgain;
    private JButton buttonCancel;
    private JButton buttonDontShowAgain;

    public JobOptionsDialog() {
        setContentPane(contentPane);
        setModal(true);

        getRootPane().setDefaultButton(buttonApplyandDontShowAgain);

        buttonApplyandDontShowAgain.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onApplyAndHide();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onApplyAndHide() {
        // add your code here
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public static void main(String[] args) {
        JobOptionsDialog dialog = new JobOptionsDialog();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
