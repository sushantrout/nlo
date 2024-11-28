package com.nlo.repository;

import com.nlo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByUsername(String username);
    List<User> findByConstituencyId(String constituencyId);
    Optional<User> findByMobile(String mobile);
    Optional<User> findByMemberShipId(String memberShipId);

}