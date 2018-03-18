package org.rootservices.jwt.jwe.entity;

import org.rootservices.jwt.entity.jwt.header.Header;

public class JWE<T> {
    private Header header;
    private T payload;
    private byte[] cek;
    private byte[] iv;
    private byte[] authTag;

    public JWE() {}

    public JWE(Header header, T payload, byte[] cek, byte[] iv, byte[] authTag) {
        this.header = header;
        this.payload = payload;
        this.cek = cek;
        this.iv = iv;
        this.authTag = authTag;
    }

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public T getPayload() {
        return payload;
    }

    public void setPayload(T payload) {
        this.payload = payload;
    }

    public byte[] getCek() {
        return cek;
    }

    public void setCek(byte[] cek) {
        this.cek = cek;
    }

    public byte[] getIv() {
        return iv;
    }

    public void setIv(byte[] iv) {
        this.iv = iv;
    }

    public byte[] getAuthTag() {
        return authTag;
    }

    public void setAuthTag(byte[] authTag) {
        this.authTag = authTag;
    }
}
