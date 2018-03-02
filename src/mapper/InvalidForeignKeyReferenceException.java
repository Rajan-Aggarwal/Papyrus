package mapper;

public class InvalidForeignKeyReferenceException extends MapperException {

    public String toString() {
        return "No such field in the referenced scroll";
    }
}
