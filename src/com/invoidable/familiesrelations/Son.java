package com.invoidable.familiesrelations;

public class Son extends Member{
    Son(String name, int age, Gender gender, String phoneNumber, Boolean isMature,Boolean isLeader) {
        super(name, age, gender, phoneNumber, isMature, isLeader, Role.S);
    }
}
