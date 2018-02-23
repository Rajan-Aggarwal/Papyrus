package mapper;

import fields.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;

/*
    The ORM engine
 */

public class Mapper {

    private Object table;
    private String tableName;
    private Field[] fields;

    /*
        CONSTRUCTOR and CREATING THE TABLE
     */
    public Mapper(Object tableObj) throws InvalidFieldException {

        this.table = tableObj;
        this.tableName = table.getClass().getSimpleName();
        fields = table.getClass().getDeclaredFields();

        for (Field field : fields) {
            if (field.getType().equals(VarcharField.class)||field.getType().equals(VarcharField.class)||
            field.getType().equals(VarcharField.class)||field.getType().equals(VarcharField.class)) {
                continue;
            } else {
                throw new InvalidFieldException();
            }
        }

        try {
            String query = createTableQuery(fields);
            System.out.println(query);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }


    }

    //////////////////////////////////////////////

    /*
        CREATING ALL THE CLAUSES WHICH APPLY TO EVERY TYPE
     */

    private String nullClause(boolean isNullable) {
        if (isNullable) {
            return "";
        }
        return "not null";
    }

    private String primaryClause(boolean isPrimary) {
        if (isPrimary) {
            return "primary key";
        } else {
            return "";
        }
    }

    ////apply only to varchar////
    private String defaultClause(String defaultText) {
        return "default '" + defaultText + "'";
    }

    /*
      CREATING FINAL QUERY
     */

    private String createTableQuery(Field[] fields) throws IllegalAccessException, InstantiationException {

        StringBuilder query = new StringBuilder("create table " + this.tableName + "(");

        for (Field field : fields) {

            String fieldName = field.getName();

            if (field.getType().equals(VarcharField.class)) {

                field.setAccessible(true);
                VarcharField fieldValue = (VarcharField) field.get(this.table);
                query.append(fieldName + " varchar(" + fieldValue.getSize() + ") ");
                query.append(nullClause(fieldValue.isNullable()) + " ");
                query.append(primaryClause(fieldValue.isPrimary()) + " ");
                query.append(defaultClause(fieldValue.getDefaultText()));

            }

            //fill this up for other fields

        }

        query.append(");");
        return query.toString();
    }

}
