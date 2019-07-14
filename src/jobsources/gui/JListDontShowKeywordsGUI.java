package jobsources.gui;

import javax.swing.*;

public class JListDontShowKeywordsGUI {

    private JListTools jListTools;
    private JList<String> jListDontShowKeywords;

    public JListDontShowKeywordsGUI(JList<String> jListDontShowKeywords)
    {
        jListTools = new JListTools();
        this.jListDontShowKeywords = jListDontShowKeywords;
    }
    public void addFileToJList(String fileName) {
        jListTools.addLinesFromFileToJList(fileName, jListDontShowKeywords);
    }
}
