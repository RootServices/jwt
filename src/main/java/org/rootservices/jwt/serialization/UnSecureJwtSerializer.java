package org.rootservices.jwt.serialization;

import org.rootservices.jwt.entity.jwt.Claims;
import org.rootservices.jwt.entity.jwt.JsonWebToken;
import org.rootservices.jwt.factory.UnSecureJwtFactory;
import org.rootservices.jwt.serialization.exception.JwtToJsonException;

import java.io.ByteArrayOutputStream;


public class UnSecureJwtSerializer {
    private UnSecureJwtFactory unSecureJwtFactory;
    private JwtSerde jwtSerde;

    public UnSecureJwtSerializer(UnSecureJwtFactory unSecureJwtFactory, JwtSerde jwtSerde) {
        this.unSecureJwtFactory = unSecureJwtFactory;
        this.jwtSerde = jwtSerde;
    }

    public String compactJwtToString(Claims claims) {
        return compactJwt(claims).toString();
    }

    public ByteArrayOutputStream compactJwt(Claims claims) {

        JsonWebToken jsonWebToken = unSecureJwtFactory.makeJwt(claims);

        ByteArrayOutputStream encodedJwt = null;
        try {
            encodedJwt = jwtSerde.compactJwt(jsonWebToken);
        } catch (JwtToJsonException e) {
            e.printStackTrace();
        }

        return encodedJwt;
    }
}
