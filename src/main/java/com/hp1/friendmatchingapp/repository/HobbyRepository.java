package com.hp1.friendmatchingapp.repository;

import com.hp1.friendmatchingapp.entity.HobbyEntity;
import com.hp1.friendmatchingapp.enums.Hobby;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HobbyRepository extends JpaRepository<HobbyEntity, Long> {
    Optional<HobbyEntity> findByHobbyName(Hobby hobbyName);
}
