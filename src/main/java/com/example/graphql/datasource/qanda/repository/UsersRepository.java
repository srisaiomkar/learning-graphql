package com.example.graphql.datasource.qanda.repository;

import com.example.graphql.datasource.qanda.entity.UsersEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UsersRepository extends CrudRepository<UsersEntity, UUID> {
    Optional<UsersEntity> findByEmailIgnoreCase(String email);
}
