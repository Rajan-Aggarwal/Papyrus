package fields;

import java.text.ParseException;
import java.util.Date;
import java.text.SimpleDateFormat;

public class Dates extends ColumnField {

    Date date;

    Dates (String date, boolean ... args) throws ParseException {

        super(args);
        this.date = new SimpleDateFormat("YYYY-MM-DD").parse(date);
    }
}
