package net.atom.lesson.sample.server;

import net.atom.lesson.sample.foo.constants.TCPConstants;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import net.atom.library.clink.core.IoContext;
import net.atom.library.clink.impl.IoSelectorProvider;

public class Server {
    public static void main(String[] args) throws IOException {
        IoContext.setup()
                .ioProvider(new IoSelectorProvider())
                .start();

        TCPServer tcpServer = new TCPServer(TCPConstants.PORT_SERVER);
        boolean isSucceed = tcpServer.start();
        if (!isSucceed) {
            System.out.println("Start TCP server failed!");
            return;
        }

        UDPProvider.start(TCPConstants.PORT_SERVER);

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        String str;
        do {
            str = bufferedReader.readLine();
            tcpServer.broadcast(str);
        } while (!"bye".equalsIgnoreCase(str));

        UDPProvider.stop();
        tcpServer.stop();
        IoContext.close();
    }
}
