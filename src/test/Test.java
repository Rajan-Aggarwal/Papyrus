package test;

import fields.*;
import mapper.*;
import oracle.sql.ARRAY;
import ruler.*;
import scroll.Scroll;
import java.util.ArrayList;
import java.util.HashMap;

class Student extends Scroll {

    VarcharField id = new VarcharField(5,false, true);
    VarcharField name = new VarcharField( 10, false);
    IntegerField marks = new IntegerField(10);
    DateField dob = new DateField(false);
}

///Testing

//solves two things:
    //1. Programmer can't go wrong with field mismatches
    //2. Have different names for foreign keys!


class Instructor extends Scroll {

    VarcharField iname = new VarcharField(10, false);
    ForeignKeyField sid = new ForeignKeyField(new Student(), "id");
    NumericField salary = new NumericField(6, 2,false, false, true);
}


public class Test {

    public static void main(String[] args) throws MapperException, RulerException {

        // Test create query
        Mapper student = new Mapper(new Student());
        Mapper instructor = new Mapper(new Instructor());

        // Test insert query
        Ruler student1 = new Ruler(new Student());
        Ruler instructor1 = new Ruler(new Instructor());


        HashMap<String, ArrayList<Object>> tuples = new HashMap<>();
        ArrayList<Object> one = new ArrayList<>();
        one.add("'1010'");
        one.add("'1011'");
        ArrayList<Object> two = new ArrayList<>();
        two.add("'Tom'");
        two.add("'Matt'");
        ArrayList<Object> three = new ArrayList<>();
        three.add("'17-03-1998'");
        three.add("'11-04-1990'");
        tuples.put("id", one);
        tuples.put("name", two);
        tuples.put("dob", three);
        student1.insert(tuples, 2);

        tuples.clear();
        one.clear();
        two.clear();
        three.clear();

        one.add("'John'");
        one.add("'Jake'");
        two.add("'1010'");
        two.add("'1011'");
        three.add(1500);
        three.add(2000);
        tuples.put("iname", one);
        tuples.put("id", two);
        tuples.put("salary", three);
        instructor1.insert(tuples, 2);

        //Test select
        HashMap<String, ArrayList<Object>> rows = student1.selectAll();

        for (HashMap.Entry<String, ArrayList<Object>> entry : rows.entrySet()) {
            ArrayList<Object> values = entry.getValue();
            for (Object obj : values) {
                System.out.print(obj + " ");
            }
            System.out.println();
        }

        System.out.println();

        rows.clear();

        HashMap<String, Object> where = new HashMap<>();
        where.put("salary", 1500);

        rows = instructor1.selectAll(where);

        for (HashMap.Entry<String, ArrayList<Object>> entry : rows.entrySet()) {
            ArrayList<Object> values = entry.getValue();
            for (Object obj : values) {
                System.out.print(obj + " ");
            }
            System.out.println();
        }

        System.out.println();

        //Test update
        HashMap<String, Object> updates = new HashMap<>();
        updates.put("salary", "2500");

        instructor1.update(updates, where);

        rows = instructor1.selectAll();

        for (HashMap.Entry<String, ArrayList<Object>> entry : rows.entrySet()) {
            ArrayList<Object> values = entry.getValue();
            for (Object obj : values) {
                System.out.print(obj + " ");
            }
            System.out.println();
        }

        System.out.println();

        //Test delete
        where.clear();
        where.put("salary", 2000);

        instructor1.delete(where);

        rows = instructor1.selectAll();

        for (HashMap.Entry<String, ArrayList<Object>> entry : rows.entrySet()) {
            ArrayList<Object> values = entry.getValue();
            for (Object obj : values) {
                System.out.print(obj + " ");
            }
            System.out.println();
        }
    }
}
//        }
//}
//
//public class Test {
//    public static void main(String[] args) throws MapperException, RulerException {
//        try {
//
//            //model file
//            Mapper mapper = new Mapper(new Student());
//
//            //controller file
//            Ruler ruler = new Ruler(new Student());
//            HashMap<String, Object> tuple = new HashMap<>();
//            tuple.put("name","'Vis'");
//            tuple.put("marks", 50.1);
//            ruler.insert(tuple);
//            tuple.clear();
//            tuple.put("name","'LOL'");
//            tuple.put("marks", 35.1);
//            ruler.insert(tuple);
//
//            HashMap<String, Object> updates = new HashMap<>();
//            updates.put("marks","100");
//
//            HashMap<String, Object> where = new HashMap<>();
//            where.put("name", "'Vis'");
//            where.put("marks", 50.1);
//
//            ruler.update(updates, where);
//
//            HashMap<String, ArrayList<Object>> row = ruler.selectAll();
//
//            for (HashMap.Entry<String, ArrayList<Object>> entry:row.entrySet()) {
//                ArrayList<Object> values = entry.getValue();
//                for (Object obj:values) {
//                    System.out.println(obj);
//                }
//            }
//
//            HashMap<String, Object> where1 = new HashMap<>();
//            where1.put("name", "'LOL'");
//            where1.put("marks", 35.1);
//
//            ruler.delete(where1);
//
//        } catch (InvalidFieldException e) {
//            System.out.println("Invalid Field");
//        }
//
////        Connection conn = DAO.getConnection();
//
//    }
//}
////
