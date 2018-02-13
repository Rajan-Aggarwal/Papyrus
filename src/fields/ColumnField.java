package fields;

/*
    Abstract class that must be extended by every other field class
 */

public abstract class ColumnField {

    public boolean nullable = true;
    public boolean primary = false;
    public boolean foreign = false;

}
