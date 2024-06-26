package org.neobis.neoauthproject.service.Impl;

import lombok.RequiredArgsConstructor;
import org.neobis.neoauthproject.entity.ConfirmationToken;
import org.neobis.neoauthproject.repository.ConfirmationTokenRepository;
import org.neobis.neoauthproject.service.ConfirmationTokenService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ConfirmationTokenServiceImpl implements ConfirmationTokenService {
    private final ConfirmationTokenRepository confirmationTokenRepository;
    @Override
    public void saveConfirmationToken(ConfirmationToken confirmationToken) {
        confirmationTokenRepository.save(confirmationToken);
    }

    @Override
    public Optional<ConfirmationToken> getToken(String token) {
        return confirmationTokenRepository.findByToken(token);
    }
}
