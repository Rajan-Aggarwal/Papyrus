package mapper;

/**
 * Exception that is thrown when the table description given in the user-defined Scroll class is invalid.
 * Usually occurs when no primary key exist. Exact description is thrown along with it.
 */

public class InvalidTableDescriptionException extends MapperException {

    private String sqlException;

    public InvalidTableDescriptionException(String sqlException) {

        this.sqlException = sqlException;
    }

    /**
     * overrides toString() method of Object class
     * @return exception description containing exact sql error.
     */

    public String toString() {

        return "Invalid table definition: " + this.sqlException;
    }
}
