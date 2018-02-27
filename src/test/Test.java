package test;

import fields.*;

import mapper.InvalidFieldException;
import mapper.Mapper;

import java.lang.reflect.Field;
import java.sql.Time;
import java.text.ParseException;

class Student {

//    TimeField name;
    VarcharField name = new VarcharField( 5,"LOL", false, true, true);
    NumericField name1 = new NumericField( 5, 5, 4.5, false, true);
    DateField d1;
    TimeField t1;

    {
        try {
            d1 = new DateField("2017-03-17", false, true);
            t1 = new TimeField(false, true);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}

public class Test {
    public static void main(String[] args) {
        try {
            Mapper mapper = new Mapper(new Student());
        } catch (InvalidFieldException e) {
            System.out.println("Invalid Field");
        }
    }
}

