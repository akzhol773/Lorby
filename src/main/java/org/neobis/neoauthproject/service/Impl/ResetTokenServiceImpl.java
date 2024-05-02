package org.neobis.neoauthproject.service.Impl;


import lombok.RequiredArgsConstructor;
import org.neobis.neoauthproject.entity.PasswordResetToken;
import org.neobis.neoauthproject.repository.ResetTokenRepository;
import org.neobis.neoauthproject.service.ResetTokenService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ResetTokenServiceImpl implements ResetTokenService {

    private final ResetTokenRepository repository;
    @Override
    public void saveResetToken(PasswordResetToken token) {
        repository.save(token);
    }

    @Override
    public Optional<PasswordResetToken> getToken(String token) {
        return repository.findByToken(token);
    }



}
