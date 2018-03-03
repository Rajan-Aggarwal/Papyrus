package fields;

public class VarcharField extends ColumnField {

    private String defaultText;
    private int size;

    public VarcharField(int size, boolean ... args)  {

        super(args);
        this.size = size;
        this.defaultText = "";
    }

    public VarcharField(int size, String defaultText, boolean ... args) {

        super(args);
        this.size = size;
        this.defaultText = defaultText;
    }

    public String getDefaultText() {

        return defaultText;
    }

    public int getSize() {

        return size;
    }
}
