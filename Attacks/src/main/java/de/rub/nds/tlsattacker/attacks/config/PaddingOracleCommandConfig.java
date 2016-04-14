/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS.
 *
 * Copyright (C) 2015 Chair for Network and Data Security,
 *                    Ruhr University Bochum
 *                    (juraj.somorovsky@rub.de)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.rub.nds.tlsattacker.attacks.config;

import com.beust.jcommander.Parameter;
import de.rub.nds.tlsattacker.tls.config.ClientCommandConfig;
import de.rub.nds.tlsattacker.tls.constants.CipherSuite;
import java.util.LinkedList;

/**
 * 
 * @author Juraj Somorovsky <juraj.somorovsky@rub.de>
 */
public class PaddingOracleCommandConfig extends ClientCommandConfig {

    public static final String ATTACK_COMMAND = "padding_oracle";

    @Parameter(names = "-block_size", description = "Block size of the to be used block cipher")
    Integer blockSize = 16;

    public PaddingOracleCommandConfig() {
	cipherSuites = new LinkedList<>();
	cipherSuites.add(CipherSuite.TLS_RSA_WITH_AES_128_CBC_SHA);
	cipherSuites.add(CipherSuite.TLS_DHE_DSS_WITH_AES_128_CBC_SHA);
	cipherSuites.add(CipherSuite.TLS_DHE_RSA_WITH_AES_128_CBC_SHA);
	cipherSuites.add(CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA);
	cipherSuites.add(CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA256);
	cipherSuites.add(CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA);
	cipherSuites.add(CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA256);
    }

    public Integer getBlockSize() {
	return blockSize;
    }

    public void setBlockSize(Integer blockSize) {
	this.blockSize = blockSize;
    }

}
