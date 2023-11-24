package com.example.workswiper.Domains;

import com.example.workswiper.User.User;
import jakarta.persistence.*;

import java.sql.Blob;

@Entity
@Table(name = "avatar")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Lob
    @Column(name = "image", nullable = false)
    private Blob image;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user_id;

    public Image() {
    }

    public Image(User user_id, Blob image) {
        this.image = image;
        this.user_id = user_id;
    }

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public Blob getImage() {
        return image;
    }
    public void setImage(Blob image) {
        this.image = image;
    }

}