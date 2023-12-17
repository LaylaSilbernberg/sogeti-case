package com.layla.filmlandbackend.security;

import org.springframework.stereotype.Component;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

@Component
final class KeyGeneratorUtils {
    private KeyGeneratorUtils() {

    }

    static KeyPair generateRSAKey(){
        KeyPair keypair = null;

        try {
            KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
            generator.initialize(2048);
            keypair = generator.generateKeyPair();
            return keypair;

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return keypair;
    }
}
