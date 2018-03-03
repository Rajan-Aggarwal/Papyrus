package mapper;

/**
 *Custom exception that extends MapperException.
 *Thrown when fields of the user-defined Scroll class are other than ColumnField type.
 */

public class InvalidFieldException extends MapperException {


    /**
     * overrides toString() method of Object class
     * @return exception description
     */
    public String toString() {

        return "Invalid Field entered";

    }
}
