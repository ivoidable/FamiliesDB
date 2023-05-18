package com.invoidable.familiesrelations;

public class Father extends Leader{

    Father(String name, int age, Gender gender, String phoneNumber) {
        super(name, age, gender, phoneNumber, true, true, Role.F);
    }
}
