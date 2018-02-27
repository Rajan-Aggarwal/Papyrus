package mapper;

import fields.*;
//import org.omg.Messaging.SYNC_WITH_TRANSPORT;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Calendar;

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
            if (!field.getType().equals(VarcharField.class) && !field.getType().equals(NumericField.class) &&
            !field.getType().equals(DateField.class) && !field.getType().equals(TimeField.class)) {
                throw new InvalidFieldException();
            }
        }

        try {
            String query = createTableQuery(fields);
            System.out.println(query);
        } catch (IllegalAccessException e) {
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

    private String primaryClause(boolean isPrimary, Field field) {

        if (isPrimary) {
            return field.getName();
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
        return " default ('" + defaultText + "')";
    }

    //only for numeric

    private String defaultNumericClause(double defaultValue) {

        if(defaultValue == Double.NaN) {
            return "";
        }
        return " default (" + defaultValue + ")";
    }

    //only for date

    private String defaultDateClause(String defaultDate) {

        String dateStamp = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
        if (defaultDate.equals(dateStamp)) {
            return "";
        }
        return " default ('" + defaultDate + "')";
    }

    //only for time

    private String defaultTimeClause(String defaultTime) {

        String timeStamp = new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());
        if (defaultTime.equals(timeStamp)) {
            return "";
        }
        return " default ('" + defaultTime + "')";
    }

    /////create for others here////

    /*
      CREATING FINAL QUERY
     */

    private String createTableQuery(Field[] fields) throws IllegalAccessException {

        StringBuilder query = new StringBuilder("create table " + this.tableName + "(\n");
        StringBuilder primaryKeys = new StringBuilder("primary key(");

        for (Field field : fields) {

            if (field.getType().equals(VarcharField.class)) {

                field.setAccessible(true);
                VarcharField fieldValue = (VarcharField) field.get(this.table);
                query.append(field.getName()).append(" varchar(").append(fieldValue.getSize()).append(")");
                query.append(nullClause(fieldValue.isNullable()));
                primaryKeys.append(primaryClause(fieldValue.isPrimary(), field)).append(", ");
                query.append(uniqueClause(fieldValue.isUnique()));
                query.append(defaultVarcharClause(fieldValue.getDefaultText()));
                query.append(",");
            } else if (field.getType().equals(NumericField.class)) {

                field.setAccessible(true);
                NumericField fieldValue = (NumericField) field.get(this.table);
                query.append(field.getName()).append(" numeric(").append(fieldValue.getSize()).append(",").append(fieldValue.getPrecision()).append(")");
                query.append(nullClause(fieldValue.isNullable()));
                primaryKeys.append(primaryClause(fieldValue.isPrimary(), field)).append(", ");
                query.append(uniqueClause(fieldValue.isUnique()));
                query.append(defaultNumericClause(fieldValue.getDefaultValue()));
                query.append(",");
            } else if (field.getType().equals(DateField.class)) {

                field.setAccessible(true);
                DateField fieldValue = (DateField) field.get(this.table);
                query.append(field.getName()).append(" date");
                query.append(nullClause(fieldValue.isNullable()));
                primaryKeys.append(primaryClause(fieldValue.isPrimary(), field)).append(", ");
                query.append(uniqueClause(fieldValue.isUnique()));
                query.append((defaultDateClause(fieldValue.getDefaultValue())));
                query.append(",");
            } else if (field.getType().equals(TimeField.class)) {

                field.setAccessible(true);
                TimeField fieldValue = (TimeField) field.get(this.table);
                query.append(field.getName()).append(" time");
                query.append(nullClause(fieldValue.isNullable()));
                primaryKeys.append(primaryClause(fieldValue.isPrimary(), field)).append(",");
                query.append(uniqueClause(fieldValue.isUnique()));
                query.append((defaultTimeClause(fieldValue.getDefaultValue())));
                query.append(",");
            }
            query.append("\n");
        }

        //delete the last comma
        primaryKeys.deleteCharAt(primaryKeys.length() - 1);
        query.append(primaryKeys.append(")\n"));
        query.append(");");
        return query.toString();
    }

}
