package jobsources.gui;

import jobsources.CustomExceptions;
import jobsources.SearchThread;
import jobsources.files_that_work_with_job_data.GlassdoorJobData;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class JButtonSearchGUI {

    private JButton jButtonSearch;
    private JComboBox jComboBoxPredefinedSearch;
    private Timer timer;
    private JList<String> allJobsJList;
    private ArrayList<String> allJobLinks = new ArrayList<>();
    private LinkedHashMap<String, GlassdoorJobData> allJobsFromFile;
    //  private GlassDoor glassDoor;
   // private JobDataObjectsFile jobDataObjectsFile;
    private JComboBoxGUI jComboBoxNumberOfJobsToFind;
    private JobOptionsDialog jOptionsDialog;

    JButtonSearchGUI(JButton jButtonSearch, JList allJobsJList, JComboBoxGUI jComboBoxNumberOfJobsToFind) {
        this.jButtonSearch = jButtonSearch;
        setActionListener();
        createTimer();
        this.allJobsJList = allJobsJList;
  //      jobDataObjectsFile = new JobDataObjectsFile();
  //      allJobsFromFile = jobDataObjectsFile.readObjectsFromFile();
        this.jComboBoxNumberOfJobsToFind = jComboBoxNumberOfJobsToFind;
    }


    private void setActionListener() {
        jButtonSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                jOptionsDialog = new JobOptionsDialog();
//                jOptionsDialog.pack();
//                jOptionsDialog.setVisible(true);

                if (isButtonTextEqualToSearch()) {
                    timer.start();

                    Thread searchThread = createThread(comboBoxSearchTerm());
                    searchThread.start();

                    jButtonSearch.setEnabled(false);
                    jButtonSearch.setText("Stop");
                } else if (jButtonSearch.getText().equals("Stop")) {
                    jButtonSearch.setText("Search");
                    System.out.println("Stop");
                    timer.stop();
                }
            }
        });

    }

    private Thread createThread(String searchTerm) {
        SearchThread searchThread = null;
        try {
            searchThread = new SearchThread(jButtonSearch, allJobsJList, searchTerm, jComboBoxNumberOfJobsToFind);
        } catch (IOException e1) {
            e1.printStackTrace();
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
        return new Thread(searchThread, "T1");
    }

    private String comboBoxSearchTerm() {
        return (String) jComboBoxPredefinedSearch.getSelectedItem();
    }

    private boolean isButtonTextEqualToSearch() {
        return jButtonSearch.getText().equals("Search");
    }

    public void setJcomboboxPredefinedSearch(JComboBoxGUI jComboBoxGUI) {
        this.jComboBoxPredefinedSearch = jComboBoxGUI.getThisComBoBox();
    }

    //Create a timer.
    private void createTimer() {
        timer = new Timer(300, new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                DefaultListModel<String> model;
                model = (DefaultListModel<String>) allJobsJList.getModel();
                allJobsJList.setModel(model);
                allJobsJList.updateUI();
            }
        });
    }

    private LinkedHashMap<String, GlassdoorJobData> addUnseenJobsToMap(LinkedHashMap<String, GlassdoorJobData> map) throws CustomExceptions {
        ArrayList<String> newLinks = new ArrayList<>();

        for (String jobLink : allJobLinks) {
            if (map.containsKey(jobLink)) {
                continue;
            }
            newLinks.add(jobLink);
        }

        GlassdoorJobData glassdoorJobData = null;
        for (String link : newLinks) {
            glassdoorJobData = new GlassdoorJobData(link);
            map.put(link, glassdoorJobData);
        }

        return map;
    }

//    private ArrayList<String> getALlLinksFromMainSite(String mainLink) {
//        try {
//            glassDoor = new GlassDoor(mainLink);
//        } catch (IOException | InterruptedException e) {
//            e.printStackTrace();
//        }
//        return glassDoor.getAllJobLinks();
//    }

}
