package fields;

import scroll.Scroll;
import java.lang.reflect.Field;

/**
 * This class extends ColumnField and is used to create an Foreign Keys.
 * It stores data required to add foreign key constraints in SQL.
 */
public class ForeignKeyField extends ColumnField {

    private Scroll refObject;
    private Field refField;
    private boolean fieldNotFound;
    private String refName;

    /**
     * Constructor used to store metadata to create foreign key query.
     * @param refObject Object of type Scroll which is the table referred by foreign key.
     * @param refAttribute String which is mame of attribute being referred to.
     */
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

    /**
     * Getter to return status of the foreign key search.
     * @return The status
     */
    public boolean getFieldNotFound() {

        return this.fieldNotFound;
    }

    /**
     * Getter for the referred object.
     * @return The object of type Scroll which is referred by the foreign key.
     */
    public Scroll getRefObject() {

        return this.refObject;
    }

    /**
     * Getter for the referred field.
     * @return The referred field of type Field.
     */
    public Field getRefField() {

        return this.refField;
    }

    /**
     * Getter for the class of the referred field.
     * @return The name of the class which the referred field belongs to.
     */
    public String getRefName() {

        return this.refName;
    }
}
