package fields;

import java.util.Date;
import java.text.SimpleDateFormat;

public class Dates extends ColumnField {

    Date date;

    Dates (String date, boolean ... args) {

        super(args);
        this.date = new SimpleDateFormat("YYYY-MM-DD").parse(date);
    }
}
