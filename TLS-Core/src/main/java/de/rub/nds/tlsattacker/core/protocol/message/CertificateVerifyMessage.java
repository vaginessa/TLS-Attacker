/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 *
 * Copyright 2014-2017 Ruhr University Bochum / Hackmanit GmbH
 *
 * Licensed under Apache License 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.tlsattacker.core.protocol.message;

import de.rub.nds.modifiablevariable.ModifiableVariableFactory;
import de.rub.nds.modifiablevariable.ModifiableVariableProperty;
import de.rub.nds.modifiablevariable.bytearray.ModifiableByteArray;
import de.rub.nds.modifiablevariable.integer.ModifiableInteger;
import de.rub.nds.modifiablevariable.util.ArrayConverter;
import de.rub.nds.tlsattacker.core.config.Config;
import de.rub.nds.tlsattacker.core.constants.HandshakeMessageType;
import de.rub.nds.tlsattacker.core.protocol.handler.CertificateVerifyHandler;
import de.rub.nds.tlsattacker.core.protocol.handler.ProtocolMessageHandler;
import de.rub.nds.tlsattacker.core.state.TlsContext;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CertificateVerifyMessage extends HandshakeMessage {

    /**
     * selected Signature and Hashalgorithm
     */
    @ModifiableVariableProperty(type = ModifiableVariableProperty.Type.TLS_CONSTANT)
    private ModifiableByteArray signatureHashAlgorithm;
    /**
     * signature length
     */
    @ModifiableVariableProperty(type = ModifiableVariableProperty.Type.LENGTH)
    private ModifiableInteger signatureLength;
    /**
     * signature
     */
    @ModifiableVariableProperty(type = ModifiableVariableProperty.Type.SIGNATURE)
    private ModifiableByteArray signature;

    public CertificateVerifyMessage() {
        super(HandshakeMessageType.CERTIFICATE_VERIFY);
    }

    public CertificateVerifyMessage(Config tlsConfig) {
        super(tlsConfig, HandshakeMessageType.CERTIFICATE_VERIFY);
    }

    public ModifiableByteArray getSignatureHashAlgorithm() {
        return signatureHashAlgorithm;
    }

    public void setSignatureHashAlgorithm(ModifiableByteArray signatureHashAlgorithm) {
        this.signatureHashAlgorithm = signatureHashAlgorithm;
    }

    public void setSignatureHashAlgorithm(byte[] signatureHashAlgorithm) {
        this.signatureHashAlgorithm = ModifiableVariableFactory.safelySetValue(this.signatureHashAlgorithm,
                signatureHashAlgorithm);
    }

    public ModifiableInteger getSignatureLength() {
        return signatureLength;
    }

    public void setSignatureLength(ModifiableInteger signatureLength) {
        this.signatureLength = signatureLength;
    }

    public void setSignatureLength(int length) {
        this.signatureLength = ModifiableVariableFactory.safelySetValue(this.signatureLength, length);
    }

    public ModifiableByteArray getSignature() {
        return signature;
    }

    public void setSignature(ModifiableByteArray signature) {
        this.signature = signature;
    }

    public void setSignature(byte[] signature) {
        this.signature = ModifiableVariableFactory.safelySetValue(this.signature, signature);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(super.toString());
        builder.append("  \nSignatureAndHashAlgorithm:").append(
                ArrayConverter.bytesToHexString(signatureHashAlgorithm.getValue()));
        builder.append("  \nSignature Length:").append(signatureLength.getValue());
        builder.append("  \nSignature:").append(ArrayConverter.bytesToHexString(signature.getValue()));
        return builder.toString();
    }

    @Override
    public ProtocolMessageHandler getHandler(TlsContext context) {
        return new CertificateVerifyHandler(context);
    }
}
