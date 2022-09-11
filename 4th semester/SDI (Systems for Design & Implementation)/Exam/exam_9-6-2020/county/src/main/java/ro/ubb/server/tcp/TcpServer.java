package ro.ubb.server.tcp;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.function.UnaryOperator;

public class TcpServer {
    @Autowired
    private ExecutorService executorService;

    private Map<String, UnaryOperator<Message>> methodHandlers = new HashMap<>();

    public void addHandler(String methodName, UnaryOperator<Message> handler) {
        methodHandlers.put(methodName, handler);
    }

    public void startServer() {
        System.out.println("SERVER STARTED");

        try (var serverSocket = new ServerSocket(Message.SERVER_PORT)) {
            while (true) {
                Socket client = serverSocket.accept();
                executorService.submit(new ClientHandler(client));
            }
        } catch (IOException exception) {
            throw new TcpException("Error connecting clients", exception);
        }
    }

    @AllArgsConstructor
    private class ClientHandler implements Runnable {
        private Socket socket;

        @Override
        public void run() {
            try (var inputStream = socket.getInputStream();
                 var outputStream = socket.getOutputStream()) {
                Message request = new Message();
                request.readFrom(inputStream);
                System.out.println("Received request: " + request);

                Message response = methodHandlers.get(request.getHeader())
                        .apply(request);
                response.writeTo(outputStream);
            } catch (IOException exception) {
                throw new TcpException("Error processing client", exception);
            }
        }
    }
}
