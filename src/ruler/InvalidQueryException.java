package ruler;

public class InvalidQueryException extends RulerException{

    private String sqlException;

    public InvalidQueryException(String sqlException) {

        this.sqlException = sqlException;
    }

    public String toString() {

        return sqlException;
    }
}

