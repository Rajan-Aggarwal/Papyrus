package fields;

public class ForeignKeyField extends ColumnField {

    private Object reference;

    public ForeignKeyField(Object reference) {

        this.reference = reference;

    }

    public Object getReference() {
        return this.reference;
    }

    /*public String getReferencedClass() {
        return this.reference.
    }*/
}
