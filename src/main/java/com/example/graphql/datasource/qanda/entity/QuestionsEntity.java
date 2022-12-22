package com.example.graphql.datasource.qanda.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "questions")
@Getter
@Setter
public class QuestionsEntity {
    @Id
    private UUID id;
    private String title;
    private String content;
    private String tags;
    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "question")
    private List<AnswersEntity> answers;

    @ManyToOne
    @JoinColumn(name = "created_by", nullable = false)
    private UsersEntity createdBy;
}
