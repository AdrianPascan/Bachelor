package ro.ubb.scanner.tcp;

import java.io.IOException;
import java.net.Socket;
import ro.ubb.server.tcp.Message;
import ro.ubb.server.tcp.TcpException;

public class TcpClient {
    public Message sendAndReceive(Message request) {
        try (var socket = new Socket(Message.HOST, Message.SERVER_PORT);
             var inputStream = socket.getInputStream();
             var outputStream = socket.getOutputStream()
        ) {
            System.out.println("TcpClient - sendAndReceive -> sending request: " + request);
            request.writeTo(outputStream);

            Message response = new Message();
            response.readFrom(inputStream);
            System.out.println("TcpClient - sendAndReceive -> received response: " + response);

            return response;
        } catch (IOException exception) {
            throw new TcpException("Error on connection to server " + exception.getMessage(), exception);
        }
    }
}
