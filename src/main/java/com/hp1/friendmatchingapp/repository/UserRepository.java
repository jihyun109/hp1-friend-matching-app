package com.hp1.friendmatchingapp.repository;

import com.hp1.friendmatchingapp.dto.UserInfoResponseDTO;
import com.hp1.friendmatchingapp.dto.UserMatchingResponseDto;
import com.hp1.friendmatchingapp.entity.UserEntity;
import com.hp1.friendmatchingapp.enums.Gender;
import com.hp1.friendmatchingapp.enums.Hobby;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,Long> {
    UserEntity findUserEntityById(Long id);
    Optional<UserEntity> findUserEntityByUsername(String userName);
    Slice<UserEntity> findSliceByOrderByIdAsc(Pageable pageable);
    boolean existsByUsername(String username);
    Optional<UserEntity> findByEmail(String emil);

    @Query("SELECT DISTINCT new com.hp1.friendmatchingapp.dto.UserMatchingResponseDto(u.id, u.firstName, u.age, u.gender, u.profileImageUrl)" +
            "FROM UserEntity u " +
            "JOIN UserHobbyEntity uh ON u.id = uh.userId " +
            "JOIN HobbyEntity h ON uh.hobbyId = h.id " +
            "WHERE u.gender IN :gender AND h.hobbyName IN :hobbies AND u.ageRange IN :ageRanges " +
            "AND u.username != :username")
    Page<UserMatchingResponseDto> findUserEntitiesByByGenderAAndHobbiesExcludingSelf
            (String username, Set<Gender> gender, Set<Hobby> hobbies, Set<Integer> ageRanges, Pageable pageable);

    @Modifying
    @Query("UPDATE UserEntity u SET u.profileImageUrl = :profileImageUrl WHERE u.id = :userId")
    void updateUserProfileImageById(Long userId, String profileImageUrl);

    @Query("SELECT new com.hp1.friendmatchingapp.dto.UserInfoResponseDTO(u.profileImageUrl, u.firstName, u.birthDate, u.gender, u.chatRoomUrl)" +
            "FROM UserEntity u " +
            "WHERE u.id = :userId")
    UserInfoResponseDTO getUserInfoById(Long userId);

}
