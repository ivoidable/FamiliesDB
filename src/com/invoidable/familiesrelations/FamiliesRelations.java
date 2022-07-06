package com.invoidable.familiesrelations;

import java.util.ArrayList;
import java.util.Scanner;

public class FamiliesRelations {

    public static final OkHttpPro http = new OkHttpPro();
    public static ArrayList<Family> families;

    public static void main(String[] args) throws Exception {
        families = new ArrayList<>();
        saveLoad(1);

        while (true) {
            menu();
            System.out.print("\033[H\033[2J");
            System.out.flush();
        }
    }

    public static void menu() throws Exception {
        System.out.println("================================");
        System.out.println("1- add family");
        System.out.println("2- add member to a family");
        System.out.println("3- remove family");
        System.out.println("4- remove member from a family");
        System.out.println("5- modify member in family");
        System.out.println("6- list families");
        System.out.println("0- Save");
        System.out.println("================================");
        Scanner sc = new Scanner(System.in);

        System.out.print("Choice: ");
        int choice = sc.nextInt();

        switch (choice) {
            case 1: addFamily(); break;
            case 2: addMemberToFamily(); break;
            case 3: removeFamily(); break;
            case 4: removeMemberFromFamily(); break;
            case 5: modify(); break;
            case 6: listFamilies(); break;
            case 0: saveLoad(0); saveLoad(1);break;
            default: System.out.println("Wrong Input!"); menu();break;
        }
    }

    public static void saveLoad(int state) throws Exception {
        if (state == 0) {
            for (Family value : families) {
                http.sendPost(value);
                Thread.sleep(500);
            }
        } else if (state == 1) {
            families = http.sendGet();
        }
    }

    private static void listFamilies() {
        System.out.println("<- List Of Families ->");

        for (int i = 0; i < families.size(); i++) {
            System.out.println((i + 1) + "- " + families.get(i).familyName);
            System.out.println("<- Members of: " + families.get(i).familyName + " -> \n");

            for (int n = 0; n < families.get(i).members.size(); n++) {
                System.out.println("    " + (n + 1) + "- " + "Name: " + families.get(i).members.get(n).name + " Age: " + families.get(i).members.get(n).age +
                         " Gender: " + families.get(i).members.get(n).gender + " Is Mature: " + families.get(i).members.get(n).isMature);

                System.out.println();
            }
            System.out.println("<- End of: " + families.get(i).familyName + " ->");
        }
    }

    private static void modify() {
        System.out.println("<- Choose Family to modify, boss ->");

        System.out.println("<-------------------------->");
        for (int i = 0; i < families.size(); i++) {
            System.out.println(i + "- " + families.get(i).familyName);
        }
        System.out.println("<-------------------------->");

        Scanner sc = new Scanner(System.in);

        int index = sc.nextInt();
        sc.nextLine();

        System.out.println("<-------------------------->");
        for (int n = 0; n < families.get(index).memberCount; n++) {
            System.out.println(n + "- " + families.get(index).members.get(n).name);
        }
        System.out.println("<-------------------------->");

        int memberIndex = sc.nextInt();
        sc.nextLine();

        System.out.println("<- Editing Member:" + families.get(index).members.get(memberIndex).name + "->");

        System.out.println("1- Change Name");
        System.out.println("2- Change Age");
        System.out.println("3- Change Gender");
        System.out.println("4- Change Phone Number");
        System.out.println("5- Toggle Maturity (Currently: " + families.get(index).members.get(memberIndex).isMature + ")");
        System.out.println("0- Exit");
        System.out.println();
        System.out.print("Choice: ");

        int state = sc.nextInt();

        modifyProps(families.get(index).members.get(memberIndex), state);
    }

