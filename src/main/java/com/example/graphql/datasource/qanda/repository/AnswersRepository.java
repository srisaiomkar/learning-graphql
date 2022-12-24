package com.example.graphql.datasource.qanda.repository;

import com.example.graphql.datasource.qanda.entity.AnswersEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
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

    @Transactional
    @Modifying
    @Query(
            nativeQuery = true,
            value = "UPDATE answers set up_votes = up_votes + 1 " +
                    "WHERE id = :answerId"
    )
    public void upVoteAnswer(@Param("answerId") UUID answerId);


    @Transactional
    @Modifying
    @Query(
            nativeQuery = true,
            value = "UPDATE answers set down_votes = down_votes + 1 " +
                    "WHERE id = :answerId"
    )
    public void downVoteAnswer(@Param("answerId") UUID answerId);
}
