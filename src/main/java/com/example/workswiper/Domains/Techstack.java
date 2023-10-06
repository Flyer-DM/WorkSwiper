package com.example.workswiper.Domains;

import jakarta.persistence.*;

@Entity
@Table(name = "techstack", uniqueConstraints = @UniqueConstraint(columnNames = "technology"))
public class Techstack {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    @Column(name = "technology", nullable = false)
    private String technology;

    public Techstack() {
    }

    public Techstack(String technology) {
        this.technology = technology;
    }

    public String getTechnology() {
        return technology;
    }

    public void setTechnology(String technology) {
        this.technology = technology;
    }
}
