//package jobsources.read_write_to_files;
//
//import jobsources.files_that_work_with_job_data.GlassdoorJobData;
//
//import java.io.*;
//import java.util.TreeSet;
//
//public class WriteObjectsToFile implements Serializable {
//
//    public void writeObjects(TreeSet<GlassdoorJobData> listOfGlassdoorJobData) {
//        writeToFile(listOfGlassdoorJobData, "jobObjects.bin");
//    }
//
//    public void writeObjects(TreeSet<GlassdoorJobData> listOfGlassdoorJobData, String fileName) {
//        writeToFile(listOfGlassdoorJobData, fileName);
//    }
//
//    private void writeToFile(TreeSet<GlassdoorJobData> listOfGlassdoorJobData, String fileName)
//    {
//        try {
//            FileOutputStream fileOutputStream = new FileOutputStream(new File(fileName));
//            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
//            objectOutputStream.writeObject(listOfGlassdoorJobData);
//            objectOutputStream.close();
//            fileOutputStream.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//}
