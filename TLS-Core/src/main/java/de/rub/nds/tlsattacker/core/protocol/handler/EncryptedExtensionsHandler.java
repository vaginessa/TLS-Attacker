/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 *
 * Copyright 2014-2017 Ruhr University Bochum / Hackmanit GmbH
 *
 * Licensed under Apache License 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.tlsattacker.core.protocol.handler;

import de.rub.nds.tlsattacker.core.constants.HandshakeMessageType;
import de.rub.nds.tlsattacker.core.protocol.handler.extension.ExtensionHandler;
import de.rub.nds.tlsattacker.core.protocol.handler.factory.HandlerFactory;
import de.rub.nds.tlsattacker.core.protocol.message.EncryptedExtensionsMessage;
import de.rub.nds.tlsattacker.core.protocol.message.extension.ExtensionMessage;
import de.rub.nds.tlsattacker.core.protocol.parser.EncryptedExtensionsParser;
import de.rub.nds.tlsattacker.core.protocol.preparator.EncryptedExtensionsPreparator;
import de.rub.nds.tlsattacker.core.protocol.serializer.EncryptedExtensionsSerializer;
import de.rub.nds.tlsattacker.core.state.TlsContext;

/**
 * This handler processes the EncryptedExtension messages, as defined in
 * https://tools.ietf.org/html/draft-ietf-tls-tls13-21#section-4.3.1
 */
public class EncryptedExtensionsHandler extends HandshakeMessageHandler<EncryptedExtensionsMessage> {

    public EncryptedExtensionsHandler(TlsContext tlsContext) {
        super(tlsContext);
    }

    @Override
    public EncryptedExtensionsParser getParser(byte[] message, int pointer) {
        return new EncryptedExtensionsParser(pointer, message, tlsContext.getLastRecordVersion());
    }

    @Override
    public EncryptedExtensionsPreparator getPreparator(EncryptedExtensionsMessage message) {
        return new EncryptedExtensionsPreparator(tlsContext.getChooser(), message);
    }

    @Override
    public EncryptedExtensionsSerializer getSerializer(EncryptedExtensionsMessage message) {
        return new EncryptedExtensionsSerializer(message, tlsContext.getChooser().getSelectedProtocolVersion());
    }

    @Override
    public void adjustTLSContext(EncryptedExtensionsMessage message) {
        if (message.getExtensions() != null) {
            for (ExtensionMessage extension : message.getExtensions()) {
                ExtensionHandler handler = HandlerFactory.getExtensionHandler(tlsContext,
                        extension.getExtensionTypeConstant(), HandshakeMessageType.ENCRYPTED_EXTENSIONS);
                handler.adjustTLSContext(extension);
            }
        }
    }

}
