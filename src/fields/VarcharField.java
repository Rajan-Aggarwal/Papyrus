package fields;

public class VarcharField extends ColumnField {

    private String defaultText;
    private String fieldName;
    private int size; //necessary

    public VarcharField(String fieldName, int size, boolean ... args)  {

        super(args);
        this.size = size;
        this.fieldName = fieldName;
        this.defaultText = ""; //see Mapper implementation

    }

    public VarcharField(String fieldName, int size, String defaultText, boolean ... args) {

        super(args);
        this.fieldName = fieldName;
        this.size = size;
        this.defaultText = defaultText;

    }

    public String getDefaultText() {

        return defaultText;
    }

    public int getSize() {

        return size;
    }

    public String getFieldName() {

        return fieldName;
    }

}
