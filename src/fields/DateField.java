package fields;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This class is used to create an SQL field of type Date and extends ColumnField.
 * It stores data required by the Date field and has getters which return attributes
 * required to create SQL queries.
 */

public class DateField extends ColumnField {

    private String format;
    private String defaultDate;

    /**
     * Constructor for Date field without default value and specified format.
     * @param args Boolean arguments required to set the constraints required by ColumnField.
     */
    public DateField(boolean ... args) {

        super(args);
        this.format = "";
        this.defaultDate = "";
    }

    /**
     * Constructor for Date field with deafault value and specified format.
     * @param format Format of the date.
     * @param defaultDate The value of the default date.
     * @param args Boolean arguments required to set the constraints required by
     *             ColumnField.
     * @throws ParseException if the date cannot be formatted in the specified format.
     */
    public DateField(String format, String defaultDate, boolean ... args) throws ParseException {

        super(args);
        this.format = format;
        Date date = new SimpleDateFormat(this.format).parse(defaultDate);
        this.defaultDate = defaultDate;
    }

    /**
     * Getter for the default date.
     * @return The default date of the DateField.
     */
    public String getDefaultDate() {

        return defaultDate;
    }

    /**
     * Getter for the date format
     * @return The date format of the DateField.
     */
    public String getFormat() {

        return this.format;
    }
}
