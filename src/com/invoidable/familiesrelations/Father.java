package com.invoidable.familiesrelations;

public class Father extends Member{

    String job;

    double salary;

    String email;



    Father(String name, int age, Gender gender, String phoneNumber) {
        super(name, age, gender, phoneNumber, true, true);
    }
}
