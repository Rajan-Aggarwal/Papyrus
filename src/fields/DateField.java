package fields;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;

public class DateField extends ColumnField {

    private Date defaultDate;

    public DateField(boolean ... args) throws ParseException {

        super(args);
        String dateStamp = new SimpleDateFormat("YYYY-MM-DD").format(Calendar.getInstance().getTime());
        DateFormat df = new SimpleDateFormat("YYYY-MM-DD");
        this.defaultDate = df.parse(dateStamp);
    }

    public DateField(String defaultDate, boolean ... args) throws ParseException {

        super(args);
        this.defaultDate = new SimpleDateFormat("YYYY-MM-DD").parse(defaultDate);
    }

    public Date getDate() {

        return this.defaultDate;
    }
}
