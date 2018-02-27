package fields;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;

public class TimeField extends ColumnField {

    private String defaultTime;
    
    public TimeField(boolean ... args) throws ParseException {
        
        super(args);
        this.defaultTime = new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());
    }

    public TimeField(String defaultTime, boolean ... args) throws ParseException {

        super(args);

        //to check the validity of date string --> parse exception is thrown otherwise
        Date date = new SimpleDateFormat("HH:mm:ss").parse(defaultTime);
        this.defaultTime = defaultTime;
    }

    public String getTime() {

        return this.defaultTime;
    }

    public String getDefaultValue() {

        return defaultTime.toString();
    }
}
