package ro.ubb.socket.client;

import ro.ubb.socket.client.service.CommonServiceClient;
import ro.ubb.socket.client.tcp.TcpClient;
import ro.ubb.socket.client.ui.Console;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClientApp {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        TcpClient tcpClient = new TcpClient();
        CommonServiceClient helloService = new CommonServiceClient(executorService, tcpClient);
        Console console = new Console(helloService);
        console.runConsole();

        executorService.shutdown();

        System.out.println("bye");
    }

}
