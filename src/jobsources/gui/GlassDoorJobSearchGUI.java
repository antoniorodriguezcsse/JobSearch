package jobsources.gui;

import jobsources.files_that_work_with_job_data.GlassdoorJobData;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GlassDoorJobSearchGUI extends JFrame {
    private JPanel panel1;
    private JList<String> jListDontShowKeywords;
    private JList<String> jListGoodKeywords;
    private JList<String> jListBadKeywords;
    private JLabel labelDontShowKeyWords;
    private JLabel labelBadWords;
    private JLabel labelGoodWords;
    private JPanel jPanelForLists;
    private JTextField jTextFieldSearchLink;
    private JButton jButtonSearch;
    private JTabbedPane jTabbedPaneForResults;
    private JLabel labelHowManyJobsToFind;
    private JComboBox jComboBoxNumberOfJobsToFind;
    private JScrollPane scrollPaneBadKeywords;
    private JScrollPane scrollPaneDontShowKeywords;
    private JScrollPane scrollPaneGoodKeywords;
    private JPanel jPanelForGoodWords;
    private JPanel jPanelForBadWords;
    private JPanel jPanelForDontShowWords;
    private JPanel tabbedJpanelAllJobs;
    private JRadioButton jRadioButtonEnterLink;
    private JRadioButton jRadioButtonPredefined;
    public JComboBox jComboBoxPredefinedSearch;
    private JPanel JPanelTop;
    private JList jListAllJobs;
    private JScrollPane jScrollPaneForALlJobs;
    private JScrollPane menuScrollPane;
    private JListTools jListTools;
    // private JRadioButtonTools radioButtonTools;
    private Timer timer;

    public GlassDoorJobSearchGUI() {
        //$$$setupUI$$$();
        JListGoodKeywordsGUI jListGoodKeywordsGUI = new JListGoodKeywordsGUI(jListGoodKeywords);
        jListGoodKeywordsGUI.addFileToJList("good-keywords.txt");

        JListBadKeywordsGUI jListBadKeywordsGUI = new JListBadKeywordsGUI(jListBadKeywords);
        jListBadKeywordsGUI.addFileToJList("bad-keywords.txt");

        JListDontShowKeywordsGUI jListDontShowKeywordsGUI = new JListDontShowKeywordsGUI(jListDontShowKeywords);
        jListDontShowKeywordsGUI.addFileToJList("dont-show-keywords.txt");

        JRadioButtonEnterMainLinkGUI jRadioButtonEnterMainLinkGUI = new JRadioButtonEnterMainLinkGUI(jRadioButtonEnterLink);
        jRadioButtonEnterMainLinkGUI.setJcomboboxPredefinedSearch(jComboBoxPredefinedSearch);
        jRadioButtonEnterMainLinkGUI.setJtextFieldSearchLink(jTextFieldSearchLink);

        JRadioButtonPredefinedGUI jRadioButtonPredefinedGUI = new JRadioButtonPredefinedGUI(jRadioButtonPredefined);
        jRadioButtonPredefinedGUI.setJcomboboxPredefinedSearch(jComboBoxPredefinedSearch);
        jRadioButtonPredefinedGUI.setJtextFieldSearchLink(jTextFieldSearchLink);

        JTextfFieldSearchLinkGUI jTextfFieldSearchLinkGUI = new JTextfFieldSearchLinkGUI(jTextFieldSearchLink);

        jRadioButtonPredefinedGUI.select();
        jTextfFieldSearchLinkGUI.disable();
///////////////////////////////////////////////////////
        JButtonSearchGUI jButtonSearchGUI = new JButtonSearchGUI(jButtonSearch, jListAllJobs, new JComboBoxGUI(jComboBoxNumberOfJobsToFind));

        jButtonSearchGUI.setJcomboboxPredefinedSearch(new JComboBoxGUI(jComboBoxPredefinedSearch));

        readJobsFromFileToTabbedView();


    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("GlassDoorJobSearchGUI");
        frame.setContentPane(new GlassDoorJobSearchGUI().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(1200, 600);
        frame.setVisible(true);

    }

    private void readJobsFromFileToTabbedView() {
      //  JobDataObjectsFile jobDataObjectsFile = new JobDataObjectsFile();
        ArrayList<GlassdoorJobData> glassdoorJobDatafromFile = new ArrayList<>();
        /////////////////////////////////////////////// jobDatafromFile.addAll(jobDataObjectsFile.readObjectsFromFile());

        DefaultListModel<String> model = new DefaultListModel<String>();

        for (int i = 0; i < glassdoorJobDatafromFile.size(); i++) {
            model.add(i, glassdoorJobDatafromFile.get(i).getJobTitle());
        }
        //  model.addAll(wordsFromFile);
        jListAllJobs.setModel(model);
    }

    private void createUIComponents() {
        jListGoodKeywords = new JList<String>();
        jListDontShowKeywords = new JList<String>();
        jListBadKeywords = new JList<String>();

        scrollPaneBadKeywords = new JScrollPane();
        scrollPaneBadKeywords.setMinimumSize(new Dimension(100, 100));
        scrollPaneDontShowKeywords = new JScrollPane();
        scrollPaneDontShowKeywords.setMinimumSize(new Dimension(100, 100));
        scrollPaneGoodKeywords = new JScrollPane();
        scrollPaneGoodKeywords.setMinimumSize(new Dimension(100, 100));

        jComboBoxPredefinedSearch = new JComboBox();
        jComboBoxNumberOfJobsToFind = new JComboBox();

        jRadioButtonEnterLink = new JRadioButton();
        jRadioButtonPredefined = new JRadioButton();

        jTextFieldSearchLink = new JTextField();

        jListAllJobs = new JList<String>();

    }

}
