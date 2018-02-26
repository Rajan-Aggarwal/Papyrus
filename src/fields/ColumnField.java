package fields;

/*
    Abstract class that must be extended by every other field class
 */

public abstract class ColumnField {

    private boolean isNullable = true;
    private boolean isPrimary = false;
    private boolean isUnique = false;

    //leave it for now on. we'll look into it
    //private boolean isForeign;

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

    public boolean isNullable() {

        return isNullable;
    }

    public boolean isPrimary() {

        return isPrimary;
    }

    public boolean isUnique() {

        return isUnique;
    }


    /*public boolean isForeign() {
        return isForeign;
    }*/

}
