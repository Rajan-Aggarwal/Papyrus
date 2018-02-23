package fields;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/*
    Abstract class that must be extended by every other field class
 */

public abstract class ColumnField {

    private boolean isNullable = true;
    private boolean isPrimary = false;

    //leave it for now on. we'll look into it
    //private boolean isForeign;

    ColumnField (boolean ... args) {

        ArrayList<Boolean> varargs = new ArrayList<Boolean>();

        for (int i=0; i<args.length; i++) {
            varargs.add(args[i]);
        }

        /*while(varargs.size()<2) { //adds false to all the fields till the size becomes 3
            //except the first one, which should be true by default
            if (varargs.size()==1) {
                varargs.add(true);
            }
            else {
                varargs.add(false);
            }
        }*/

        if (varargs.size()==1) {
            isNullable = varargs.get(0);
        } else {
            isNullable = varargs.get(0);
            isPrimary = varargs.get(1);
        }

        System.out.println("nullable" + isNullable + "primary" + isPrimary);

        //isForeign = (boolean) varargs.get(2);s

    }

    public boolean isNullable() {
        return isNullable;
    }

    public boolean isPrimary() {
        return isPrimary;
    }

    /*public boolean isForeign() {
        return isForeign;
    }*/

}
