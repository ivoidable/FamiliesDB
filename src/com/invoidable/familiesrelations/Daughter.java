package com.invoidable.familiesrelations;

public class Daughter extends Member{
    Daughter(String name, int age, Gender gender, String phoneNumber, Boolean mature, Boolean isLeader) {
        super(name, age, gender, phoneNumber, mature, isLeader, Role.D);
    }
}
