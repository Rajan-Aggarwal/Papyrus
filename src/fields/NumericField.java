package fields;

public class NumericField extends ColumnField {

    private double defaultValue;
    private int size;
    private int precision;

    public NumericField(int size, int precision, boolean ... args) { // nullable, primary, foreign, defValue

        super(args);
        this.size = size;
        this.precision = precision;
        this.defaultValue = -999.9999; //sorry but only way i can think of as of now. pls fix

    }

    public NumericField(int size, int precision, double defaultValue, boolean ... args){

        super(args);
        this.size = size;
        this.precision = precision;
        this.defaultValue = defaultValue;

    }
}