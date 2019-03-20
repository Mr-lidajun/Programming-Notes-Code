package net.atom.lesson.sample.client;

import net.atom.lesson.sample.client.bean.ServerInfo;
import net.atom.library.clink.utils.CloseUtils;

import java.io.*;
import java.net.Inet4Address;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class TCPClient {
    public static void linkWith(ServerInfo info) throws IOException {
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

        try {
            // 发送数据
            write(socket);
        } catch (Exception e) {
            System.out.println("异常关闭");
        }
        // 发送数据完成，则关闭接收
        readHandler.exit();

        // 释放资源
        socket.close();
        System.out.println("客户端已退出～");

    }

    private static void write(Socket client) throws IOException {
        // 构建键盘输入流
        InputStream in = System.in;
        BufferedReader input = new BufferedReader(new InputStreamReader(in));

        // 得到Socket输出流，并转换为打印流
        OutputStream outputStream = client.getOutputStream();
        PrintStream socketPrintStream = new PrintStream(outputStream);

        do {
            // 键盘读取一行
            String str = input.readLine();
            // 发送到服务器
            socketPrintStream.println(str);
            if ("bye".equalsIgnoreCase(str)) {
                break;
            }
        } while (true);

        // 资源释放
        CloseUtils.close(socketPrintStream);
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
