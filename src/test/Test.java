package test;

import fields.*;
import mapper.*;
import ruler.*;
import scroll.Scroll;
import java.util.ArrayList;
import java.util.HashMap;

class Student extends Scroll {

    VarcharField ID = new VarcharField(5,false, true);
    VarcharField name = new VarcharField( 10, false);
    IntegerField tot_cred = new IntegerField(5);
}

class Instructor extends Scroll {

    ForeignKeyField ID = new ForeignKeyField(new Student(), "ID");
    VarcharField name = new VarcharField( 10, false);
    IntegerField salary = new IntegerField(1000);
}


public class Test {

    public static void main(String[] args) throws MapperException, RulerException {

        // Create student and instructor tables
        Mapper studentMapper = new Mapper(new Student());
        Mapper instructorMapper = new Mapper(new Instructor());

        // Insert values into student and instructor
        Ruler studentRuler = new Ruler(new Student());
        Ruler instructorRuler = new Ruler(new Instructor());

        HashMap<String, ArrayList<Object>> tuples = new HashMap<>();
        ArrayList<Object> ID = new ArrayList<>();
        ID.add("0001");
        ID.add("0002");
        ArrayList<Object> name = new ArrayList<>();
        name.add("Tom");
        name.add("Matt");
        ArrayList<Object> tot_cred = new ArrayList<>();
        tot_cred.add(10);
        tot_cred.add(6);
        tuples.put("ID", ID);
        tuples.put("name", name);
        tuples.put("tot_cred", tot_cred);
        studentRuler.insert(tuples, 2);

        tuples.clear();
        ID.clear();
        name.clear();
        ArrayList<Object> salary = new ArrayList<>();
        ID.add("0001");
        ID.add("0002");
        name.add("John");
        name.add("Jacob");
        salary.add(3000);
        salary.add(2000);
        tuples.put("ID", ID);
        tuples.put("name", name);
        tuples.put("salary", salary);
        instructorRuler.insert(tuples, 2);

        //Select all students with tot_cred=10
        HashMap<String, Object> where = new HashMap<>();
        where.put("tot_cred", 10);

        HashMap<String, ArrayList<Object>> rows = studentRuler.selectAll(where);

        for (HashMap.Entry<String, ArrayList<Object>> entry : rows.entrySet()) {
            ArrayList<Object> values = entry.getValue();
            for (Object obj : values) {
                System.out.print(obj + " ");
            }
            System.out.println();
        }

        System.out.println();

        //Update instructor salary where salary=2000
        HashMap<String, Object> updates = new HashMap<>();
        updates.put("salary", 3000);
        where.clear();
        where.put("salary", 2000);

        instructorRuler.update(updates, where);

        rows = instructorRuler.selectAll();

        for (HashMap.Entry<String, ArrayList<Object>> entry : rows.entrySet()) {
            ArrayList<Object> values = entry.getValue();
            for (Object obj : values) {
                System.out.print(obj + " ");
            }
            System.out.println();
        }

        Object ob = instructorRuler.count("salary", where);

        System.out.println(ob);

        System.out.println();

        //Delete student where tot_cred = 6
        where.clear();
        where.put("tot_cred", 6);

        studentRuler.delete(where);

        rows = studentRuler.selectAll();

        for (HashMap.Entry<String, ArrayList<Object>> entry : rows.entrySet()) {
            ArrayList<Object> values = entry.getValue();
            for (Object obj : values) {
                System.out.print(obj + " ");
            }
            System.out.println();
        }
    }
}
