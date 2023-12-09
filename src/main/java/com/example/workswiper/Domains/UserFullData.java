package com.example.workswiper.Domains;

import com.example.workswiper.User.User;

import java.util.List;

public class UserFullData {

    private User user;

    private PersonalData personalData;

    private String techstackList;

    private List<Link> linkList;

    private Task taskLiked;

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

    public String getTechstackList() {
        return techstackList;
    }

    public void setTechstackList(String techstackList) {
        this.techstackList = techstackList;
    }

    public List<Link> getLinkList() {
        return linkList;
    }

    public void setLinkList(List<Link> linkList) {
        this.linkList = linkList;
    }

    public Task getTaskLiked() {
        return taskLiked;
    }

    public void setTaskLiked(Task taskLiked) {
        this.taskLiked = taskLiked;
    }

    @Override
    public String toString() {
        return "UserFullData{" +
                "user=" + user +
                '}';
    }
}
