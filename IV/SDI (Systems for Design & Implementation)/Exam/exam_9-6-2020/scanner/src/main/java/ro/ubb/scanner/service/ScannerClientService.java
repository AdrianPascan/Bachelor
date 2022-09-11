package ro.ubb.scanner.service;

import org.springframework.beans.factory.annotation.Autowired;
import ro.ubb.scanner.model.ScannerClient;
import ro.ubb.scanner.repository.ScannerClientRepository;
import ro.ubb.scanner.tcp.TcpClient;
import ro.ubb.server.ServerApp;
import ro.ubb.server.tcp.Message;
import ro.ubb.server.tcp.TcpException;
import ro.ubb.server.tcp.TcpService;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicInteger;

public class ScannerClientService {
    @Autowired
    private ExecutorService executorService;

    @Autowired
    private TcpClient tcpClient;

    @Autowired
    private ScannerClientRepository repository;

    private AtomicInteger count = new AtomicInteger(0);

    public void addScanner(ScannerClient scannerClient) {
        repository.addScannerClient(scannerClient);

        executorService.submit(() -> {
            while (true) {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException exception) {
                    exception.printStackTrace();
                }

                int candidateA = new Random().nextInt() % 2;
                if (candidateA < 0) candidateA = -candidateA;
                int candidateB = new Random().nextInt() % 2;
                if (candidateB < 0) candidateB = -candidateB;
                int candidateC = new Random().nextInt() % 2;
                if (candidateC < 0) candidateC = -candidateC;

                try {
                    int finalCandidateA = candidateA;
                    int finalCandidateB = candidateB;
                    int finalCandidateC = candidateC;

                    CompletableFuture.supplyAsync((() -> {
                        String body = String.format("%s,%d,%d,%d",
                                scannerClient.getName(), finalCandidateA, finalCandidateB, finalCandidateC);

                        Message request = new Message(scannerClient.getCountyId(), TcpService.ADD_SCANNER, body);

                        System.out.println(String.format("%d - %s %d %d %d",
                                count.incrementAndGet(), scannerClient.getName(), finalCandidateA, finalCandidateB, finalCandidateC));
                        Message response = tcpClient.sendAndReceive(request);
                        if (response.getHeader().equals(ServerApp.ERROR)) {
                            throw new TcpException(
                                    String.format("TcpException -> %s", response.getBody()));
                        }

                        return response.getBody();
                    }), executorService);
                }
                catch (TcpException exception) {
                    System.out.println(String.format("TcpException | %s", exception.getMessage()));
                    System.out.println(String.format("Stopping scanner '%s'...", scannerClient.getName()));
                    System.out.println(String.format("Scanner '%s' stopped.", scannerClient.getName()));
                    break;
                }
            }
        });
    }
}
