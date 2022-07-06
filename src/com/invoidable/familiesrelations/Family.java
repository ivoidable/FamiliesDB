package com.invoidable.familiesrelations;

import java.util.ArrayList;

public class Family {

    public String familyName;

    public Member leader;

    public ArrayList<Member> members = new ArrayList<Member>();

    public int memberCount;

    public Family(String familyName, Member leader) {
        this.familyName = familyName;
        this.leader = leader;

        this.members.add(this.leader);
        this.memberCount = members.size();
    }

    public void addMember(Member member) {
        members.add(member);
        this.memberCount = members.size();
    }

    public void removeMember(int index) {
        members.remove(index);
        this.memberCount = members.size();
    }

    public void changeName(String name) {
        this.familyName = name;
    }

    public void changeLeader(Member member) {
        if (member.isMature) {
            for (int i = 0;i < this.members.size(); i++) {
                if (member == this.members.get(i)) {
                    this.leader = member;
                    System.out.println("Assigned Member: " + member.name + " As New Leader For Family: " + familyName);
                    break;
                }
            }
            System.out.println("Provided Member Is NOT Part Of Family: " + familyName);

        } else {
            System.out.println("ERR: Member not mature!");
        }
        this.memberCount = members.size();
    }

}
