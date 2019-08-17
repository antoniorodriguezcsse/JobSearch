package jobsources.read_write_to_files;

import jobsources.SortbyJobID;
import jobsources.files_that_work_with_job_data.JobData;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.TreeSet;

class WriteObjectsToFileTest {

    @Test
    void writeObjectToFile() {
    }

    @Test
    void readWriteObjectsFileTest() {
        File file = new File("objectTestFile.bin");
        file.delete();
        TreeSet<JobData> jobDataTreeSet = new TreeSet<>(new SortbyJobID());
        jobDataTreeSet.add(new JobData("websiteTest/companySite.htm"));
        jobDataTreeSet.add(new JobData("websiteTest/dontShow0.htm"));
        jobDataTreeSet.add(new JobData("websiteTest/easyApply.htm"));

        WriteObjectsToFile writeObjectsToFile = new WriteObjectsToFile();
        writeObjectsToFile.writeObjects(jobDataTreeSet, "objectTestFile.bin");

        ReadObjectsFromFile readObjectsFromFile = new ReadObjectsFromFile();
        TreeSet<JobData> objectsFromFile = new TreeSet<>(new SortbyJobID());
        objectsFromFile = readObjectsFromFile.readObjects("objectTestFile.bin");

        assert(jobDataTreeSet.equals(objectsFromFile));

        jobDataTreeSet.add(new JobData("websiteTest/goDaddy.htm"));
        assert(!jobDataTreeSet.equals(objectsFromFile));

    }
}