package fields;

public class Varchar extends ColumnField {

    String text;
    private int size;

    Varchar (int size, boolean ... args) {

        super(args);
        this.size = size;

    }
}
