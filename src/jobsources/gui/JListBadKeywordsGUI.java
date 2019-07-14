package jobsources.gui;

import javax.swing.*;

public class JListBadKeywordsGUI {

    private JListTools jListTools;
   // private String badKeywordsFile;
    private JList jListBadKeywords;

    public JListBadKeywordsGUI(JList jListBadKeywords)
    {
        jListTools = new JListTools();
      ////  badKeywordsFile = "bad-keywords.txt";
        this.jListBadKeywords = jListBadKeywords;
    }
    public void addFileToJList(String fileName) {
        jListTools.addLinesFromFileToJList(fileName, jListBadKeywords);
    }
}
