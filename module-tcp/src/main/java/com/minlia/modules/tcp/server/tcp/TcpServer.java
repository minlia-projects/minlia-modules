package com.minlia.modules.tcp.server.tcp;

import com.minlia.modules.tcp.server.FastList;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TcpServer implements Server, Connection.Listener {

    private ServerSocket serverSocket;
    private volatile boolean isStop;
    private List<Connection> connections = new FastList(Connection.class);
    private List<Connection.Listener> listeners = new FastList(Connection.Listener.class);

    @Override
    public void setPort(Integer port) {
        try {
            if (port == null) {
                log.info("Property tcp.server.port not found. Use default port 1234");
                port = 1234;
            }
            serverSocket = new ServerSocket(port);
            log.info("Server start at port " + port);
        } catch (IOException e) {
            e.printStackTrace();
            log.error("May be port " + port + " busy.");
        }
    }

    @Override
    public int getConnectionsCount() {
        return connections.size();
    }

    @Override
    public void start() {
        new Thread(() -> {
            while (!isStop) {
                try {
                    Socket socket = serverSocket.accept();
                    if (socket.isConnected()) {
                        TcpConnection tcpConnection = new TcpConnection(socket);
                        tcpConnection.start();
                        tcpConnection.addListener(this);
                        connected(tcpConnection);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void stop() {
        isStop = true;
    }

    @Override
    public List<Connection> getConnections() {
        return connections;
    }

    @Override
    public void addListener(Connection.Listener listener) {
        listeners.add(listener);
    }

    @Override
    public void messageReceived(Connection connection, Object message) {
        log.trace("Received new message from " + connection.getAddress().getCanonicalHostName());
        log.trace("Class name: " + message.getClass().getCanonicalName() + ", toString: " + message.toString());
        for (Connection.Listener listener : listeners) {
            listener.messageReceived(connection, message);
        }
    }

    @Override
    public void connected(Connection connection) {
        log.info("New connection! Ip: " + connection.getAddress().getCanonicalHostName() + ".");
        connections.add(connection);
        log.info("Current connections count: " + connections.size());
        for (Connection.Listener listener : listeners) {
            listener.connected(connection);
        }
    }

    @Override
    public void disconnected(Connection connection) {
        log.info("Disconnect! Ip: " + connection.getAddress().getCanonicalHostName() + ".");
        connections.remove(connection);
        log.info("Current connections count: " + connections.size());
        for (Connection.Listener listener : listeners) {
            listener.disconnected(connection);
        }
    }
}
