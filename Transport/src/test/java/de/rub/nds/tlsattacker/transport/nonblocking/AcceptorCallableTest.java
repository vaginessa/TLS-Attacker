/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 *
 * Copyright 2014-2017 Ruhr University Bochum / Hackmanit GmbH
 *
 * Licensed under Apache License 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.tlsattacker.transport.nonblocking;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class AcceptorCallableTest {

    private Thread t;

    private AcceptorCallable callable;

    private ServerSocket socket;

    private FutureTask<Socket> task;

    @Before
    public void setUp() throws IOException {
        socket = new ServerSocket(0);
        callable = new AcceptorCallable(socket);
        task = new FutureTask<>(callable);
        t = new Thread(task);
    }

    @After
    public void shutDown() throws IOException {
        socket.close();
    }

    /**
     * Test of run method, of class AcceptorCallableTest.
     * 
     * @throws java.util.concurrent.ExecutionException
     * @throws java.lang.InterruptedException
     */
    @Test
    public void testRun() throws IOException, InterruptedException, ExecutionException {
        t.start();
        Socket clientSocket = new Socket();
        clientSocket.connect(new InetSocketAddress("localhost", socket.getLocalPort()));
        try {
            Thread.currentThread().sleep(10);
        } catch (InterruptedException ex) {
        }
        assertFalse(t.isAlive());
        assertTrue(task.isDone());
        assertNotNull(task.get());
    }
}
