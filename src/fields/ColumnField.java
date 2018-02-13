package fields;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/*
    Abstract class that must be extended by every other field class
 */

public abstract class ColumnField {

    public boolean nullable = false;
    public boolean primary = false;
    public boolean foreign = false;
    public boolean defValue = false;

    ColumnField (boolean ... args) {

        ArrayList<Boolean> varargs;
        varargs = new ArrayList<>((Collection<? extends Boolean>) Arrays.asList(args));

        while(varargs.size()<4) {
            varargs.add(false);
        }

        nullable = varargs[0];
        primary = varargs[1];
        foreign = varargs[2];
        defValue = varargs[3];
    }
}
