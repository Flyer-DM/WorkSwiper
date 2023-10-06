package com.example.workswiper.Domains;

import com.example.workswiper.User.User;
import jakarta.persistence.*;

@Entity
@Table(name = "link")
public class Link {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    @Column(name = "text", nullable = false, length = 64)
    private String text;

    @Column(name = "link", nullable = false, length = 2048)
    private String link;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user_id;

    public Link() {
    }

    public Link(String text, String link, User user_id) {
        this.text = text;
        this.link = link;
        this.user_id = user_id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

}
