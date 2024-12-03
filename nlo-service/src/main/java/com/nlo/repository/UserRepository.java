package com.nlo.repository;

import com.nlo.entity.User;
import com.nlo.model.UserIdNameDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByUsername(String username);
    List<User> findByConstituencyId(String constituencyId);
    Optional<User> findByMobile(String mobile);
    Optional<User> findByMemberShipId(String memberShipId);

    @Query("SELECT new com.nlo.model.UserIdNameDTO(u.id, u.name) FROM User u WHERE u.id IN :ids")
    List<UserIdNameDTO> findUserIdAndNamesByIds(@Param("ids") Set<String> ids);

}