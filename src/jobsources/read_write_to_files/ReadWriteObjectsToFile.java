package jobsources.read_write_to_files;

import jobsources.files_that_work_with_job_data.JobData;

import java.io.*;
import java.util.ArrayList;
import java.util.TreeSet;

public class ReadWriteObjectsToFile implements Serializable {

    private FileOutputStream fileOutputStream;

    public void writeObjectToFile(JobData jobData) {
        System.out.println("Hello");
        try {
            fileOutputStream = new FileOutputStream(new File("jobObjects.txt"));
            System.out.println("fileStream: " + fileOutputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(jobData);
            objectOutputStream.close();
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Object> readObjectsFromFile() {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream("jobObjects.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ArrayList<Object> objectsList = new ArrayList<Object>();
        boolean cont = true;
        try{
            ObjectInputStream input = new ObjectInputStream(fis);
            while(cont){
                Object obj = input.readObject();
                if(obj != null)
                    objectsList.add(obj);
                else
                    cont = false;
            }
        }catch(Exception e){
            //System.out.println(e.printStackTrace());
        }
        return objectsList;
    }


}
