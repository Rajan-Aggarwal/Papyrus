package ruler;

/**
 * Custom exception class that extends RulerException.
 * Returns exceptions while executing queries.
 */
public class InvalidQueryException extends RulerException{

    private String sqlException;

    public InvalidQueryException(String sqlException) {

        this.sqlException = sqlException;
    }

    public String toString() {

        return sqlException;
    }
}

