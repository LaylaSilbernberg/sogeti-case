package com.layla.filmlandbackend.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

@Component
final class KeyGeneratorUtils {
    private KeyGeneratorUtils() {

    }
    private static final Logger LOG = LoggerFactory.getLogger(KeyGeneratorUtils.class);

    static KeyPair generateRSAKey(){
        KeyPair keypair = null;

        try {
            KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
            generator.initialize(2048);
            keypair = generator.generateKeyPair();
            return keypair;

        } catch (NoSuchAlgorithmException e) {
            LOG.atError().log(e.getMessage());
        }
        return keypair;
    }
}
