package jobsources.files_that_work_with_job_data;

public interface InterfaceDateTools {
    String getDifferenceBetweenDatesInDays(String finalDate, String initialDate, String format);

    String getDateMinusDays(String numberOfDays, String desiredDateFormat);

    String getCurrentDate(String dateFormat);
}
