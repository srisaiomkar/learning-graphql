package com.example.graphql.datasource.qanda.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "answers")
@Getter
@Setter
public class AnswersEntity {
    @Id
    private UUID id;
    private String content;
    private String category;
    @Column(name = "up_votes")
    private Integer upVotes;
    @Column(name = "down_votes")
    private Integer downVotes;
    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "question_id",nullable = false)
    private QuestionsEntity question;

    @ManyToOne
    @JoinColumn(name="created_by", nullable = false)
    private UsersEntity createdBy;
}
