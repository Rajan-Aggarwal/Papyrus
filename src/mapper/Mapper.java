package mapper;

import fields.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;

/*
    The ORM engine
 */

public class Mapper {

    private Class table;
    private String tableName;
    private HashMap<String, Class> fieldMap = new HashMap<>(); //dictionary

    public Mapper(Object tableObj) throws InvalidFieldException {
        this.table = tableObj.getClass();
        tableName = table.getName();
        Field[] fields = table.getDeclaredFields();
        for (Field field : fields) {
            try {
                if (!(field.getType().newInstance() instanceof ColumnField)) {
                    throw new InvalidFieldException();
                }
                fieldMap.put(field.getName(), field.getType());
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    public HashMap<String, Class> getFieldMap() {
        return this.fieldMap;
    }

}
