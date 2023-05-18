package com.invoidable.familiesrelations;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FamiliesRelations {

    public static final OkHttpPro http = new OkHttpPro();
    public static final String dbFileName = ".\\FamiliesDB.json";
    public static ArrayList<Family> families;

    public static void main(String[] args) throws Exception {
        families = new ArrayList<>();
        saveLoad(1);

        while (true) {
            System.out.print("\033[H\033[2J");
            System.out.flush();
            menu();
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
        System.out.println("7- Save");
        System.out.println("0- Exit");
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
            case 7: saveLoad(0); saveLoad(1);break;
            case 0: System.exit(0);break;
            default: System.out.println("Wrong Input!"); menu();break;
        }
    }

    public static void saveLoad(int state) throws Exception {
        if (state == 0) {
            FileWriter out = null;
            try {
                out = new FileWriter(dbFileName);
                String famString = new Gson().toJson(families);
                out.write(famString);
                System.out.println(ConsoleColors.ANSI_PURPLE + "<- Saved Families ->" + ConsoleColors.ANSI_RESET);
            } catch (Exception e) {
                System.out.println(e);
            } finally {
                if (out != null) {
                    out.close();
                }
            }
        }
        else if (state == 1) {
            File db = new File(dbFileName);
            if (!db.exists()){
                return;
            }
            BufferedReader reader = new BufferedReader(new FileReader(dbFileName));
            try {
                StringBuilder builder = new StringBuilder();
                String line = null;
                String ls = System.getProperty("line.separator");
                while ((line = reader.readLine()) != null)
                {
                    builder.append(line);
                    builder.append(ls);
                }
                // Deleting the last line separator
                builder.deleteCharAt(builder.length() - 1);
                reader.close();
                String famString = builder.toString();
                JSONArray tempArray = new JSONArray(famString);
                Type listType = new TypeToken<ArrayList<Family>>(){}.getType();
                List<Family> myFamilyList = new Gson().fromJson(String.valueOf(tempArray), listType);
                families = new ArrayList<Family>(myFamilyList);
                System.out.println(ConsoleColors.ANSI_PURPLE + "<- Reading Families ->" + ConsoleColors.ANSI_RESET);

            } catch (Exception e)
            {
                System.out.println(e);
            }
        }
    }

    private static void listFamilies() {
        if (families.isEmpty()){
            System.out.println(ConsoleColors.ANSI_PURPLE + "<- No Available Families ->" + ConsoleColors.ANSI_RESET);
            System.out.println("Press Enter To Continue...");
            Scanner sc = new Scanner(System.in);
            sc.nextLine();
            return;
        }
        System.out.println(ConsoleColors.ANSI_PURPLE + "<- List Of Families ->" + ConsoleColors.ANSI_RESET);

        for (Family fam : families) {
            System.out.println(ConsoleColors.ANSI_YELLOW + "<- Members of: " + ConsoleColors.ANSI_CYAN + fam.familyName + " - " + fam.relativityLevel + ConsoleColors.ANSI_YELLOW + " -> \n" + ConsoleColors.ANSI_RESET);

            for (Member mem : fam.members) {
                System.out.println(ConsoleColors.ANSI_YELLOW + "    " + "- " + "Name: " + ConsoleColors.ANSI_CYAN + mem.name + ConsoleColors.ANSI_YELLOW + " Age: " + ConsoleColors.ANSI_CYAN + mem.age +
                        ConsoleColors.ANSI_YELLOW + " Gender: " + ConsoleColors.ANSI_CYAN + mem.gender + ConsoleColors.ANSI_YELLOW + " Is Mature: " + ConsoleColors.ANSI_CYAN + mem.isMature()+ ConsoleColors.ANSI_YELLOW + " Role: " + ConsoleColors.ANSI_CYAN + mem.role + ConsoleColors.ANSI_RESET);

                System.out.println();
            }
            System.out.println(ConsoleColors.ANSI_YELLOW + "<- End of: " + ConsoleColors.ANSI_CYAN + fam.familyName + ConsoleColors.ANSI_YELLOW + " ->" + ConsoleColors.ANSI_RESET);
        }
        System.out.println("Press Enter To Continue...");
        Scanner sc = new Scanner(System.in);
        sc.nextLine();
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
        System.out.println("5- Toggle Maturity (Currently: " + families.get(index).members.get(memberIndex).isMature() + ")");
        System.out.println("0- Exit");
        System.out.println();
        System.out.print("Choice: ");

        int state = sc.nextInt();

        modifyProps(families.get(index).members.get(memberIndex), state);
        try {
            saveLoad(0);
        } catch (Exception e) {
            System.out.print(e);
        }
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
                System.out.println("Current Name: " + member.name);
                System.out.print("New Name: ");
                name = sc.nextLine();
                member.name = name.toString();
                break;
            case 2:
                System.out.println("Current Age: " + member.age);
                System.out.print("New Age: ");
                age = sc.nextInt();
                sc.nextLine();
                member.age = age;
                break;
            case 3:
                System.out.println("Current Gender: " + member.gender);
                System.out.print("New Gender (M) or (F): ");
                member.gender = ((sc.next().charAt(0) == 'm' || sc.next().charAt(0) == 'M') ? Gender.M : Gender.F);
                break;
            case 4:
                System.out.println("Current Phone No.: " + member.phoneNumber);
                System.out.print("New Phone No.: ");
                phoneNumber = sc.nextLine();
                member.phoneNumber = phoneNumber.toString();
                break;
            case 5:
                if (!member.isLeader) {
                    member.mature = !member.isMature();
                } else {
                    System.out.println("Can't Make A Leader Immature");
                }
                break;
            case 0:
                break;

        }
        try {
            saveLoad(0);
        } catch (Exception e) {
            System.out.print(e);
        }
    }

    private static void removeMemberFromFamily() {
        System.out.println(ConsoleColors.ANSI_YELLOW + "<-- Choose A Family -->" + ConsoleColors.ANSI_RESET);
        for (int i = 0; i < families.size(); i++) {
            System.out.println(ConsoleColors.ANSI_CYAN + i + "- " + families.get(i).familyName + ConsoleColors.ANSI_RESET);
        }
        System.out.print("Choice: ");
        Scanner sc = new Scanner(System.in);
        int choice = sc.nextInt();
        Family fam = families.get(choice);

        System.out.println(ConsoleColors.ANSI_YELLOW + "<-- Choose A Member -->" + ConsoleColors.ANSI_RESET);
        for (int i = 0; i < fam.members.size(); i++) {
            System.out.println(ConsoleColors.ANSI_YELLOW + "    " + i + "- " + "Name: " + ConsoleColors.ANSI_CYAN + fam.members.get(i).name + ConsoleColors.ANSI_YELLOW + " Age: " + ConsoleColors.ANSI_CYAN + fam.members.get(i).age +
                    ConsoleColors.ANSI_YELLOW + " Gender: " + ConsoleColors.ANSI_CYAN + fam.members.get(i).gender + ConsoleColors.ANSI_YELLOW + " Is Mature: " + ConsoleColors.ANSI_CYAN + fam.members.get(i).isMature()+ ConsoleColors.ANSI_YELLOW + " Role: " + ConsoleColors.ANSI_CYAN + fam.members.get(i).role + ConsoleColors.ANSI_RESET);
            System.out.println();
        }
        System.out.print("Choice: ");
        int index = sc.nextInt();
        fam.members.remove(index);
        System.out.println(ConsoleColors.ANSI_GREEN + "<-- Successfully Deleted Member -->" + ConsoleColors.ANSI_RESET);
        sc.nextLine();
        try {
            saveLoad(0);
        } catch (Exception e) {
            System.out.print(e);
        }
    }

    private static void removeFamily() {
        if (families.size() == 0){
            System.out.println(ConsoleColors.ANSI_RED + "<-- No Families To Remove From! -->" + ConsoleColors.ANSI_RESET);
            return;
        }
        Scanner sc = new Scanner(System.in);
        for (int i = 0; i < families.size(); i++) {
            System.out.println(i + "- " + families.get(i).familyName);
        }

        int index = sc.nextInt();
        sc.nextLine();
        String famName = families.get(index).familyName;
        families.remove(index);
        System.out.println(ConsoleColors.ANSI_GREEN + "<- Removed Family: " + famName + " ->" + ConsoleColors.ANSI_RESET);
        try {
            saveLoad(0);
        } catch (Exception e) {
            System.out.print(e);
        }
    }

    private static void addMemberToFamily() {
        Scanner sc = new Scanner(System.in);

        for (int i = 0; i < families.size(); i++) {
            System.out.println(i + "- " + families.get(i).familyName);
        }

        int index = sc.nextInt();

        Member member = newMember();

        families.get(index).addMember(member);
        try {
            saveLoad(0);
        } catch (Exception e) {
            System.out.print(e);
        }
    }

    private static Member newMember() {
        Scanner sc = new Scanner(System.in);

        System.out.println(ConsoleColors.ANSI_PURPLE + "<- New Member ->" + ConsoleColors.ANSI_RESET);
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

        System.out.println(ConsoleColors.ANSI_PURPLE + "<- Create New Family ->" + ConsoleColors.ANSI_RESET);

        System.out.print("Family Name: ");
        String familyName = sc.nextLine();

        System.out.print("Relativity Level (0 - 5): ");
        int relativityLevel = sc.nextInt();

        Member leader = createLeader();

        Family newFamily = new Family(familyName, leader, relativityLevel);

        families.add(newFamily);

        System.out.println(ConsoleColors.ANSI_GREEN + "<- Created New Family ->" + ConsoleColors.ANSI_RESET);
        System.out.println("Family Name: " + newFamily.familyName);
        System.out.println("Leader:  " + newFamily.leader.name);
        try {
            saveLoad(0);
        } catch (Exception e) {
            System.out.print(e);
        }
    }

    private static Member createLeader() {
        Scanner sc = new Scanner(System.in);

        System.out.println(ConsoleColors.ANSI_PURPLE + "<- Create Family Leader ->" + ConsoleColors.ANSI_RESET);

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
