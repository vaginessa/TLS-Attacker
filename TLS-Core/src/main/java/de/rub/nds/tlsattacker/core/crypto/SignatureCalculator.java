/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 *
 * Copyright 2014-2017 Ruhr University Bochum / Hackmanit GmbH
 *
 * Licensed under Apache License 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.tlsattacker.core.crypto;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.interfaces.DSAPrivateKey;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.RSAPrivateKey;

import de.rub.nds.modifiablevariable.util.BadRandom;
import de.rub.nds.tlsattacker.core.constants.SignatureAndHashAlgorithm;
import de.rub.nds.tlsattacker.core.exceptions.CryptoException;
import de.rub.nds.tlsattacker.core.workflow.chooser.Chooser;

public class SignatureCalculator {

    private SignatureCalculator() {
    }

    public static byte[] generateSignature(SignatureAndHashAlgorithm algorithm, Chooser chooser, byte[] toBeSigned) {
        switch (algorithm.getSignatureAlgorithm()) {
            case ANONYMOUS:
                return generateAnonymousSignature(chooser, toBeSigned, algorithm);
            case DSA:
                return generateDSASignature(chooser, toBeSigned, algorithm);
            case ECDSA:
                return generateECDSASignature(chooser, toBeSigned, algorithm);
            case RSA:
                return generateRSASignature(chooser, toBeSigned, algorithm);
            default:
                throw new UnsupportedOperationException("Unknown SignatureAlgorithm:"
                        + algorithm.getSignatureAlgorithm().name());
        }
    }

    public static byte[] generateSignature(PrivateKey key, byte[] toBeSigned, SignatureAndHashAlgorithm algorithm,
            BadRandom random) {
        try {
            Signature instance = Signature.getInstance(algorithm.getJavaName());
            instance.initSign(key, random);
            instance.update(toBeSigned);
            return instance.sign();
        } catch (SignatureException | InvalidKeyException | NoSuchAlgorithmException ex) {
            throw new CryptoException("Could not sign Data", ex);
        }
    }

    public static byte[] generateRSASignature(Chooser chooser, byte[] toBeSigned, SignatureAndHashAlgorithm algorithm) {
        RSAPrivateKey key = KeyGenerator.getRSAPrivateKey(chooser);
        return generateSignature(key, toBeSigned, algorithm, chooser.getContext().getBadSecureRandom());
    }

    public static byte[] generateDSASignature(Chooser chooser, byte[] toBeSigned, SignatureAndHashAlgorithm algorithm) {
        DSAPrivateKey key = KeyGenerator.getDSAPrivateKey(chooser);
        return generateSignature(key, toBeSigned, algorithm, chooser.getContext().getBadSecureRandom());
    }

    public static byte[] generateECDSASignature(Chooser chooser, byte[] toBeSigned, SignatureAndHashAlgorithm algorithm) {
        ECPrivateKey key = KeyGenerator.getECPrivateKey(chooser);
        return generateSignature(key, toBeSigned, algorithm, chooser.getContext().getBadSecureRandom());
    }

    public static byte[] generateAnonymousSignature(Chooser chooser, byte[] toBeSigned,
            SignatureAndHashAlgorithm algorithm) {
        return new byte[0];
    }
}
