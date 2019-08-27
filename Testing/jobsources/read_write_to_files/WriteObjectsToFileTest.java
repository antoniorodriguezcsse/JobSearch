//package jobsources.read_write_to_files;
//
//import jobsources.CustomExceptions;
//import jobsources.SortbyJobID;
//import jobsources.files_that_work_with_job_data.GlassdoorJobData;
//import org.junit.jupiter.api.Test;
//
//import java.io.File;
//import java.util.TreeSet;
//
//class WriteObjectsToFileTest {
//
//    @Test
//    void writeObjectToFile() {
//    }
//
//    @Test
//    void readWriteObjectsFileTest() throws CustomExceptions {
//        File file = new File("objectTestFile.bin");
//        file.delete();
//        TreeSet<GlassdoorJobData> glassdoorJobDataTreeSet = new TreeSet<>(new SortbyJobID());
//        glassdoorJobDataTreeSet.add(new GlassdoorJobData("websiteTest/companySite.htm"));
//        glassdoorJobDataTreeSet.add(new GlassdoorJobData("websiteTest/dontShow0.htm"));
//        glassdoorJobDataTreeSet.add(new GlassdoorJobData("websiteTest/easyApply.htm"));
//
//        WriteObjectsToFile writeObjectsToFile = new WriteObjectsToFile();
//        writeObjectsToFile.writeObjects(glassdoorJobDataTreeSet, "objectTestFile.bin");
//
//        ReadObjectsFromFile readObjectsFromFile = new ReadObjectsFromFile();
//        TreeSet<GlassdoorJobData> objectsFromFile = new TreeSet<>(new SortbyJobID());
//        objectsFromFile = readObjectsFromFile.readObjects("objectTestFile.bin");
//
//        assert(glassdoorJobDataTreeSet.equals(objectsFromFile));
//
//        glassdoorJobDataTreeSet.add(new GlassdoorJobData("websiteTest/goDaddy.htm"));
//        assert(!glassdoorJobDataTreeSet.equals(objectsFromFile));
//
//    }
//}