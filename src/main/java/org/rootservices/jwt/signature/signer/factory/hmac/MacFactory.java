package org.rootservices.jwt.signature.signer.factory.hmac;

import org.rootservices.jwt.entity.jwk.SymmetricKey;
import org.rootservices.jwt.signature.signer.SignAlgorithm;
import org.rootservices.jwt.signature.signer.factory.exception.InvalidAlgorithmException;
import org.rootservices.jwt.signature.signer.factory.hmac.exception.SecurityKeyException;

import javax.crypto.Mac;
import java.security.Key;

/**
 * Created by tommackenzie on 8/22/15.
 */
public interface MacFactory {
    Key makeKey(SignAlgorithm alg, SymmetricKey jwk);
    Mac makeMac(SignAlgorithm alg, SymmetricKey jwk) throws InvalidAlgorithmException, SecurityKeyException;
}
