package com.example.graphql.datasource.qanda.repository;

import com.example.graphql.datasource.qanda.entity.QuestionsEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface QuestionsRepository extends CrudRepository<QuestionsEntity, UUID> {

    List<QuestionsEntity> findAllByOrderByCreatedAtDesc();

    @Query(
            nativeQuery = true,
            value = "select * from questions where " +
                    "UPPER(title) LIKE UPPER(:term) " +
                    "OR UPPER(content) Like UPPER(:term) " +
                    "OR UPPER(Tags) LIKE UPPER(:term)"
    )
    List<QuestionsEntity> findBySearchTerm(@Param("term") String term);
}
