package ruler;

public class InvalidInsertionException extends Exception{
    private String sqlException;
    public InvalidInsertionException(String sqlException) {
        this.sqlException = sqlException;
    }
    public String toString() {
        return sqlException;
    }
}
