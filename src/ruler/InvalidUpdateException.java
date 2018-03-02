package ruler;

public class InvalidUpdateException extends RulerException{

    private String sqlException;

    public InvalidUpdateException(String sqlException) {

        this.sqlException = sqlException;
    }

    public String toString() {

        return sqlException;
    }
}
