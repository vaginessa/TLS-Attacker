/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 *
 * Copyright 2014-2017 Ruhr University Bochum / Hackmanit GmbH
 *
 * Licensed under Apache License 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.tlsattacker.core.protocol.message;

import de.rub.nds.modifiablevariable.HoldsModifiableVariable;
import de.rub.nds.modifiablevariable.ModifiableVariableFactory;
import de.rub.nds.modifiablevariable.ModifiableVariableProperty;
import de.rub.nds.modifiablevariable.bytearray.ModifiableByteArray;
import de.rub.nds.modifiablevariable.integer.ModifiableInteger;
import de.rub.nds.modifiablevariable.util.ArrayConverter;
import de.rub.nds.tlsattacker.core.constants.HandshakeMessageType;
import de.rub.nds.tlsattacker.core.protocol.message.extension.SupplementalData.SupplementalDataEntry;
import de.rub.nds.tlsattacker.core.protocol.handler.SupplementalDataMessageHandler;
import de.rub.nds.tlsattacker.core.state.TlsContext;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.LinkedList;
import java.util.List;

@XmlRootElement
public class SupplementalDataMessage extends HandshakeMessage {

    @HoldsModifiableVariable
    private List<SupplementalDataEntry> entries;

    @ModifiableVariableProperty(type = ModifiableVariableProperty.Type.LENGTH)
    private ModifiableInteger supplementalDataLength;

    @ModifiableVariableProperty
    private ModifiableByteArray supplementalDataBytes;

    public SupplementalDataMessage(LinkedList<SupplementalDataEntry> entries) {
        super(HandshakeMessageType.SUPPLEMENTAL_DATA);
        this.entries = new LinkedList<>(entries);
    }

    public SupplementalDataMessage() {
        super(HandshakeMessageType.SUPPLEMENTAL_DATA);
        this.entries = new LinkedList<>();
    }

    public List<SupplementalDataEntry> getEntries() {
        return entries;
    }

    public void setEntries(List<SupplementalDataEntry> entries) {
        this.entries = entries;
    }

    public ModifiableInteger getSupplementalDataLength() {
        return supplementalDataLength;
    }

    public void setSupplementalDataLength(ModifiableInteger supplementalDataLength) {
        this.supplementalDataLength = supplementalDataLength;
    }

    public ModifiableByteArray getSupplementalDataBytes() {
        return supplementalDataBytes;
    }

    public void setSupplementalDataBytes(ModifiableByteArray supplementalDataBytes) {
        this.supplementalDataBytes = supplementalDataBytes;
    }

    public void setSupplementalDataBytes(byte[] supplementalDataBytes) {
        this.supplementalDataBytes = ModifiableVariableFactory.safelySetValue(this.supplementalDataBytes,
                supplementalDataBytes);
    }

    @Override
    public SupplementalDataMessageHandler getHandler(TlsContext context) {
        return new SupplementalDataMessageHandler(context);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(super.toString());
        sb.append("\n  Supplemental Data Length: ").append(supplementalDataLength.getValue());
        for (SupplementalDataEntry entry : entries) {
            sb.append("\n  Supplemental Data Type: ").append(entry.getSupplementalDataType().getValue());
            sb.append("\n  Supplemental Data Length: ").append(entry.getSupplementalDataLength().getValue());
            sb.append("\n  Supplemental Data : ").append(
                    ArrayConverter.bytesToHexString(entry.getSupplementalData().getValue()));
        }
        return sb.toString();
    }

}
