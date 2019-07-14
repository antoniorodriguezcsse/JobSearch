package jobsources.gui;

import jobsources.read_write_to_files.FileRead;

import javax.swing.*;
import java.util.ArrayList;

public class JListTools {
   // private JList<String> jList;

    public void addListOfItemsToJList(ArrayList<String> listOfItems, JList<String> jList)
    {
        DefaultListModel<String> model = new DefaultListModel<>();

        model.addAll(listOfItems);
        jList.setModel(model);
    }

    public void addLinesFromFileToJList(String fileName, JList<String> jList)
    {
        DefaultListModel<String> model = new DefaultListModel<>();

        FileRead fileRead = new FileRead();
        ArrayList<String> linesFromFile = new ArrayList<>();
        linesFromFile = fileRead.getLinesFromFile(fileName);

        model.addAll(linesFromFile);
        jList.setModel(model);
    }
}
