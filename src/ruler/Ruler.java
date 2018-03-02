package ruler;

import java.sql.Connection;
import java.util.ArrayList;
import java.lang.reflect.Field;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import database.DAO;

public class Ruler {
    private Object table;
    private String tableName;
    private Field[] fields;

    public Ruler (Object tableObj) {

        this.table = table;
        this.tableName = tableObj.getClass().getSimpleName();
        this.fields = tableObj.getClass().getDeclaredFields();
    }

    public void insert (HashMap<String, Object> tuple) {

        Connection conn = DAO.getConnection();
        Statement stmt = null;

        StringBuilder insertQuery = new StringBuilder("insert into " + this.tableName + " values (");

        for (Field field:this.fields) {
            insertQuery.append(tuple.get(field.getName())).append(",");
        }

        insertQuery.deleteCharAt(insertQuery.length() - 1);
        insertQuery.append(")");

        try {
            stmt = conn.createStatement();
            stmt.executeUpdate(insertQuery.toString());
            System.out.println("INSERTED");
        } catch (SQLException e) {
            System.out.println("SQLException: " + e);
        }
    }
}
