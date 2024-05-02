package org.neobis.neoauthproject.repository;



import org.neobis.neoauthproject.entity.PasswordResetToken;
import org.neobis.neoauthproject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
    Optional<PasswordResetToken> findByToken(String token);
    List<PasswordResetToken> findByUser(User user);
}
