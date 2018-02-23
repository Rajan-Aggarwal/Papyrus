package test;

import fields.ColumnField;
import fields.VarcharField;
import mapper.InvalidFieldException;
import mapper.Mapper;

import java.lang.reflect.Field;

class Student {
    VarcharField name = new VarcharField(10, "Rajan", false, true);
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
