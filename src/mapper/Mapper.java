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

    private Scroll table;
    private String tableName;
    private Field[] fields;

    /*
        CONSTRUCTOR and CREATING THE TABLE
     */
    public Mapper(Scroll tableObj) throws InvalidFieldException {

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
            stmt.executeUpdate("drop table " + this.tableName);
            System.out.println("DROPPED");
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
            System.out.println("SQLException: " + e);
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

    private String primaryClause(boolean isPrimary, Field field) {

        if (isPrimary) {
            return field.getName() + ",";
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

    private void getSimpleQuery(Field field, StringBuilder query, StringBuilder primaryKeys) throws IllegalAccessException {

        if (field.getType().equals(VarcharField.class)) {

            field.setAccessible(true);
            VarcharField fieldValue = (VarcharField) field.get(this.table);
            query.append(field.getName()).append(" varchar(").append(fieldValue.getSize() + ")");
            query.append(nullClause(fieldValue.isNullable()));
            primaryKeys.append(primaryClause(fieldValue.isPrimary(), field));
            query.append(uniqueClause(fieldValue.isUnique()));
            query.append(defaultVarcharClause(fieldValue.getDefaultText()));
            query.append(",");

        } else if (field.getType().equals(NumericField.class)) {

            field.setAccessible(true);
            NumericField fieldValue = (NumericField) field.get(this.table);
            query.append(field.getName()).append(" numeric(").append(fieldValue.getSize()).append(",").append(fieldValue.getPrecision()).append(")");
            query.append(nullClause(fieldValue.isNullable()));
            primaryKeys.append(primaryClause(fieldValue.isPrimary(), field));
            query.append(uniqueClause(fieldValue.isUnique()));
            query.append(defaultNumericClause(fieldValue.getDefaultValue()));
            query.append(",");

        } else if (field.getType().equals(DateField.class)) {

            field.setAccessible(true);
            DateField fieldValue = (DateField) field.get(this.table);
            query.append(field.getName()).append(" date");
            query.append(nullClause(fieldValue.isNullable()));
            primaryKeys.append(primaryClause(fieldValue.isPrimary(), field));
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

    private String createTableQuery(Field[] fields) throws IllegalAccessException {

        StringBuilder query = new StringBuilder("create table " + this.tableName + "(\n");
        StringBuilder primaryKeys = new StringBuilder("primary key(");
        //boolean hasForeign = false;
        //StringBuilder references = new StringBuilder(" references ")

        for (Field field : fields) {

            if (field.getType().equals(ForeignKeyField.class)) {

                field.setAccessible(true);
                ForeignKeyField fieldValue = (ForeignKeyField) field.get(this.table);

                Object refObj = fieldValue.getReference(); //gets "Student"

                StringBuilder foreignKey = new StringBuilder("foreign key (");
                foreignKey.append(field.getName() + ") " + "references (" + refObj.getClass().getSimpleName());

                Field foreignField = fieldValue.getClass().getDeclaredFields()[0];
                //System.out.println("Foreign field = " + foreignField);
                getSimpleQuery(foreignField, query, primaryKeys);
                query.append(foreignKey.append("),\n"));
            }
            else { getSimpleQuery (field, query, primaryKeys); }

        }

        //delete the last comma
        primaryKeys.deleteCharAt(primaryKeys.length() - 1);

        query.append(primaryKeys.append(")\n"));
        query.append(")");
        return query.toString();
    }

}
