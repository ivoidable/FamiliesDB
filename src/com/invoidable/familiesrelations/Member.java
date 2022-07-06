package com.invoidable.familiesrelations;

public class Member {

    String name;

    int age;

    Gender gender;

    String phoneNumber;

    boolean isMature;

    boolean isLeader;

    Member(String name, int age, Gender gender, String phoneNumber, Boolean isMature, boolean isLeader) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.isMature = isMature;
        this.isLeader = isLeader;
    }
}
