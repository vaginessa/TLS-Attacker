/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 *
 * Copyright 2014-2017 Ruhr University Bochum / Hackmanit GmbH
 *
 * Licensed under Apache License 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.tlsattacker.core.record.cipher;

import de.rub.nds.tlsattacker.core.record.cipher.cryptohelper.EncryptionResult;
import de.rub.nds.tlsattacker.core.record.cipher.cryptohelper.EncryptionRequest;
import de.rub.nds.tlsattacker.core.exceptions.CryptoException;
import de.rub.nds.tlsattacker.core.state.TlsContext;

public class RecordNullCipher extends RecordCipher {

    public RecordNullCipher(TlsContext context) {
        super(context, null);
    }

    /**
     * Null Cipher just passes the data through
     *
     * @param request
     *            The EncryptionRequest
     * @return The EncryptionResult
     */
    @Override
    public EncryptionResult encrypt(EncryptionRequest request) throws CryptoException {
        return new EncryptionResult(request.getPlainText());
    }

    /**
     * Null Cipher just passes the data through
     *
     * @param data
     *            The raw data that should be decrypted
     * @return The raw decrypted Data
     */
    @Override
    public byte[] decrypt(byte[] data) {
        return data;
    }

    @Override
    public boolean isUsingPadding() {
        return false;
    }

    @Override
    public boolean isUsingMac() {
        return false;
    }

    @Override
    public boolean isUsingTags() {
        return false;
    }

    @Override
    public byte[] getEncryptionIV() {
        return new byte[0];
    }

    @Override
    public byte[] getDecryptionIV() {
        return new byte[0];
    }
}
