package net.atom.lesson.sample.client;

import net.atom.lesson.sample.client.bean.ServerInfo;
import net.atom.library.clink.utils.CloseUtils;

import java.io.*;
import java.net.Inet4Address;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class TCPClient {
    private final Socket socket;
    private final ReadHandler readHandler;
    private final PrintStream printStream;

    public TCPClient(Socket socket, ReadHandler readHandler) throws IOException {
        this.socket = socket;
        this.readHandler = readHandler;
        this.printStream = new PrintStream(socket.getOutputStream());
    }

    public static TCPClient startWith(ServerInfo info) throws IOException {
        Socket socket = new Socket();
        // 超时时间
        socket.setSoTimeout(3000);

        // 连接本地，端口2000；超时时间3000ms
        socket.connect(new InetSocketAddress(Inet4Address.getByName(info.getAddress()), info.getPort()), 3000);

        System.out.println("已发起服务器连接，并进入后续流程～");
        System.out.println("客户端信息：" + socket.getLocalAddress() + " P:" + socket.getLocalPort());
        System.out.println("服务器信息：" + socket.getInetAddress() + " P:" + socket.getPort());

        // 接收数据
        ReadHandler readHandler = new ReadHandler(socket.getInputStream());
        readHandler.start();
        TCPClient tcpClient = null;

        try {
            // 发送数据
            tcpClient = new TCPClient(socket, readHandler);
        } catch (Exception e) {
            System.out.println("异常关闭");
            CloseUtils.close(socket);
        }
        return tcpClient;

    }

    public void exit() {
        readHandler.exit();
        CloseUtils.close(socket);
        CloseUtils.close(printStream);
    }

    public void send(String str) {
        this.printStream.println(str);
    }

    static class ReadHandler extends Thread {
        private boolean done = false;
        private InputStream inputStream;

        public ReadHandler(InputStream inputStream) {
            this.inputStream = inputStream;
        }

        @Override
        public void run() {
            super.run();

            try {
                // 得到输入流，用于接收数据
                BufferedReader socketInput = new BufferedReader(new InputStreamReader(inputStream));

                do {
                    // 客户端拿到一条数据
                    String str = null;
                    try {
                        str = socketInput.readLine();
                    } catch (SocketTimeoutException e) {
                        // 超时 socket.setSoTimeout(3000)，则继续
                        continue;
                    }
                    if (str == null) {
                        System.out.println("连接已关闭，无法读取数据！");
                        break;
                    }
                    // 打印到屏幕
                    System.out.println(str);
                } while (!done);
            } catch (Exception e) {
                if (!done) {
                    System.out.println("连接异常断开：" + e.getMessage());
                }
            } finally {
                // 连接关闭
                CloseUtils.close(inputStream);
            }
        }

        void exit() {
            done = true;
            CloseUtils.close(inputStream);
        }
    }
}
