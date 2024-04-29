package org.neobis.neoauthproject.dto;

import org.neobis.neoauthproject.exception.BaseException;

public class InvalidTokenException extends BaseException {
    public InvalidTokenException(String message) {
        super(message);
    }
}
