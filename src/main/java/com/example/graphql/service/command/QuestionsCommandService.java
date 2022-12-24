package com.example.graphql.service.command;

import com.example.graphql.datasource.qanda.entity.QuestionsEntity;
import com.example.graphql.datasource.qanda.entity.UsersEntity;
import com.example.graphql.datasource.qanda.repository.QuestionsRepository;
import com.example.graphql.generated.types.QuestionCreateInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.UUID;

@Service
public class QuestionsCommandService {

    @Autowired
    private QuestionsRepository questionsRepository;

    public QuestionsEntity createQuestion(QuestionCreateInput input, UsersEntity usersEntity){
        var questionsEntity = new QuestionsEntity();
        questionsEntity.setId(UUID.randomUUID());
        questionsEntity.setTags(String.join(",",input.getTags()));
        questionsEntity.setCreatedBy(usersEntity);
        questionsEntity.setTitle(input.getTitle());
        questionsEntity.setContent(input.getContent());
        questionsEntity.setAnswers(Collections.emptyList());

        return questionsRepository.save(questionsEntity);
    }
}
