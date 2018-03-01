package test;

import database.DAO;
import fields.*;

import mapper.InvalidFieldException;
import mapper.Mapper;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.Time;
import java.text.ParseException;

class Student {

//    TimeField name;
    VarcharField name = new VarcharField( 5, true, true);
    NumericField name1 = new NumericField( 5, 5, true, true);
    DateField d1;


    {
        try {
            d1 = new DateField("yyyy-MM-dd", "2012-06-23", true, true);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}

///Testing

//solves two things:
    //1. Programmer can't go wrong with field mismatches
    //2. Have different names for foreign keys!


class Instructor {
    NumericField iid = new NumericField(3, 2, true, true);
    ForeignKeyField name = new ForeignKeyField(new Student());
}


public class Test {
    public static void main(String[] args) {
        try {
            Mapper mapper = new Mapper(new Student());
            Mapper m2 = new Mapper(new Instructor());
        } catch (InvalidFieldException e) {
            System.out.println("Invalid Field");
        }

//        Connection conn = DAO.getConnection();

    }
}

