package test;

import mapper.Mapper;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

class Student {
    Integer id;
    String name;
}


public class Test {
    public static void main(String[] args) {
        Mapper mapper = new Mapper(new Student());
        HashMap<String, Class> map = mapper.getFieldMap();
        for (Map.Entry<String, Class> entry : map.entrySet()){
            System.out.println(entry.getKey() + " " + entry.getValue().getCanonicalName());
        }
    }
}
