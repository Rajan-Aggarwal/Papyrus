package fields;

import java.text.ParseException;
import java.util.Date;
import java.text.SimpleDateFormat;

public class TimeField extends ColumnField {

    private Date time;

    public TimeField(String time, boolean ... args) throws ParseException {

        super(args);
        this.time = new SimpleDateFormat("hh:mm:ss").parse(time);
    }

    public Date getTime() {
        return this.time;
    }
}
