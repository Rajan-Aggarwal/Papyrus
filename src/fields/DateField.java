package fields;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;

public class DateField extends ColumnField {

    private String defaultDate;
    private String fieldName;

    public DateField(String fieldName, boolean ... args) throws ParseException {

        super(args);
        this.fieldName = fieldName;
        this.defaultDate = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
    }

    public DateField(String fieldName, String defaultDate, boolean ... args) throws ParseException {

        super(args);
        this.fieldName = fieldName;
        this.defaultDate = new SimpleDateFormat("yyyy-MM-dd").format(defaultDate);
    }

    public String getDate() {

        return defaultDate;
    }

    public String getFieldName() {

        return fieldName;
    }

    public String getDefaultValue() {

        return defaultDate.toString();
    }
}
