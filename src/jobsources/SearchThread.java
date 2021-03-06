package jobsources;

//import jobsources.files_that_work_with_job_data.JListData;
import jobsources.files_that_work_with_job_data.JobData;
import jobsources.gui.JComboBoxGUI;
import jobsources.read_write_to_files.FileWrite;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class SearchThread implements Runnable {

    //   private volatile Boolean exit = false;
    private JButton searchButton;
    private LinkedHashMap<String, JobData> allJobsFromFile;
    private ArrayList<String> allJobLinks = new ArrayList<>();
  //  private JobDataObjectsFile jobDataObjectsFile;
    private JList<String> allJobsJList; // = new JList();
    private FileWrite fileWrite;
    private String searchTerm;
    private JComboBoxGUI jComboBoxNumberOfJobsToFind;


    public SearchThread(JButton searchButton, JList allJobsJlist, String searchTerm, JComboBoxGUI jComboBoxNumberOfJobsToFind) throws IOException, InterruptedException {
        this.searchButton = searchButton;

    //    jobDataObjectsFile = new JobDataObjectsFile();
    //    allJobsFromFile = jobDataObjectsFile.readObjectsFromFile();
        // allJobsJlist = new JList();
        this.allJobsJList = allJobsJlist;
        //fileWrite = new FileWrite("job-links.txt");
        this.searchTerm = searchTerm;
        this.jComboBoxNumberOfJobsToFind = jComboBoxNumberOfJobsToFind;
    }

    @Override
    public void run() {
        ArrayList<JobData> jobDataArrayList = new ArrayList<>();

      //  JListData JListData = new JListData(searchTerm, allJobsJList, jComboBoxNumberOfJobsToFind);
//       // SWENewGradAbstractJobStrategy(allJobsJList, jComboBoxNumberOfJobsToFind);
        if(searchTerm.equals("Entry level software engineer")) {
            String mainSite = "https://www.glassdoor.com/Job/concord-software-engineer-entry-level-jobs-SRCH_IL.0,7_IC1147340_KE8,37.htm?radius=50";
         //   GlassdoorFacade glassdoorFacade = new GlassdoorFacade(mainSite);

//            Context context = new Context(new EntrySWEAbstractJobStrategy(allJobsJList, jComboBoxNumberOfJobsToFind));
//            jobDataArrayList = context.executeStrategy();
        }
//
        else if(searchTerm.equals("Junior software engineer"))
        {
          //  System.out.println("Junior selected");
//            Context context = new Context(new JuniorSWEAbstractJobStrategy(allJobsJList));
//            jobDataArrayList = context.executeStrategy();
        }
//


        LinkedHashMap<String, JobData> mapOfAllJobs = new LinkedHashMap<>();
        mapOfAllJobs = addUnseenJobsToMap(allJobsFromFile);
     //   jobDataObjectsFile.writeObjectsToFile(mapOfAllJobs);

//        String title = "";
//        for (String s : mapOfAllJobs.keySet()) {
//            title = mapOfAllJobs.get(s).getJobTitle();
//            model.addElement(title);
//
//          //  allJobsJList.setModel(model);
//            // allJobsJList.repaint();
//       //     allJobsJList.updateUI();
//        }

        searchButton.setEnabled(true);
        searchButton.setText("Search");
    }

    private LinkedHashMap<String, JobData> addUnseenJobsToMap(LinkedHashMap<String, JobData> map) {
        ArrayList<String> newLinks = new ArrayList<>();

        for (String jobLink : allJobLinks) {
            if (map.containsKey(jobLink)) {
                continue;
            }
            newLinks.add(jobLink);
        }

        JobData jobData = null;
        for (String link : newLinks) {
            jobData = new JobData(link);
            map.put(link, jobData);
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
