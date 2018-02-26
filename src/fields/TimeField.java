package fields;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;

public class TimeField extends ColumnField {

    private Date defaultTime;
    
    public TimeField(boolean ... args) throws ParseException {
        
        super(args);
        String timeStamp = new SimpleDateFormat("hh:mm:ss").format(Calendar.getInstance().getTime());
        DateFormat df = new SimpleDateFormat("hh:mm:ss");
        this.defaultTime = df.parse(timeStamp);
    }

    public TimeField(String defaultTime, boolean ... args) throws ParseException {

        super(args);
        this.defaultTime = new SimpleDateFormat("hh:mm:ss").parse(defaultTime);
    }

    public Date getTime() {

        return this.defaultTime;
    }
}