    private static void modifyProps(Member member, int state) {
        Scanner sc = new Scanner(System.in);

        String name;
        int age;
        Gender gender;
        String phoneNumber;
        Boolean isMature;

        switch (state) {
            case 1:
                name = sc.nextLine();
                member.name = name.toString();
                break;
            case 2:
                age = sc.nextInt();
                sc.nextLine();
                member.age = age;
                break;
            case 3:
                member.gender = ((sc.next().charAt(0) == 'm' || sc.next().charAt(0) == 'M') ? Gender.M : Gender.F);
                break;
            case 4:
                phoneNumber = sc.nextLine();
                member.phoneNumber = phoneNumber.toString();
                break;
            case 5:
                if (!member.isLeader) {
                    member.isMature = !member.isMature;
                } else {
                    System.out.println("Can't Make A Leader Immature");
                }
                break;
            case 0:
                break;

        }
    }

    private static void removeMemberFromFamily() {
        for (int i = 0; i < families.size(); i++) {
            System.out.println();
        }
    }

    private static void removeFamily() {
        Scanner sc = new Scanner(System.in);
        for (int i = 0; i < families.size(); i++) {
            System.out.println(i + "- " + families.get(i).familyName);
        }

        int index = sc.nextInt();
        sc.nextLine();

        families.remove(index);
        System.out.println("<- Removed Family: " + families.get(index).familyName + " ->");

    }

    private static void addMemberToFamily() {
        Scanner sc = new Scanner(System.in);

        for (int i = 0; i < families.size(); i++) {
            System.out.println(i + "- " + families.get(i).familyName);
        }

        int index = sc.nextInt();

        Member member = newMember();

        families.get(index).addMember(member);
    }

    private static Member newMember() {
        Scanner sc = new Scanner(System.in);

        System.out.println("<- New Member ->");
        System.out.print("Name: ");
        String name = sc.nextLine();

        System.out.print("gender (m) or (f): ");
        char genderLetter = sc.next().charAt(0);
        Gender gender = ((genderLetter == 'm') ? Gender.M : Gender.F);

        sc.nextLine();

        System.out.print("Phone Number (Nullable): ");
        String phoneNumber = sc.nextLine();

        System.out.print("Is Mature (t) or (f): ");
        char mature = sc.next().charAt(0);
        Boolean isMature = (mature == 't');

        System.out.print("Age: ");
        int age = sc.nextInt();

        sc.nextLine();

        System.out.println("Role (d), (s), (m), (f): ");
        char roleLetter = sc.next().charAt(0);
        Role role;
        if (roleLetter == 'd') {
            role = Role.D;
        } else if (roleLetter == 's') {
            role = Role.S;
        } else if (roleLetter == 'm') {
            role = Role.M;
        } else {
            role = Role.F;
        }

        Member member;

        if (role == Role.F) {
            member = new Father(name, age, gender, phoneNumber);
        } else if (role == Role.M) {
            member = new Mother(name, age, gender, phoneNumber, false);
        } else if (role == Role.S) {
            member = new Son(name, age, gender, phoneNumber, isMature, false);
        } else {
            member = new Daughter(name, age, gender, phoneNumber, isMature, false);
        }

        return member;
    }

    private static void addFamily() {
        Scanner sc = new Scanner(System.in);

        System.out.println("<- Create New Family ->");

        System.out.print("Family Name: ");
        String familyName = sc.nextLine();

        Member leader = createLeader();

        Family newFamily = new Family(familyName, leader);

        families.add(newFamily);

        System.out.println("<- Created New Family ->");
        System.out.println("Family Name: " + newFamily.familyName);
        System.out.println("Leader:  " + newFamily.leader.name);
    }

    private static Member createLeader() {
        Scanner sc = new Scanner(System.in);

        System.out.println("<- Create Family Leader ->");

        System.out.print("Name: ");
        String name = sc.nextLine();

        System.out.print("gender (m) or (f): ");
        char genderLetter = sc.next().charAt(0);
        Gender gender = ((genderLetter == 'm') ? Gender.M : Gender.F);

        sc.nextLine();

        System.out.print("phone number (with key): ");
        String phoneNumber = sc.nextLine();

        System.out.print("Age: ");
        int age = sc.nextInt();
        sc.nextLine();

        return ((gender == Gender.M) ?  new Father(name, age, gender, phoneNumber) :  new Mother(name, age, gender, phoneNumber, true));
    }

}
