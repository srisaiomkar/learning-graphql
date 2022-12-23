package com.example.graphql.service.query;

import com.example.graphql.datasource.qanda.entity.QuestionsEntity;
import com.example.graphql.datasource.qanda.repository.QuestionsRepository;
import com.example.graphql.generated.types.Question;
import com.example.graphql.util.GraphqlBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class QuestionsQueryService {

    @Autowired
    private QuestionsRepository questionsRepository;

    public List<QuestionsEntity> questionLatestList(){
        return questionsRepository.findAllByOrderByCreatedAtDesc();
    }

    public Optional<QuestionsEntity> questionDetail(UUID id){
        return questionsRepository.findById(id);
    }

    public List<QuestionsEntity> questionsBySearchTerm(String term){
        return questionsRepository.findBySearchTerm("%" + term + "%");
    }
}
