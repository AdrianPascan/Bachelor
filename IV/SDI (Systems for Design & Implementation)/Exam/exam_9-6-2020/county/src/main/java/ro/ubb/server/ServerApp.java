package ro.ubb.server;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ro.ubb.server.console.ConsoleServer;
import ro.ubb.server.model.ScannerService;
import ro.ubb.server.service.ServiceServer;
import ro.ubb.server.tcp.Message;
import ro.ubb.server.tcp.TcpServer;
import ro.ubb.server.tcp.TcpService;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;

public class ServerApp {
    public static final String ERROR = "ERROR";
    public static final String OK = "OK";

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext("ro.ubb.server.config");

        ExecutorService executorService = (ExecutorService) context.getBean("executorService");
        TcpServer tcpServer = (TcpServer) context.getBean("tcpServer");
        ServiceServer serviceServer = (ServiceServer) context.getBean("serviceServer");
        ConsoleServer consoleServer = (ConsoleServer) context.getBean("consoleServer");

        addHandlers(tcpServer, serviceServer);

//        consoleServer.start();
//        tcpServer.startServer();
        executorService.submit(consoleServer::start);
        executorService.submit(tcpServer::startServer);
        executorService.shutdown();
    }

    private static void addHandlers(TcpServer tcpServer, ServiceServer serviceServer) {
        tcpServer.addHandler(TcpService.ADD_SCANNER, (request) -> {
            Integer port = request.getPort();
            String input = request.getBody();
            ScannerService scannerService = getScannerServiceFromString(port, input);

            try {
                return new Message(port, OK, String.format("Server -> added: %s", serviceServer.addScanner(scannerService).get()));
            } catch (InterruptedException | ExecutionException exception) {
                return new Message(port, ERROR, String.format("Server -> exception: %s", exception.toString()));
            }
        });
    }

    private static ScannerService getScannerServiceFromString(Integer countyId, String input) {
        List<String> tokenks = Arrays.asList(input.split(","));

        String name = tokenks.get(0);
        Integer candidateA = Integer.parseInt(tokenks.get(1));
        Integer candidateB = Integer.parseInt(tokenks.get(2));
        Integer candidateC = Integer.parseInt(tokenks.get(3));

        ScannerService scannerService = ScannerService.builder()
                .countyId(countyId)
                .name(name)
                .candidateA(candidateA)
                .candidateB(candidateB)
                .candidateC(candidateC)
                .build();

        return scannerService;
    }
}
