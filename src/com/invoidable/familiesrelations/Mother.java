package com.invoidable.familiesrelations;

public class Mother extends Member{
    Mother(String name, int age, Gender gender, String phoneNumber, Boolean isLeader) {
        super(name, age, gender, phoneNumber, true, isLeader);
    }
}
