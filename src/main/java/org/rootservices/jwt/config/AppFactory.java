package org.rootservices.jwt.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import org.rootservices.jwt.builder.TokenBuilder;
import org.rootservices.jwt.entity.jwk.Key;
import org.rootservices.jwt.entity.jwt.header.Algorithm;
import org.rootservices.jwt.serializer.JWTSerializer;
import org.rootservices.jwt.serializer.JWTSerializerImpl;
import org.rootservices.jwt.serializer.Serializer;
import org.rootservices.jwt.serializer.SerializerImpl;
import org.rootservices.jwt.signature.VerifySignature;
import org.rootservices.jwt.signature.VerifySignatureImpl;
import org.rootservices.jwt.signature.signer.Signer;
import org.rootservices.jwt.signature.signer.factory.MacFactory;
import org.rootservices.jwt.signature.signer.factory.MacFactoryImpl;
import org.rootservices.jwt.signature.signer.factory.SignerFactory;
import org.rootservices.jwt.signature.signer.factory.SignerFactoryImpl;

import javax.crypto.Mac;
import java.util.Base64;

/**
 * Created by tommackenzie on 8/13/15.
 */
public class AppFactory {

    public ObjectMapper objectMapper() {
        return new ObjectMapper()
                .setPropertyNamingStrategy(
                        PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES
                )
                .configure(JsonParser.Feature.STRICT_DUPLICATE_DETECTION, true)
                .registerModule(new Jdk8Module())
                .setSerializationInclusion(JsonInclude.Include.NON_NULL)
                .setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
    }

    public Serializer serializer() {
        return new SerializerImpl(objectMapper());
    }

    public Base64.Decoder decoder() {
        return Base64.getDecoder();
    }

    public Base64.Encoder encoder() {
        return Base64.getUrlEncoder().withoutPadding();
    }

    public JWTSerializer jwtSerializer() {
        return new JWTSerializerImpl(
                serializer(),
                encoder(),
                decoder()
        );
    }

    public MacFactory macFactory() {
        return new MacFactoryImpl();
    }

    public SignerFactory signerFactory() {
        return new SignerFactoryImpl(
                macFactory(),
                serializer(),
                encoder()
        );
    }

    public VerifySignature verifySignature() {
        return new VerifySignatureImpl(signerFactory());
    }

    public TokenBuilder tokenBuilder(Algorithm alg, Key jwk){
        Signer signer = signerFactory().makeSigner(alg, jwk);
        return new TokenBuilder(jwtSerializer(), signer);
    }
}