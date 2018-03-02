package mapper;

import database.DAO;
import fields.*;
import fields.ForeignKeyField;
//import org.omg.Messaging.SYNC_WITH_TRANSPORT;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import scroll.*;

/*
    The ORM engine
 */

public class Mapper {

    //static int counter = 0;

    private Scroll table;
    private String tableName;
    private Field[] fields;

    /*
        CONSTRUCTOR and CREATING THE TABLE
     */

    public Mapper(Scroll tableObj) throws InvalidFieldException, InvalidTableDescriptionException {

        //counter++;
        this.table = tableObj;
        this.tableName = table.getClass().getSimpleName();
        fields = table.getClass().getDeclaredFields();

        for (Field field : fields) {
            if (!field.getType().equals(VarcharField.class) && !field.getType().equals(NumericField.class) &&
            !field.getType().equals(DateField.class) && !field.getType().equals(ForeignKeyField.class)) {
                throw new InvalidFieldException();
            }
        }

        Connection conn = DAO.getConnection();

        Statement stmt = null;

        try {
            stmt = conn.createStatement();
            stmt.executeUpdate("drop table " + this.tableName + " cascade constraints");
            //System.out.println("DROPPED");
        } catch (SQLException e) {
            //do nothing an hope it's 'cause table does not exist
            System.out.println("*");
        }

        try {
            String query = createTableQuery(fields);
            if (stmt!=null){
                System.out.println(query);
                stmt.executeUpdate(query);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            throw new InvalidTableDescriptionException(e.getMessage());
        } catch (InvalidForeignKeyReferenceException e) {
            e.printStackTrace();
        }


    }

    //////////////////////////////////////////////

    /*
        CLAUSES WHICH APPLY TO EVERY TYPE
     */

    private String nullClause(boolean isNullable) {

        if (isNullable) {
            return "";
        }

        return " not null";

    }

    private String primaryClause(boolean isPrimary, String fieldName) {

        if (isPrimary) {
            return fieldName + ",";
        } else {
            return "";
        }
    }

    private  String uniqueClause(boolean isUnique) {

        if (isUnique) {
            return " unique";
        } else {
            return "";
        }
    }

    //only for varchar

    private String defaultVarcharClause(String defaultText) {

        if (defaultText.equals("")) {
            return "";
        }
        return " default '" + defaultText + "'";
    }

    //only for numeric

    private String defaultNumericClause(double defaultValue) {

        if(Double.isNaN(defaultValue)) {
            return "";
        }
        return " default " + defaultValue + "";
    }

    //only for date

    /*private String formatDateClause(String format) {

        return " format '" + format + "'";

    }*/

    private String defaultDateClause(String format, String defaultDate) {

        if (format.equals("") && defaultDate.equals("")) {
            return "";
        }

        //default (to_date('01-01-2001', 'dd-mm-yyyy'))

        return " default (to_date('" + defaultDate + "', '" + format + "'))";
    }

    private void getSimpleQuery(Field field, String fieldName, StringBuilder query, StringBuilder primaryKeys, Scroll tableObj) throws IllegalAccessException {

        if (field.getType().equals(VarcharField.class)) {

            field.setAccessible(true);
            VarcharField fieldValue = (VarcharField) field.get(tableObj);
            query.append(fieldName).append(" varchar(").append(fieldValue.getSize() + ")");
            query.append(nullClause(fieldValue.isNullable()));
            primaryKeys.append(primaryClause(fieldValue.isPrimary(), fieldName));
            query.append(uniqueClause(fieldValue.isUnique()));
            query.append(defaultVarcharClause(fieldValue.getDefaultText()));
            query.append(",");

        } else if (field.getType().equals(NumericField.class)) {

            field.setAccessible(true);
            NumericField fieldValue = (NumericField) field.get(tableObj);
            query.append(fieldName).append(" numeric(").append(fieldValue.getSize()).append(",").append(fieldValue.getPrecision()).append(")");
            query.append(nullClause(fieldValue.isNullable()));
            primaryKeys.append(primaryClause(fieldValue.isPrimary(), fieldName));
            query.append(uniqueClause(fieldValue.isUnique()));
            query.append(defaultNumericClause(fieldValue.getDefaultValue()));
            query.append(",");

        } else if (field.getType().equals(DateField.class)) {

            field.setAccessible(true);
            DateField fieldValue = (DateField) field.get(tableObj);
            query.append(fieldName).append(" date");
            query.append(nullClause(fieldValue.isNullable()));
            primaryKeys.append(primaryClause(fieldValue.isPrimary(), fieldName));
            query.append(uniqueClause(fieldValue.isUnique()));
            //query.append(formatDateClause(fieldValue.getFormat()));
            query.append((defaultDateClause(fieldValue.getFormat(),
                    fieldValue.getDefaultDate())));
            query.append(",");

        }

        query.append("\n");
    }


    /*
      CREATING FINAL QUERY
     */

    private String createTableQuery(Field[] fields) throws IllegalAccessException, InvalidForeignKeyReferenceException {

        StringBuilder query = new StringBuilder("create table " + this.tableName + "(\n");
        StringBuilder primaryKeys = new StringBuilder("primary key(");
        //boolean hasForeign = false;
        //StringBuilder references = new StringBuilder(" references ")

        for (Field field : fields) {

            if (field.getType().equals(ForeignKeyField.class)) {

                /*

                getSimpleQuery takes in a field value with sb query and primary keys

                ForeignKeyField fk = new ForeignKeyField(new Student());

                1. You need the object of student() ---> refObj
                2. You need the field of that object() ---> off of that object get the fields and
                    search for the name of that field!!
                    Fields[] refFields = refObj.getClass().getDeclaredFields()
                    for (Field f:refFields) {
                        if (f.getName().equals("name")) return f;
                    }

                */
                field.setAccessible(true);
                ForeignKeyField fieldValue = (ForeignKeyField) field.get(this.table);
                if (fieldValue.getFieldNotFound()) {
                    throw new InvalidForeignKeyReferenceException();
                }
                getSimpleQuery(fieldValue.getRefField(), field.getName(), query, primaryKeys,
                                fieldValue.getRefObject());

                StringBuilder foreignKey = new StringBuilder("foreign key (");
                foreignKey.append(field.getName() + ") references " + fieldValue.getRefName());
                foreignKey.append(" on delete cascade");
                foreignKey.append(",\n");

                query.append(foreignKey);

            }

            else { getSimpleQuery (field, field.getName(), query, primaryKeys, this.table); }

        }

        //delete the last comma
        primaryKeys.deleteCharAt(primaryKeys.length() - 1);
        query.append(primaryKeys.append(")\n"));
        query.append(")");

        return query.toString();
    }

    /*private Field getForeignFieldFromString() {

    }*/

}
