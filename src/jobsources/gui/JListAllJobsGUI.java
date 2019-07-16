package jobsources.gui;

import javax.swing.*;
import java.util.ArrayList;

public class JListAllJobsGUI {
    private final JList<String> allJobsJList;
    private DefaultListModel<String> model;

    public JListAllJobsGUI(JList<String> allJobsJList) {
        this.allJobsJList = allJobsJList;
        model = (DefaultListModel<String>) allJobsJList.getModel();
    }

    public void addItem(String item)
    {
        model.addElement(item);
        int lastIndex = allJobsJList.getModel().getSize() - 1;
        if (lastIndex >= 0) {
//            System.out.println("last index: " + lastIndex);
            allJobsJList.ensureIndexIsVisible(lastIndex);
        }
    }

    private void upDateGUI() {
        allJobsJList.updateUI();
    }

    public String getSelectedItem() {
       return allJobsJList.getSelectedValue();
    }

    public void clear()
    {
        model.removeAllElements();
        allJobsJList.ensureIndexIsVisible(0);
    }

    public void addList(ArrayList<String> list)
    {
        model.addAll(list);
    }

}
