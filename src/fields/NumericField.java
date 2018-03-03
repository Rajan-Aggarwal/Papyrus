package fields;

/**
 * This class extends ColumnField and is used to create an SQL field of type Numeric.
 * It stores data required by the Numeric field and has getters which return attributes
 * required to create SQL queries.
 */
public class NumericField extends ColumnField {

    private double defaultValue;
    private int size;
    private int precision;

    /**
     * Constructor for Numeric field without default value.
     * @param size Integer which stores the maximum size of the Numeric value.
     * @param precision Integer which stores the maximum precision of the Numeric value.
     * @param args Boolean arguments required to set the constraints required by ColumnField.
     */
    public NumericField(int size, int precision, boolean ... args) {

        super(args);
        this.size = size;
        this.precision = precision;
        this.defaultValue = Double.NaN;
    }

    /**
     * Constructor for Numeric field with default value.
     * @param size Integer which stores the maximum size of the Numeric value.
     * @param precision Integer which stores the maximum precision of the Numeric value.
     * @param defaultValue Double value which stores the default value of the Numeric field.
     * @param args Boolean arguments required to set the constraints required by ColumnField.
     */
    public NumericField(int size, int precision, double defaultValue, boolean ... args){

        super(args);
        this.size = size;
        this.precision = precision;
        this.defaultValue = defaultValue;
    }

    /**
     * Getter for the default value.
     * @return The default value of the Numeric field.
     */
    public double getDefaultValue() {

        return defaultValue;
    }

    /**
     * Getter for the size.
     * @return The size of the Numeric field.
     */
    public int getSize () {

        return size;
    }

    /**
     * Getter for the precision.
     * @return The precision of the Numeric field.
     */
    public int getPrecision () {

        return precision;
    }
}
