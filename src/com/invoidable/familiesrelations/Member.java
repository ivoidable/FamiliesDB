package com.invoidable.familiesrelations;

import java.util.Date;

public class Member {

    String name;

    int age;

    Gender gender;

    String phoneNumber;

    Date birthday;
    boolean mature;

    boolean isLeader;
    Role role;

    Member(String name, int age, Gender gender, String phoneNumber, boolean mature, boolean isLeader, Role role) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.isLeader = isLeader;
        this.mature = mature;
        this.role = role;
    }

    public boolean isMature()
    {
        return mature;
    }
}