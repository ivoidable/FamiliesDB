package com.invoidable.familiesrelations;

public class Leader extends Member{

    String job;

    double salary;

    String email;

    Leader(String name, int age, Gender gender, String phoneNumber, boolean mature, boolean isLeader, Role role) {
        super(name, age, gender, phoneNumber, mature, isLeader, role);
    }
}
