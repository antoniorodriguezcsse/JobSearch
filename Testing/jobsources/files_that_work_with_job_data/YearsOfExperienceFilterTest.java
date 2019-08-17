package jobsources.files_that_work_with_job_data;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class YearsOfExperienceFilterTest {

    @Test
    void showJobFilter() {
        YearsOfExperienceFilter yearsOfExperienceFilter = new YearsOfExperienceFilter();
        String s = "3 years experience.";
        assertTrue(yearsOfExperienceFilter.showJobFilter(s, 3));

        s = "3 YEARS EXPERIENCE.";
        assertTrue(yearsOfExperienceFilter.showJobFilter(s, 3));

        s = "2 years experience.";
        assertTrue(yearsOfExperienceFilter.showJobFilter(s, 3));

        s = "2+ years experience.";
        assertTrue(yearsOfExperienceFilter.showJobFilter(s, 3));

        s = "10+ years experience.";
        assertTrue(yearsOfExperienceFilter.showJobFilter(s, 10));

        s = "years experience.";
        assertFalse(yearsOfExperienceFilter.showJobFilter(s, 2));

        s = "2 years ";
        assertFalse(yearsOfExperienceFilter.showJobFilter(s, 2));

        s = "years experience ";
        assertFalse(yearsOfExperienceFilter.showJobFilter(s, 2));

        s = "2years experience ";
        assertTrue(yearsOfExperienceFilter.showJobFilter(s, 2));

        s = "2+years experience ";
        assertTrue(yearsOfExperienceFilter.showJobFilter(s, 2));

        s = "one years experience ";
        assertTrue(yearsOfExperienceFilter.showJobFilter(s, 2));

        s = "three years experience ";
        assertFalse(yearsOfExperienceFilter.showJobFilter(s, 2));

        s = "three years experience ";
        assertTrue(yearsOfExperienceFilter.showJobFilter(s, 3));

        s = "Master/Bachelor’s degree in Computer Science or related discipline * 5+ years of software design and development experience with core Java and J2EE * Experienced in building resilient, stateless, scalable, & distributed systems * Ability to meet tight deadlines and to be productive and effective within a matrix organization * Excellent communicator – verbal and written skills required * Experience with Spark and Scala * Programming/scripting/query Languages: Java, Python, SQL * AWS deployment automation: Terraform/Cloud Formation * General AWS Service knowledge: S3, Redshift, Elastic Search, DynamoDB, EC2, EMR, Data Pipeline, Glue, Kinesis, SNS, SQS";
        assertTrue(yearsOfExperienceFilter.showJobFilter(s, 5));

        s = "5 years’ experience maintaining Cisco Systems (Nexus) equipment and F5 load balancers";
        assertTrue(yearsOfExperienceFilter.showJobFilter(s, 5));
    }
}