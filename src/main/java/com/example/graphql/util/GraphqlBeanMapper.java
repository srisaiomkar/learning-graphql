package com.example.graphql.util;


import com.example.graphql.datasource.qanda.entity.AnswersEntity;
import com.example.graphql.datasource.qanda.entity.QuestionsEntity;
import com.example.graphql.datasource.qanda.entity.UsersEntity;
import com.example.graphql.datasource.qanda.repository.QuestionsRepository;
import com.example.graphql.generated.types.*;
import org.apache.commons.lang3.StringUtils;
import org.ocpsoft.prettytime.PrettyTime;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.ZoneOffset;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class GraphqlBeanMapper {
    private static final PrettyTime PRETTY_TIME = new PrettyTime();
    private static final ZoneOffset ZONE_OFFSET = ZoneOffset.ofHours(-5);

    public static User mapToGraphql(UsersEntity usersEntity){
        var user = new User();
        var createdDateTime = usersEntity.getCreatedAt().atOffset(ZONE_OFFSET);
//        var questions = usersEntity.getQuestions().stream()
//                                .map(questionsEntity -> mapToGraphql(questionsEntity))
//                                .collect(Collectors.toList());
        user.setId(usersEntity.getId().toString());
        user.setFirstName(usersEntity.getFirstname());
        user.setLastName(usersEntity.getLastname());
        user.setEmail(usersEntity.getEmail());
        user.setAvatar(usersEntity.getAvatar());
        user.setCreatedDateTime(createdDateTime);
//        user.setQuestions(questions);

        return user;
    }

    public static Question mapToGraphql(QuestionsEntity questionsEntity){
        var question = new Question();

        var createdDateTime = questionsEntity.getCreatedAt().atOffset(ZONE_OFFSET);
        var answers = questionsEntity.getAnswers().stream()
//                        .sorted(Comparator.comparing(AnswersEntity::getCreatedAt).reversed())
                        .map(GraphqlBeanMapper::mapToGraphql)
                        .collect(Collectors.toList());
        question.setId(questionsEntity.getId().toString());
        question.setContent(questionsEntity.getContent());
        question.setTitle(questionsEntity.getTitle());
        question.setTags(List.of(questionsEntity.getTags().split(",")));
        question.setAnswers(answers);
        question.setAuthor(mapToGraphql(questionsEntity.getCreatedBy()));
        question.setCreatedDateTime(createdDateTime);
        question.setPrettyCreatedDateTime(PRETTY_TIME.format(createdDateTime));
        question.setNumOfSolutions(answers.size());
        return question;
    }

    public static Answer mapToGraphql(AnswersEntity answersEntity){
        var answer = new Answer();
        var createdDateTime = answersEntity.getCreatedAt().atOffset(ZONE_OFFSET);
        var category = StringUtils
                .equalsIgnoreCase(answersEntity.getCategory(), AnswerCategory.EXPLANATION.toString())?
                AnswerCategory.EXPLANATION: AnswerCategory.REFERENCE;
        answer.setId(answersEntity.getId().toString());
        answer.setAuthor(mapToGraphql(answersEntity.getCreatedBy()));
        answer.setContent(answersEntity.getContent());
        answer.setCreatedDateTime(createdDateTime);
        answer.setUpVotes(answersEntity.getUpVotes());
        answer.setDownVotes(answersEntity.getDownVotes());
        answer.setPrettyCreatedDateTime(PRETTY_TIME.format(createdDateTime));
        answer.setCategory(category);
        return answer;
    }

    public static QuestionsEntity mapInputToEntity(QuestionCreateInput input, UsersEntity usersEntity){
        var questionsEntity = new QuestionsEntity();
        questionsEntity.setId(UUID.randomUUID());
        questionsEntity.setTags(String.join(",",input.getTags()));
        questionsEntity.setCreatedBy(usersEntity);
        questionsEntity.setTitle(input.getTitle());
        questionsEntity.setContent(input.getContent());
        questionsEntity.setAnswers(Collections.emptyList());

        return questionsEntity;
    }

    public static AnswersEntity mapInputToEntity(AnswerCreateInput input, UsersEntity usersEntity, QuestionsEntity questionsEntity){
        var answersEntity = new AnswersEntity();
        answersEntity.setId(UUID.randomUUID());
        answersEntity.setContent(input.getContent());
        answersEntity.setCategory(input.getCategory().name());
        answersEntity.setCreatedBy(usersEntity);
        answersEntity.setQuestion(questionsEntity);
        answersEntity.setDownVotes(0);
        answersEntity.setUpVotes(0);

        return answersEntity;
    }

}
