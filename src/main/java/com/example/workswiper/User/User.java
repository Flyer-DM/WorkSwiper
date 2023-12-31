package com.example.workswiper.User;

import com.example.workswiper.Domains.FirstTime;
import com.example.workswiper.Domains.Task;
import com.example.workswiper.Domains.Techstack;
import jakarta.persistence.*;

import java.util.Collection;

@Entity
@Table(name = "user", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Collection<Role> roles;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id", referencedColumnName = "user_id")
    private FirstTime firstTime;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_tech", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "tech_id", referencedColumnName = "id"))
    private Collection<Techstack> techstacks;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "task_seen", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "task_id", referencedColumnName = "id"))
    private Collection<Task> task_seen;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "task_stared", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "task_id", referencedColumnName = "id"))
    private Collection<Task> task_stared;


    public Collection<Task> getTask_seen() {
        return task_seen;
    }

    public void setTask_seen(Collection<Task> task_seen) {
        this.task_seen = task_seen;
    }

    public Collection<Task> getTask_stared() {
        return task_stared;
    }

    public void setTask_stared(Collection<Task> task_stared) {
        this.task_stared = task_stared;
    }

    public User() {
    }

    public User(String firstName, String lastName, String email, String password, Collection<Role> roles) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }


    public User(Long studentId) {
        this.id = studentId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public FirstTime getFirstTime() {
        return firstTime;
    }

    public void setFirstTime(FirstTime firstTime) {
        this.firstTime = firstTime;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }

    public Collection<Techstack> getTechstacks() {
        return techstacks;
    }

    public void setTechstacks(Collection<Techstack> techstacks) {
        this.techstacks = techstacks;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", roles=" + roles +
                ", techstacks=" + techstacks +
                '}';
    }
}