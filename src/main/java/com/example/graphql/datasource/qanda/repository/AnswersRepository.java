package com.example.graphql.datasource.qanda.repository;

import com.example.graphql.datasource.qanda.entity.AnswersEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AnswersRepository extends CrudRepository<AnswersEntity, UUID> {

    @Query(
            nativeQuery = true,
            value = "SELECT * FROM answers WHERE " +
                    "UPPER(content) LIKE UPPER(:term)"
    )
    List<AnswersEntity> findBySearchTerm(@Param("term") String term);
}
