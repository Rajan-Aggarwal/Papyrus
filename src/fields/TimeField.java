package fields;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;

public class TimeField extends ColumnField {

    private String defaultTime;
    private String fieldName;
    
    public TimeField(String fieldName, boolean ... args) throws ParseException {
        
        super(args);
        this.fieldName = fieldName;
        this.defaultTime = new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());
    }

    public TimeField(String fieldName, String defaultTime, boolean ... args) throws ParseException {

        super(args);
        this.fieldName = fieldName;
        this.defaultTime = new SimpleDateFormat("HH:mm:ss").format(defaultTime);
    }

    public String getTime() {

        return this.defaultTime;
    }

    public String getFieldName() {

        return fieldName;
    }

    public String getDefaultValue() {

        return defaultTime.toString();
    }
}
