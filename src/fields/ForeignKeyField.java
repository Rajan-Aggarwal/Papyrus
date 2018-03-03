package fields;

import scroll.Scroll;
import java.lang.reflect.Field;

public class ForeignKeyField extends ColumnField {

    private Scroll refObject;
    private Field refField;
    private boolean fieldNotFound;
    private String refName;

    public ForeignKeyField(Scroll refObject, String refAttribute) {

        this.refObject = refObject;
        Field[] fields = refObject.getClass().getDeclaredFields();
        this.refName = refObject.getClass().getSimpleName();
        this.fieldNotFound = true;
        for (Field f : fields) {
            if (f.getName().equals(refAttribute)) {
                this.fieldNotFound = false;
                this.refField = f;
            }
        }
    }

    public boolean getFieldNotFound() {

        return this.fieldNotFound;
    }

    public Scroll getRefObject() {

        return this.refObject;
    }

    public Field getRefField() {

        return this.refField;
    }

    public String getRefName() {

        return this.refName;
    }
}
