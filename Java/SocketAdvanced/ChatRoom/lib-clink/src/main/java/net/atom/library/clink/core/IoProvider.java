package net.atom.library.clink.core;

import java.io.Closeable;
import java.nio.channels.SocketChannel;

/**
 * 输入、输出提供者
 */
public interface IoProvider extends Closeable {
    boolean registerInput(SocketChannel channel, HandleInputCallback callback);

    boolean registerOutput(SocketChannel channel, HandleOutputCallback callback);

    void unRegisterInput(SocketChannel channel);

    void unRegisterOutput(SocketChannel channel);

    abstract class HandleInputCallback implements Runnable {
        @Override
        public final void run() {
            canProviderInput();
        }

        /**
         * can provider input
         */
        protected abstract void canProviderInput();
    }

    abstract class HandleOutputCallback implements Runnable {
        /**
         * 附加值
         * SocketChannel是否能发送数据取决于网卡状态，当然SocketChannel并不一定能发送数据，
         * 所以我们需要注册SocketChannel，当SocketChannel可以发送的时候，通过HandleOutputCallback回调我，之前我想发送什么数据呢？
         * 想要发送的数据可以附加到attach附加值上
         * 待正在发送数据时，可以从attach附加值上拿到之前想要发送的数据
         */
        private Object attach;

        @Override
        public final void run() {
            canProviderOutput(attach);
        }

        public final void setAttach(Object attach) {
            this.attach = attach;
        }

        public final <T> T getAttach() {
            return (T) this.attach;
        }

        /**
         * can provider output
         * @param attach
         */
        protected abstract void canProviderOutput(Object attach);
    }
}
