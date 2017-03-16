/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 *
 * Copyright 2014-2016 Ruhr University Bochum / Hackmanit GmbH
 *
 * Licensed under Apache License 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.tlsattacker.tls.protocol.parser.extension;

import de.rub.nds.tlsattacker.tls.constants.ExtensionByteLength;
import de.rub.nds.tlsattacker.tls.protocol.extension.ECPointFormatExtensionMessage;

/**
 *
 * @author Robert Merget - robert.merget@rub.de
 */
public class ECPointFormatExtensionParser extends ExtensionParser<ECPointFormatExtensionMessage> {

    public ECPointFormatExtensionParser(int startposition, byte[] array) {
        super(startposition, array);
    }

    @Override
    public ECPointFormatExtensionMessage parse() {
        ECPointFormatExtensionMessage msg = new ECPointFormatExtensionMessage();
        parseExtensionType(msg);
        parseExtensionLength(msg);
        msg.setPointFormatsLength(parseIntField(ExtensionByteLength.EC_POINT_FORMATS_LENGTH));
        msg.setPointFormats(parseByteArrayField(msg.getPointFormatsLength().getValue()));
        return msg;
    }

}