package org.neobis.neoauthproject.service;

import org.neobis.neoauthproject.entity.ConfirmationToken;

public interface ConfirmationTokenService {
    void saveConfirmationToken(ConfirmationToken confirmationToken);
}
