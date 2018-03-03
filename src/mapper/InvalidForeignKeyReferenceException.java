package mapper;

/**
 *Custom exception that extends MapperException.
 *Thrown when foreign keys mentioned in the user-defined Scroll class are invalid/do not exist.
 */

public class InvalidForeignKeyReferenceException extends MapperException {

    /**
     * overrides toString() method of Object class
     * @return exception description
     */

    public String toString() {

        return "No such field in the referenced scroll";
    }
}
