package fields;

public class NumericField extends ColumnField {

    private double defaultValue;
    private String fieldName;
    private int size;
    private int precision;

    public NumericField(String fieldName, int size, int precision, boolean ... args) { // nullable, primary, foreign, defValue

        super(args);
        this.size = size;
        this.fieldName = fieldName;
        this.precision = precision;
        this.defaultValue = Double.NaN;

    }

    public NumericField(String fieldName, int size, int precision, double defaultValue, boolean ... args){

        super(args);
        this.size = size;
        this.fieldName = fieldName;
        this.precision = precision;
        this.defaultValue = defaultValue;

    }

    public double getDefaultValue() {

        return defaultValue;
    }

    public int getSize () {

        return size;
    }

    public int getPrecision () {

        return precision;
    }

    public String getFieldName() {

        return fieldName;
    }
}
