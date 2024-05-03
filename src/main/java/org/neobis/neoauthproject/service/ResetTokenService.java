package org.neobis.neoauthproject.service;


import org.neobis.neoauthproject.entity.PasswordResetToken;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface ResetTokenService {
    void saveResetToken(PasswordResetToken token);

    Optional<PasswordResetToken> getToken(String token);



}
