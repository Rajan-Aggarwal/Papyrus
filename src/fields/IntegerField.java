package fields;

/**
 * Class that extends ColumnField class.
 * It stores data required by the integer field in SQL and has getters and setters which return attributes
 * required to create SQL queries.
 */

public class IntegerField extends ColumnField{

    private int defaultInt;

    /**
     * Constructor for IntegerField without a default value
     * @param args Variable arguments containing state of Nullable, Primary Key
     *              and Unique in that order
     */
    public IntegerField(boolean ... args) {
        super(args);
        this.defaultInt = Integer.MIN_VALUE;
    }

    /**
     * Constructor for IntegerField with a default value
     * @param args Variable arguments containing state of Nullable, Primary Key
     *              and Unique in that order
     */

    public IntegerField(int defaultInt, boolean ... args) {
        super(args);
        this.defaultInt = defaultInt;
    }

    /**
     * Getter for default integer value.
     * @return the default integer value of the field.
     */

    public int getDefaultInt() {
        return this.defaultInt;
    }

}
