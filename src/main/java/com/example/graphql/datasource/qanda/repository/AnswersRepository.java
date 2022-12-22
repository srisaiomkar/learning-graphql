package com.example.graphql.datasource.qanda.repository;

import com.example.graphql.datasource.qanda.entity.AnswersEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AnswersRepository extends CrudRepository<AnswersEntity, UUID> {
}
