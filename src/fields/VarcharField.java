package fields;

public class VarcharField extends ColumnField {

    String text;
    private int size;

    VarcharField(int size, boolean ... args) {

        super(args);
        this.size = size;
    }
}
