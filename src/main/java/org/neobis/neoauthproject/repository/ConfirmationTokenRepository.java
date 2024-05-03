package org.neobis.neoauthproject.repository;

import org.neobis.neoauthproject.entity.ConfirmationToken;
import org.neobis.neoauthproject.entity.PasswordResetToken;
import org.neobis.neoauthproject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, Long> {
    Optional<ConfirmationToken> findByToken(String token);
    List<ConfirmationToken> findByUser(User user);

}