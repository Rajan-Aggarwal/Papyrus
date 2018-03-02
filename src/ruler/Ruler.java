package ruler;

import java.sql.Connection;
import java.util.ArrayList;
import java.lang.reflect.Field;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import database.DAO;
import scroll.Scroll;
import java.sql.ResultSet;

public class Ruler {
    private Scroll table;
    private String tableName;
    private Field[] fields;

    public Ruler (Scroll tableObj) {

        this.table = tableObj;
        this.tableName = tableObj.getClass().getSimpleName();
        this.fields = tableObj.getClass().getDeclaredFields();
    }

    public void insert (HashMap<String, Object> tuple) throws InvalidUpdateException {

        Connection conn = DAO.getConnection();

        StringBuilder insertQuery = new StringBuilder("insert into " + this.tableName + " values (");

        for (Field field:this.fields) {
            insertQuery.append(tuple.get(field.getName())).append(",");
        }

        insertQuery.deleteCharAt(insertQuery.length() - 1);
        insertQuery.append(")");

        try {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(insertQuery.toString());
            System.out.println("INSERTED");
        } catch (SQLException e) {
            throw new InvalidUpdateException(e.getMessage());
        }
    }

    public HashMap<String, ArrayList<Object>> selectAll() throws InvalidQueryException {

        Connection conn = DAO.getConnection();

        String selectQuery = "select * from " + this.tableName;

        HashMap<String, ArrayList<Object>> tuples = new HashMap<>();

        for (Field field:this.fields) {
            tuples.put(field.getName(), new ArrayList<>());
        }

        executeSelectAll(conn, selectQuery, tuples);

        return tuples;
    }

    public HashMap<String, ArrayList<Object>> selectAll(HashMap<String,Object> where) throws InvalidQueryException {

        Connection conn = DAO.getConnection();

        StringBuilder whereClause = new StringBuilder(" where ");

        for (HashMap.Entry<String,Object> entry:where.entrySet()) {
            whereClause.append(entry.getKey()).append("=").append(entry.getValue()).append(" and ");
        }

        String selectQuery = "select * from " + this.tableName + whereClause.substring(0, whereClause.length() - 5);

        HashMap<String, ArrayList<Object>> tuples = new HashMap<>();

        for (Field field:this.fields) {
            tuples.put(field.getName(), new ArrayList<>());
        }

        executeSelectAll(conn, selectQuery, tuples);

        return tuples;
    }

    public HashMap<String, ArrayList<Object>> select (ArrayList<String> attributes) throws InvalidQueryException {

        Connection conn = DAO.getConnection();

        StringBuilder selectQuery = new StringBuilder("select ");
        HashMap<String, ArrayList<Object>> tuples = new HashMap<>();

        for (String attribute:attributes) {
            selectQuery.append(attribute).append(",");
            tuples.put(attribute, new ArrayList<>());
        }

        selectQuery.deleteCharAt(selectQuery.length() - 1);
        selectQuery.append(" from ").append(this.tableName);

        executeSelect(conn, selectQuery.toString(), tuples, attributes);

        return tuples;
    }

    public HashMap<String, ArrayList<Object>> select (ArrayList<String> attributes, HashMap<String, Object> where) throws InvalidQueryException {

        Connection conn = DAO.getConnection();

        StringBuilder selectQuery = new StringBuilder("select ");
        HashMap<String, ArrayList<Object>> tuples = new HashMap<>();

        for (String attribute:attributes) {
            selectQuery.append(attribute).append(",");
            tuples.put(attribute, new ArrayList<>());
        }

        selectQuery.deleteCharAt(selectQuery.length() - 1);

        StringBuilder whereClause = new StringBuilder(" where ");

        for (HashMap.Entry<String,Object> entry:where.entrySet()) {
            whereClause.append(entry.getKey()).append("=").append(entry.getValue()).append(" and ");
        }

        selectQuery.append(" from ").append(this.tableName).append(whereClause.substring(0, whereClause.length() - 5));

        executeSelect(conn, selectQuery.toString(), tuples, attributes);

        return tuples;
    }

