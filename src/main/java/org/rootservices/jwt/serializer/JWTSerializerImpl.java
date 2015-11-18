package org.rootservices.jwt.serializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.rootservices.jwt.entity.jwt.Claims;
import org.rootservices.jwt.entity.jwt.JsonWebToken;
import org.rootservices.jwt.entity.jwt.header.Header;

import java.nio.charset.Charset;

import java.util.Base64.Encoder;
import java.util.Base64.Decoder;
import java.util.Optional;

/**
 * Created by tommackenzie on 8/13/15.
 *
 * A Serializer that converts:
 * - a jwt as a string to a instance of a JsonWebToken.
 * - a JsonWebToken to its string representation.
 */
public class JWTSerializerImpl implements JWTSerializer {
    private final int SECURE_TOKEN_LENGTH = 3;
    private Serializer serializer;
    private Encoder encoder;
    private Decoder decoder;

    public JWTSerializerImpl(Serializer serializer, Encoder encoder, Decoder decoder) {
        this.serializer = serializer;
        this.encoder = encoder;
        this.decoder = decoder;
    }

    @Override
    public String jwtToString(JsonWebToken jwt) {
        String jwtAsText = "";
        String headerJson = "";
        String claimsJson = "";

        try {
            headerJson = serializer.objectToJson(jwt.getHeader());
            claimsJson = serializer.objectToJson(jwt.getClaims());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        jwtAsText = encode(headerJson) + "." + encode(claimsJson) + ".";

        if (jwt.getSignature().isPresent())
            jwtAsText+=jwt.getSignature().get();

        return jwtAsText;
    }

    private String encode(String input) {
        return encoder.encodeToString(input.getBytes(Charset.forName("UTF-8")));
    }

    @Override
    public JsonWebToken stringToJwt(String jwtAsText, Class claimClass) {
        String[] jwtParts = jwtAsText.split("\\.");

        byte[] headerJson = decoder.decode(jwtParts[0]);
        byte[] claimsJson = decoder.decode(jwtParts[1]);

        Header header = (Header) serializer.jsonBytesToObject(headerJson, Header.class);
        Claims claim = (Claims) serializer.jsonBytesToObject(claimsJson, claimClass);

        JsonWebToken jwt = new JsonWebToken(header, claim, Optional.of(jwtAsText));

        if (jwtParts.length == SECURE_TOKEN_LENGTH && jwtParts[SECURE_TOKEN_LENGTH-1] != null)
            jwt.setSignature(Optional.of(jwtParts[SECURE_TOKEN_LENGTH-1]));

        return jwt;
    }
}
