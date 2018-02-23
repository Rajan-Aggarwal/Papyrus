package fields;

public class Numeric extends ColumnField {

    double var;
    private int size;
    private int precision;

    Numeric (int size, int precision, boolean ... args){ // nullable, primary, foreign, defValue

        super(args);
        this.size = size;
        this.precision = precision;

    }
}
