package fields;

/**
 * This is an abstract class that must be extended by every other field class.
 * It stores data regarding the following constraints -
 * Nullable, Primary Key, Unique
 */

public abstract class ColumnField {

    private boolean isNullable = true;
    private boolean isPrimary = false;
    private boolean isUnique = false;

    /**
     * Assigns the state of constraints for each field.
     * By default isNullable is true, isPrimary is false and isUnique is false.
     * @param args Variable arguments containing state of Nullable, Primary Key
     *              and Unique in that order.
     */
    ColumnField (boolean ... args) {

        if (args.length>=1) {
            isNullable = args[0];
            if (args.length>=2) {
                isPrimary = args[1];
                if (args.length>=3) {
                    isUnique = args[2];
                }
            }
        }
    }

    /**
     * Returns the state of the nullable constraint for the field.
     * @return boolean whether field is Nullable or not.
     */
    public boolean isNullable() {

        return isNullable;
    }

    /**
     * Returns the state of the primary key constraint for the field.
     * @return boolean whether field is a Primary Key or not.
     */
    public boolean isPrimary() {

        return isPrimary;
    }

    /**
     * Returns the state of the primary key constraint for the field.
     * @return boolean whether field is Unique or not;
     */
    public boolean isUnique() {

        return isUnique;
    }
}
