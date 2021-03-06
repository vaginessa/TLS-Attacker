/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 *
 * Copyright 2014-2017 Ruhr University Bochum / Hackmanit GmbH
 *
 * Licensed under Apache License 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.tlsattacker.core.protocol.serializer;

import de.rub.nds.tlsattacker.core.constants.HandshakeMessageType;
import de.rub.nds.tlsattacker.core.constants.ProtocolVersion;
import de.rub.nds.tlsattacker.core.protocol.message.CertificateRequestMessage;
import de.rub.nds.tlsattacker.core.protocol.parser.CertificateRequestMessageParserTest;
import java.util.Collection;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class CertificateRequestMessageSerializerTest {

    @Parameterized.Parameters
    public static Collection<Object[]> generateData() {
        return CertificateRequestMessageParserTest.generateData();
    }

    private byte[] message;
    private int start;
    private byte[] expectedPart;
    private HandshakeMessageType type;
    private int length;
    private int certTypesCount;
    private byte[] certTypes;
    private int sigHashAlgsLength;
    private byte[] sigHashAlgs;
    private int distinguishedNamesLength;
    private byte[] disitinguishedNames;
    private ProtocolVersion version;

    public CertificateRequestMessageSerializerTest(byte[] message, int start, byte[] expectedPart,
            HandshakeMessageType type, int length, int certTypesCount, byte[] certTypes, int sigHashAlgsLength,
            byte[] sigHashAlgs, int distinguishedNamesLength, byte[] disitinguishedNames, ProtocolVersion version) {
        this.message = message;
        this.start = start;
        this.expectedPart = expectedPart;
        this.type = type;
        this.length = length;
        this.certTypesCount = certTypesCount;
        this.certTypes = certTypes;
        this.sigHashAlgsLength = sigHashAlgsLength;
        this.sigHashAlgs = sigHashAlgs;
        this.distinguishedNamesLength = distinguishedNamesLength;
        this.disitinguishedNames = disitinguishedNames;
        this.version = version;
    }

    /**
     * Test of serializeHandshakeMessageContent method, of class
     * CertificateRequestMessageSerializer.
     */
    @Test
    public void testSerializeHandshakeMessageContent() {
        CertificateRequestMessage message = new CertificateRequestMessage();
        message.setLength(length);
        message.setType(type.getValue());
        message.setClientCertificateTypesCount(certTypesCount);
        message.setClientCertificateTypes(certTypes);
        message.setSignatureHashAlgorithmsLength(sigHashAlgsLength);
        message.setSignatureHashAlgorithms(sigHashAlgs);
        message.setDistinguishedNamesLength(distinguishedNamesLength);
        message.setDistinguishedNames(disitinguishedNames);
        CertificateRequestMessageSerializer serializer = new CertificateRequestMessageSerializer(message, version);
        assertArrayEquals(expectedPart, serializer.serialize());
    }

}
