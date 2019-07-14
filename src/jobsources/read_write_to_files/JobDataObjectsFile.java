package jobsources.read_write_to_files;

import jobsources.SortbyRank;
import jobsources.files_that_work_with_job_data.JobData;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;

public class JobDataObjectsFile {

    private String fileName = "";
    public JobDataObjectsFile() {
        fileName = "Objects.bin";
    }


    public void writeObjectsToFile(LinkedHashMap<String, JobData> map) {

        ArrayList<JobData> objects = new ArrayList<>();
        objects.addAll(map.values());

//        for (JobData jd : objects) {
//            noDuplicatesMap.put(jd.getJobLink(), jd);
//        }
//
//        objects.clear();
//        objects.addAll(noDuplicatesMap.values());

        File f = new File(System.getProperty("user.dir") + "\\" + fileName);
        FileOutputStream fileOutputStream = null;
        ObjectOutputStream objectOutputStream = null;
        try {
            fileOutputStream  = new FileOutputStream(f);
            objectOutputStream  = new ObjectOutputStream(fileOutputStream );
            objectOutputStream .writeObject(objects);
            fileOutputStream .close();
            objectOutputStream .close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public LinkedHashMap<String, JobData> readObjectsFromFile() {
        // File f = new File(System.getProperty("user.dir") + "\\" + fileName);
        FileInputStream fileInputStream = null;
        ArrayList<JobData> jobDataFromFile = new ArrayList<>();
        try {
            fileInputStream = new FileInputStream(System.getProperty("user.dir") + "\\" + fileName);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            jobDataFromFile = (ArrayList<JobData>) objectInputStream.readObject();
            fileInputStream.close();
            objectInputStream.close();
        } catch (IOException | ClassNotFoundException | NullPointerException e) {
            // e.printStackTrace();
        }

        Collections.sort(jobDataFromFile, new SortbyRank());
        LinkedHashMap<String, JobData> jobDataMap = new LinkedHashMap<>();
        for (JobData jd : jobDataFromFile) {
            jobDataMap.put(jd.getJobLink(), jd);
        }

        return jobDataMap;
    }

    public ArrayList<String> getJobLinksFromFile() {
        FileInputStream fis = null;
        ArrayList<JobData> jobDataFromFile = new ArrayList<>();
        try {
            fis = new FileInputStream(System.getProperty("user.dir") + "\\" + fileName);
            ObjectInputStream ois = new ObjectInputStream(fis);
            jobDataFromFile = (ArrayList<JobData>) ois.readObject();
            fis.close();
            ois.close();
        } catch (IOException | ClassNotFoundException | NullPointerException e) {
            // e.printStackTrace();
        }

        Collections.sort(jobDataFromFile, new SortbyRank());
        ArrayList<String> linksFromObjects = new ArrayList<>();
        for (JobData jd : jobDataFromFile) {
            linksFromObjects.add(jd.getJobLink());
        }

        return linksFromObjects;
    }

}
