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

/**
 * Class that contains helper functions to perform data manipulation
 * queries.
 */
public class Ruler {
    private String tableName;
    private Field[] fields;

    /**
     * Constructor for the ruler class.
     * @param tableObj Object which extends Scroll which is used to define the table.
     */
    public Ruler (Scroll tableObj) {

        this.tableName = tableObj.getClass().getSimpleName();
        this.fields = tableObj.getClass().getDeclaredFields();
    }

    /**
     * Performs insert operation on the given table for one tuple.
     * @param tuple HashMap where keys are the names of the attributes of the table
     *              and values are the corresponding values for each attribute.
     * @throws InvalidUpdateException when insert operation fails.
     */
    public void insert (HashMap<String, Object> tuple) throws InvalidUpdateException {

        Connection conn = DAO.getConnection();

        StringBuilder insertQuery = new StringBuilder("insert into " + this.tableName + " values (");
        StringBuilder attributes = new StringBuilder(" (");
        StringBuilder values = new StringBuilder(" values(");

        for (Field field:this.fields) {
            if(tuple.get(field.getName())!=null) {
                attributes.append(field.getName()).append(",");
                values.append(tuple.get(field.getName())).append(",");
            }
        }

        attributes.deleteCharAt(attributes.length()-1);
        values.deleteCharAt(values.length()-1);
        attributes.append(")");
        values.append(")");
        insertQuery.append(attributes).append(values);

        executeInsert(conn, insertQuery);
    }

    /**
     * Performs insert operation on the given table for multiple tuple.
     * @param tuples HashMap where keys are the names of the attributes of the given table
     *               and values are ArrayLists containing the corresponding values for each attribute.
     *               Each value of the ArrayList for each of the keys represents one tuple.
     * @param num Integer for the number of tuples to be inserted.
     * @throws InvalidUpdateException when insert operation fails.
     */
    public void insert (HashMap<String, ArrayList<Object>> tuples, int num) throws InvalidUpdateException {

        Connection conn = DAO.getConnection();

        for (int i=0;i<num;i++) {
            StringBuilder insertQuery = new StringBuilder("insert into " + this.tableName);
            StringBuilder attributes = new StringBuilder(" (");
            StringBuilder values = new StringBuilder(" values(");

            for (Field field:this.fields) {
                if(tuples.get(field.getName())!=null) {
                    attributes.append(field.getName()).append(",");
                    values.append(tuples.get(field.getName()).get(i)).append(",");
                }
            }

            attributes.deleteCharAt(attributes.length()-1);
            values.deleteCharAt(values.length()-1);
            attributes.append(")");
            values.append(")");
            insertQuery.append(attributes).append(values);

            executeInsert(conn, insertQuery);
        }
    }

    /**
     * Performs select operation on the table and selects all tuples subject to no constraints.
     * @return HashMap where keys are the names of the attributes of the table and the values
     *          are the corresponding values stored in the table under each attribute.
     * @throws InvalidQueryException when select operation fails.
     */
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

    /**
     * Performs select operation on the table and selects all tuples subject to constraints.
     * @param where HashMap where keys are the names of attributes and values are the values
     *              are the corresponding constraints for each of the attributes.
     * @return HashMap where keys are the names of the attributes of the table and the values
     *          are the corresponding values stored in the table under each attribute.
     * @throws InvalidQueryException when select operation fails.
     */
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

    /**
     * Performs select operation on the table and selects specific tuples subject to no constraints.
     * @param attributes ArrayList containing names of attributes to select from the table.
     * @return HashMap where keys are the names of the attributes of the table and the values
     *          are the corresponding values stored in the table under each attribute.
     * @throws InvalidQueryException when select operation fails.
     */
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

    /**
     * Performs select operation on the table and selects specific tuples subject to constraints.
     * @param attributes ArrayList containing names of attributes to select from the table.
     * @param where HashMap where keys are the names of attributes and values are the values
     *              are the corresponding constraints for each of the attributes.
     * @return HashMap where keys are the names of the attributes of the table and the values
     *          are the corresponding values stored in the table under each attribute.
     * @throws InvalidQueryException when select operation fails.
     */
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

    /**
     * Performs update operation on table subject to no constraints.
     * @param set HashMap where keys are the names of the attributes to update
     *             and values are the corresponding new values of each attribute.
     * @throws InvalidUpdateException when update operation fails.
     */
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

    /**
     * Performs update operation on table subject to constraints.
     * @param set HashMap where keys are the names of the attributes to update
     *             and values are the corresponding new values of each attribute.
     * @param where HashMap where keys are the names of attributes and values are the values
     *              are the corresponding constraints for each of the attributes.
     * @throws InvalidUpdateException when update operation fails.
     */
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

    /**
     * Performs delete operations on table subject to no constraints.
     * @throws InvalidUpdateException when delete operation fails.
     */
    public void deleteAll() throws InvalidUpdateException {

        Connection conn = DAO.getConnection();

        executeUpdate(conn, "delete from " + this.tableName);
    }

    /**
     * Performs delete operation on table subject to constraints.
     * @param where HashMap where keys are the names of attributes and values are the values
     *              are the corresponding constraints for each of the attributes.
     * @throws InvalidUpdateException when delete operation fails.
     */
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

    private void executeInsert(Connection conn, StringBuilder insertQuery) throws InvalidUpdateException {
        try {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(insertQuery.toString());
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

    private void executeUpdate(Connection conn, String updateQuery) throws InvalidUpdateException {

        try{
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(updateQuery);
        } catch (SQLException e) {
            throw new InvalidUpdateException(e.getMessage());
        }
    }
}
