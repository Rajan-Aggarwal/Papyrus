package mapper;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;

public class Mapper {

    private Class table;
    private String tableName;
    private HashMap<String, Class> fieldMap = new HashMap<>();

    public Mapper(Object tableObj) {
        this.table = tableObj.getClass();
        tableName = table.getName();
        Field[] fields = table.getDeclaredFields();
        for (Field field : fields) {
            fieldMap.put(field.getName(), field.getType());
        }
    }

    public HashMap<String, Class> getFieldMap() {
        return this.fieldMap;
    }

}
