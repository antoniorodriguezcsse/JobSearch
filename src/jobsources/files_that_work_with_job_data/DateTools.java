package jobsources.files_that_work_with_job_data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class DateTools implements InterfaceDateTools {

    @Override
    public String getDifferenceBetweenDatesInDays(String finalDate, String initialDate, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);

        Date date1 = null;
        Date date2 = null;
        try {
            date1 = dateFormat.parse(finalDate);
            date2 = dateFormat.parse(initialDate);
        } catch (ParseException e) {
            return "invalid date.";
        }

        if (date1 == null || date2 == null) {
            return "date is null.";
        }
        long differenceInMilliseconds = date1.getTime() - date2.getTime();
        return String.valueOf(Math.toIntExact(TimeUnit.DAYS.convert(differenceInMilliseconds, TimeUnit.MILLISECONDS)));
    }

    @Override
    public String getDateMinusDays(String numberOfDays, String desiredDateFormat){
        Date dateObject = null;
        Date inputDate = null;
        LocalDate date = LocalDate.now().minusDays(Long.parseLong(numberOfDays));

        try {
            dateObject = new SimpleDateFormat("yyyy-MM-dd").parse(String.valueOf(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        SimpleDateFormat output = new SimpleDateFormat(desiredDateFormat);
        return output.format(dateObject);
    }

    @Override
    public String getCurrentDate(String dateFormat)
    {
        LocalDate localDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat);
        return localDate.format(formatter);
    }


}
