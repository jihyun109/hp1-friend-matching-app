package com.hp1.friendmatchingapp.repository;

import com.hp1.friendmatchingapp.entity.UserHobbyEntity;
import com.hp1.friendmatchingapp.enums.Hobby;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserHobbyRepository extends JpaRepository<UserHobbyEntity,Long> {
    @Query(value = "SELECT h.hobby_name FROM user_hobbies uh JOIN hobbies h ON uh.hobby_id = h.id WHERE uh.user_id = :userId", nativeQuery = true)
    Optional<List<Hobby>> findHobbiesByUserId(@Param("userId")Long userId);
}