    public void updateAll(HashMap<String, Object> set) throws InvalidUpdateException {

        Connection conn = DAO.getConnection();

        StringBuilder updateQuery = new StringBuilder("update ");
        updateQuery.append(this.tableName);

        StringBuilder setClause = new StringBuilder(" set ");

        for (HashMap.Entry<String,Object> entry:set.entrySet()) {
            setClause.append(entry.getKey()).append("=").append(entry.getValue()).append(",");
        }

        setClause.deleteCharAt(setClause.length() - 1);
        updateQuery.append(setClause);

        executeUpdate(conn, updateQuery.toString());
    }

    public void update(HashMap<String, Object> set, HashMap<String, Object> where) throws InvalidUpdateException {

        Connection conn = DAO.getConnection();

        StringBuilder updateQuery = new StringBuilder("update ");
        updateQuery.append(this.tableName);

        StringBuilder setClause = new StringBuilder(" set ");

        for (HashMap.Entry<String,Object> entry:set.entrySet()) {
            setClause.append(entry.getKey()).append("=").append(entry.getValue()).append(",");
        }

        setClause.deleteCharAt(setClause.length() - 1);
        updateQuery.append(setClause);

        StringBuilder whereClause = new StringBuilder(" where ");

        for (HashMap.Entry<String,Object> entry:where.entrySet()) {
            whereClause.append(entry.getKey()).append("=").append(entry.getValue()).append(" and ");
        }

        updateQuery.append(whereClause.substring(0, whereClause.length() - 5));

        executeUpdate(conn, updateQuery.toString());
    }

    public void deleteAll() throws InvalidUpdateException {

        Connection conn = DAO.getConnection();

        StringBuilder deleteQuery = new StringBuilder("delete from ");
        deleteQuery.append(this.tableName);

        executeUpdate(conn, deleteQuery.toString());
    }

    public void delete(HashMap<String, Object> where) throws InvalidUpdateException {

        Connection conn = DAO.getConnection();

        StringBuilder deleteQuery = new StringBuilder("delete from ");
        deleteQuery.append(this.tableName);

        StringBuilder whereClause = new StringBuilder(" where ");

        for (HashMap.Entry<String,Object> entry:where.entrySet()) {
            whereClause.append(entry.getKey()).append("=").append(entry.getValue()).append(" and ");
        }

        deleteQuery.append(whereClause.substring(0, whereClause.length() - 5));

        executeUpdate(conn, deleteQuery.toString());
    }

    private void executeUpdate(Connection conn, String updateQuery) throws InvalidUpdateException {

        try{
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(updateQuery);
        } catch (SQLException e) {
            throw new InvalidUpdateException(e.getMessage());
        }
    }

    private void executeSelect(Connection conn, String selectQuery, HashMap<String, ArrayList<Object>> tuples, ArrayList<String> attributes) throws InvalidQueryException {

        ResultSet rs;
        try{
            Statement stmt = conn.createStatement();
            rs = stmt.executeQuery(selectQuery);
            while(rs.next()) {
                int columnNum = 1;
                for (String attribute:attributes) {
                    tuples.get(attribute).add(rs.getObject(columnNum++));
                }
            }
        } catch (SQLException e) {
            throw new InvalidQueryException(e.getMessage());
        }
    }

    private void executeSelectAll(Connection conn, String selectQuery, HashMap<String, ArrayList<Object>> tuples) throws InvalidQueryException {

        ResultSet rs;
        try {
            Statement stmt = conn.createStatement();
            rs = stmt.executeQuery(selectQuery);
            while(rs.next()) {
                int columnNum = 1;
                for(Field field:this.fields) {
                    tuples.get(field.getName()).add(rs.getObject(columnNum++));
                }
            }
        } catch (SQLException e) {
            throw new InvalidQueryException(e.getMessage());
        }
    }
}
