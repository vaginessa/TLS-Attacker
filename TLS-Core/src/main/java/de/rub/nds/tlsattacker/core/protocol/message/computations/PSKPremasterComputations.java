/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 *
 * Copyright 2014-2017 Ruhr University Bochum / Hackmanit GmbH
 *
 * Licensed under Apache License 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.tlsattacker.core.protocol.message.computations;

import de.rub.nds.modifiablevariable.ModifiableVariableFactory;
import de.rub.nds.modifiablevariable.bytearray.ModifiableByteArray;
import de.rub.nds.modifiablevariable.integer.ModifiableInteger;

public class PSKPremasterComputations extends KeyExchangeComputations {

    private ModifiableByteArray premasterSecret;

    private ModifiableByteArray psk;
    private ModifiableInteger pskLength;

    public PSKPremasterComputations() {
    }

    public PSKPremasterComputations(ModifiableInteger pskLength, ModifiableByteArray psk) {
        this.pskLength = pskLength;
        this.psk = psk;
    }

    @Override
    public ModifiableByteArray getPremasterSecret() {
        return premasterSecret;
    }

    @Override
    public void setPremasterSecret(ModifiableByteArray PremasterSecret) {
        this.premasterSecret = PremasterSecret;
    }

    @Override
    public void setPremasterSecret(byte[] value) {
        this.premasterSecret = ModifiableVariableFactory.safelySetValue(this.premasterSecret, value);
    }
}
