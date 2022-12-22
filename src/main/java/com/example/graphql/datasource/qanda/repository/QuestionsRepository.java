package com.example.graphql.datasource.qanda.repository;

import com.example.graphql.datasource.qanda.entity.QuestionsEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface QuestionsRepository extends CrudRepository<QuestionsEntity, UUID> {

    List<QuestionsEntity> findAllByOrderByCreatedAtDesc();
}
