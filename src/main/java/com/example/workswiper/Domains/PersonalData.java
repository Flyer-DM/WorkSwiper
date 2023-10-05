package com.example.workswiper.Domains;

import com.example.workswiper.User.User;
import jakarta.persistence.*;

@Entity
@Table(name = "personaldata")
public class PersonalData {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    @Column(name = "patronymic")
    private String patronymic;

    @Column(name = "age")
    private Long age;

    @Column(name = "country")
    private String country;

    @Column(name = "city")
    private String city;

    @Column(name = "education")
    private String education;

    @Column(name = "aboutme")
    private String aboutme;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user_id;

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public Long getAge() {
        return age;
    }

    public void setAge(Long age) {
        this.age = age;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getAboutme() {
        return aboutme;
    }

    public void setAboutme(String aboutme) {
        this.aboutme = aboutme;
    }

    public User getUser() {
        return user_id;
    }

    public void setUser(User user_id) {
        this.user_id = user_id;
    }

    @Override
    public String toString() {
        return "PersonalData{" +
                "id=" + id +
                ", patronymic='" + patronymic + '\'' +
                ", age=" + age +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", education='" + education + '\'' +
                ", aboutme='" + aboutme + '\'' +
                ", FirstName=" + user_id.getFirstName() +
                ", LastName=" + user_id.getLastName() +
                ", LastName=" + user_id.getEmail() +
                '}';
    }
}
