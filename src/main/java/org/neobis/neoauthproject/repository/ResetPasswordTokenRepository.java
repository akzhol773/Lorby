package org.neobis.neoauthproject.repository;



import org.neobis.neoauthproject.entity.PasswordResetToken;
import org.neobis.neoauthproject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ResetPasswordTokenRepository extends JpaRepository<PasswordResetToken, Long> {
    Optional<PasswordResetToken> findByToken(String token);
    List<PasswordResetToken> findByUser(User user);
    @Query("SELECT t FROM PasswordResetToken t WHERE t.user.email = :email ORDER BY t.createdAt DESC")
    Optional<PasswordResetToken> getLastToken(@Param("email") String email);

}
