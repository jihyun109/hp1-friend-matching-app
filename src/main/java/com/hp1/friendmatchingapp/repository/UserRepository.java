package com.hp1.friendmatchingapp.repository;

import com.hp1.friendmatchingapp.entity.UserEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,Long> {
    Optional<UserEntity> findUserEntityByUsername(String userName);
    Slice<UserEntity> findSliceByOrderByIdAsc(Pageable pageable);

    boolean existsByUsername(String username);
    Optional<UserEntity> findByEmail(String emil);
}
