package com.example.graphql.service.query;

import com.example.graphql.datasource.qanda.entity.AnswersEntity;
import com.example.graphql.datasource.qanda.repository.AnswersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnswersQueryService {

    @Autowired
    private AnswersRepository answersRepository;

    public List<AnswersEntity> findBySearchTerm(String term){
        return answersRepository.findBySearchTerm("%" + term + "%");
    }
}
