package ro.ubb.socket.server.tcp;

import ro.ubb.socket.common.CommonServiceException;
import ro.ubb.socket.common.Message;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.function.UnaryOperator;


public class TcpServer {
    private ExecutorService executorService;
    private Map<String, UnaryOperator<Message>> methodHandlers;

    public TcpServer(ExecutorService executorService) {
        this.executorService = executorService;
        this.methodHandlers = new HashMap<>();
    }

    public void addHandler(String methodName, UnaryOperator<Message> handler) {
        methodHandlers.put(methodName, handler);
    }

    public void startServer() {
        try (var serverSocket = new ServerSocket(Message.PORT)) {
            while (true) {
                Socket client = serverSocket.accept();
                executorService.submit(new ClientHandler(client));
            }
        } catch (IOException e) {
            throw new CommonServiceException("Error connecting clients", e);
        }
    }

    private class ClientHandler implements Runnable {
        private Socket socket;

        public ClientHandler(Socket client) {
            this.socket = client;
        }

        @Override
        public void run() {
            try (var is = socket.getInputStream();
                 var os = socket.getOutputStream()) {
                Message request = new Message();
                request.readFrom(is);
                System.out.println("Received request: " + request);

                try {
                    Message response = methodHandlers.get(request.getHeader())
                            .apply(request);
                    response.writeTo(os);
                } catch (IOException e) {
                    throw new CommonServiceException("Error processing client", e);
                }
            } catch (IOException e) {
                throw new CommonServiceException("Error processing client", e);
            }
        }
    }
}
