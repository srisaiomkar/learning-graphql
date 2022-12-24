package com.example.graphql.service.command;

import com.example.graphql.datasource.qanda.entity.AnswersEntity;
import com.example.graphql.datasource.qanda.repository.AnswersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class AnswersCommandService {

    @Autowired
    private AnswersRepository answersRepository;

    public AnswersEntity saveAnswer(AnswersEntity answersEntity){
        return answersRepository.save(answersEntity);
    }

    public Optional<AnswersEntity> upVoteAnswer(UUID answerId){
        answersRepository.upVoteAnswer(answerId);
        return answersRepository.findById(answerId);
    }

    public Optional<AnswersEntity> downVoteAnswer(UUID answerId){
        answersRepository.downVoteAnswer(answerId);
        return answersRepository.findById(answerId);
    }
}
