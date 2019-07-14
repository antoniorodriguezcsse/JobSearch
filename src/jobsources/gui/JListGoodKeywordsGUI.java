package jobsources.gui;

import javax.swing.*;

public class JListGoodKeywordsGUI {

    private JListTools jListTools;
    private JList jListGoodKeywords;

    public JListGoodKeywordsGUI(JList jListGoodKeywords)
    {
        jListTools = new JListTools();
       // goodKeywordsFile = "good-keywords.txt";
        this.jListGoodKeywords = jListGoodKeywords;

        //addFileToJList();
    }
    public void addFileToJList(String fileName) {
        jListTools.addLinesFromFileToJList(fileName, jListGoodKeywords);
    }
}
