/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 *
 * Copyright 2014-2017 Ruhr University Bochum / Hackmanit GmbH
 *
 * Licensed under Apache License 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.tlsattacker.core.config.delegate;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;
import de.rub.nds.tlsattacker.core.config.Config;
import de.rub.nds.tlsattacker.core.constants.ECPointFormat;
import de.rub.nds.tlsattacker.core.constants.NamedCurve;
import java.util.LinkedList;
import org.apache.commons.lang3.builder.EqualsBuilder;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class EllipticCurveDelegateTest {

    private EllipticCurveDelegate delegate;
    private JCommander jcommander;
    private String[] args;

    @Before
    public void setUp() {
        this.delegate = new EllipticCurveDelegate();
        this.jcommander = new JCommander(delegate);
    }

    /**
     * Test of getPointFormats method, of class EllipticCurveDelegate.
     */
    @Test
    public void testGetPointFormats() {
        args = new String[2];
        args[0] = "-point_formats";
        args[1] = "ANSIX962_COMPRESSED_PRIME,UNCOMPRESSED";
        jcommander.parse(args);
        assertTrue("UNCOMPRESSED should get parsed correctly",
                delegate.getPointFormats().contains(ECPointFormat.UNCOMPRESSED));
        assertTrue("ANSIX962_COMPRESSED_PRIME should get parsed correctly",
                delegate.getPointFormats().contains(ECPointFormat.ANSIX962_COMPRESSED_PRIME));
    }

    @Test(expected = ParameterException.class)
    public void testInvalidPointFormats() {
        args = new String[2];
        args[0] = "-point_formats";
        args[1] = "NOTAPOINTFORMAT"; // Not a correct
        // point format
        jcommander.parse(args);
    }

    /**
     * Test of setPointFormats method, of class EllipticCurveDelegate.
     */
    @Test
    public void testSetPointFormats() {
        LinkedList<ECPointFormat> supportedPointFormats = new LinkedList<>();
        supportedPointFormats.add(ECPointFormat.UNCOMPRESSED);
        delegate.setPointFormats(supportedPointFormats);
        assertTrue("PointFormats setter is not working correctly",
                delegate.getPointFormats().equals(supportedPointFormats));
    }

    /**
     * Test of getNamedCurves method, of class EllipticCurveDelegate.
     */
    @Test
    public void testGetNamedCurves() {
        args = new String[2];
        args[0] = "-named_curve";
        args[1] = "SECP192R1,SECP256R1";
        jcommander.parse(args);
        assertTrue("SECP192R1 should get parsed correctly", delegate.getNamedCurves().contains(NamedCurve.SECP192R1));
        assertTrue("SECP256R1 should get parsed correctly", delegate.getNamedCurves().contains(NamedCurve.SECP256R1));
    }

    @Test(expected = ParameterException.class)
    public void testInvalidCurves() {
        args = new String[2];
        args[0] = "-named_curve";
        args[1] = "NOTACURVE"; // Not a correct
        // Curve
        jcommander.parse(args);
    }

    /**
     * Test of setNamedCurves method, of class EllipticCurveDelegate.
     */
    @Test
    public void testSetNamedCurves() {
        LinkedList<NamedCurve> supportedNamedCurves = new LinkedList<>();
        supportedNamedCurves.add(NamedCurve.BRAINPOOLP384R1);
        delegate.setNamedCurves(supportedNamedCurves);
        assertTrue("NamedCurves setter is not working correctly", delegate.getNamedCurves()
                .equals(supportedNamedCurves));
    }

    /**
     * Test of applyDelegate method, of class EllipticCurveDelegate.
     */
    @Test
    public void testApplyDelegate() {
        args = new String[4];
        args[0] = "-named_curve";
        args[1] = "SECP192R1,SECP256R1";
        args[2] = "-point_formats";
        args[3] = "ANSIX962_COMPRESSED_PRIME,UNCOMPRESSED";
        Config config = Config.createConfig();
        config.setNamedCurves(new NamedCurve[0]);
        config.setDefaultClientSupportedPointFormats(new ECPointFormat[0]);
        config.setDefaultServerSupportedPointFormats(new ECPointFormat[0]);
        jcommander.parse(args);
        delegate.applyDelegate(config);
        assertTrue("SECP192R1 should get parsed correctly", config.getNamedCurves().contains(NamedCurve.SECP192R1));
        assertTrue("SECP256R1 should get parsed correctly", config.getNamedCurves().contains(NamedCurve.SECP192R1));
        assertTrue("UNCOMPRESSED should get parsed correctly",
                config.getDefaultClientSupportedPointFormats().contains(ECPointFormat.UNCOMPRESSED));
        assertTrue("ANSIX962_COMPRESSED_PRIME should get parsed correctly", config
                .getDefaultClientSupportedPointFormats().contains(ECPointFormat.ANSIX962_COMPRESSED_PRIME));
        assertTrue("UNCOMPRESSED should get parsed correctly",
                config.getDefaultServerSupportedPointFormats().contains(ECPointFormat.UNCOMPRESSED));
        assertTrue("ANSIX962_COMPRESSED_PRIME should get parsed correctly", config
                .getDefaultServerSupportedPointFormats().contains(ECPointFormat.ANSIX962_COMPRESSED_PRIME));

    }

    @Test
    public void testNothingSetNothingChanges() {
        Config config = Config.createConfig();
        Config config2 = Config.createConfig();
        delegate.applyDelegate(config);
        assertTrue(EqualsBuilder.reflectionEquals(config, config2, "keyStore", "ourCertificate"));// little
        // ugly
    }
}
