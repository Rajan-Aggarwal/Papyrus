package test;

import fields.ColumnField;
import fields.DateField;
import fields.TimeField;
import fields.VarcharField;
import mapper.InvalidFieldException;
import mapper.Mapper;

import java.lang.reflect.Field;
import java.sql.Time;
import java.text.ParseException;

class Student {

    TimeField name;

    {
        try {
            name = new TimeField("DOB", false, true, true);
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
