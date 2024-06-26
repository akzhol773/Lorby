package org.neobis.neoauthproject.repository;


import org.neobis.neoauthproject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE (u.username = :username)")
    Optional<User> findByUsername(@Param("username") String username);

    Optional<User> findByEmailIgnoreCase(String email);

    @Query("SELECT u FROM User u WHERE (u.email = :emailOrUsername OR u.username = :emailOrUsername)")
    Optional<User> findByEmailOrUsername(@Param("emailOrUsername") String emailOrUsername);
}
