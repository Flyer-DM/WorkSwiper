package com.example.workswiper.Domains;

import com.example.workswiper.User.User;

import java.util.List;

public class UserFullData {

    private User user;

    private PersonalData personalData;

    private List<Techstack> techstackList;

    private List<Link> linkList;

    public UserFullData(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public PersonalData getPersonalData() {
        return personalData;
    }

    public void setPersonalData(PersonalData personalData) {
        this.personalData = personalData;
    }

    public List<Techstack> getTechstackList() {
        return techstackList;
    }

    public void setTechstackList(List<Techstack> techstackList) {
        this.techstackList = techstackList;
    }

    public List<Link> getLinkList() {
        return linkList;
    }

    public void setLinkList(List<Link> linkList) {
        this.linkList = linkList;
    }
}
