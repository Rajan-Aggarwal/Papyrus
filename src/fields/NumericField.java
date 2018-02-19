package fields;

public class NumericField extends ColumnField {

    double var;
    private int size;
    private int precision;

    NumericField(int size, int precision, boolean ... args){ // nullable, primary, foreign, defValue

        super(args);
        this.size = size;
        this.precision = precision;
    }
}
