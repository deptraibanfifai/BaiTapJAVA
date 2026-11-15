package com.fpt.model;

import java.io.Serializable;

public class Pet implements Serializable { // Phải thực thi Serializable 
    private String name; // [cite: 20]
    private String type; // [cite: 20]
    private int age;     // [cite: 20]

    public Pet(String name, String type, int age) { // Constructor [cite: 21]
        this.name = name;
        this.type = type;
        this.age = age;
    }

    public void makeNoise() { // Phương thức in ra tiếng kêu [cite: 22]
        System.out.println(name + " " + type + " says meow");
    }

    @Override
    public String toString() { // Ghi đè phương thức toString [cite: 23, 24, 25, 26, 27]
        return "Pet [type=" + type + ", name=" + name + ", age=" + age + "]";
    }
}