package fields;

/**
 * This class extends ColumnField and is used to create an SQL field of type Varchar.
 * It stores data required by the Varchar field and has getters which return attributes
 * required to create SQL queries.
 */
public class VarcharField extends ColumnField {

    private String defaultText;
    private int size;

    /**
     * Constructor for Varchar field without default value.
     * @param size Integer which stores the maximum size of the Varchar field.
     * @param args Boolean arguments required to set the constraints required by ColumnField.
     */
    public VarcharField(int size, boolean ... args)  {

        super(args);
        this.size = size;
        this.defaultText = "";
    }

    /**
     * Constructor for Varchar field with default value.
     * @param size Integer which stores the maximum size of the Varchar field.
     * @param defaultText String which stores the default value.
     * @param args Boolean arguments required to set the constraints required by ColumnField.
     */
    public VarcharField(int size, String defaultText, boolean ... args) {

        super(args);
        this.size = size;
        this.defaultText = defaultText;
    }

    /**
     * Getter for the default text.
     * @return The default text of the Varchar field.
     */
    public String getDefaultText() {

        return defaultText;
    }

    /**
     * Getter for the size.
     * @return  The maximum size of the varchar field.
     */
    public int getSize() {

        return size;
    }
}
