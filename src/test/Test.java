package test;

import fields.*;

import mapper.InvalidFieldException;
import mapper.InvalidTableDescriptionException;
import mapper.Mapper;
import ruler.*;
import scroll.Scroll;

import java.util.ArrayList;
import java.util.HashMap;

class Student extends Scroll {

//    TimeField name;
    VarcharField name = new VarcharField( 5, true, true);
    NumericField marks = new NumericField( 10, 6, true, false);
//    DateField d1;
//
//
//    {
//        try {
//            d1 = new DateField("yyyy-MM-dd", "2012-06-23", true, false);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//    }
}

///Testing

//solves two things:
    //1. Programmer can't go wrong with field mismatches
    //2. Have different names for foreign keys!


class Instructor extends Scroll {
    NumericField iid = new NumericField(3, 2, true, true);
    ForeignKeyField name = new ForeignKeyField(new Student());
}


public class Test {
    public static void main(String[] args) throws InvalidTableDescriptionException, RulerException {
        try {

            //model file
            Mapper mapper = new Mapper(new Student());

            //controller file
            Ruler ruler = new Ruler(new Student());
            HashMap<String, Object> tuple = new HashMap<>();
            tuple.put("name","'Vis'");
            tuple.put("marks", 50.1);
            ruler.insert(tuple);
            tuple.clear();
            tuple.put("name","'LOL'");
            tuple.put("marks", 35.1);
            ruler.insert(tuple);

            HashMap<String, Object> updates = new HashMap<>();
            updates.put("marks","100");

            HashMap<String, Object> where = new HashMap<>();
            where.put("name", "'Vis'");
            where.put("marks", 50.1);

            ruler.update(updates, where);

            HashMap<String, ArrayList<Object>> row = ruler.selectAll();

            for (HashMap.Entry<String, ArrayList<Object>> entry:row.entrySet()) {
                ArrayList<Object> values = entry.getValue();
                for (Object obj:values) {
                    System.out.println(obj);
                }
            }

            HashMap<String, Object> where1 = new HashMap<>();
            where1.put("name", "'LOL'");
            where1.put("marks", 35.1);

            ruler.delete(where1);

        } catch (InvalidFieldException e) {
            System.out.println("Invalid Field");
        }

//        Connection conn = DAO.getConnection();

    }
}

