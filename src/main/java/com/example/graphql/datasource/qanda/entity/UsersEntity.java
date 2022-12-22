package com.example.graphql.datasource.qanda.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "users")
public class UsersEntity {
    @Id
    private UUID id;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private URL avatar;
    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "is_active")
    private Boolean isActive;

    @OneToMany(mappedBy = "createdBy")
    private List<QuestionsEntity> questions;

    @OneToMany(mappedBy = "createdBy")
    private List<AnswersEntity> answers;

}
