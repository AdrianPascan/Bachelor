package ro.ubb.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;
import ro.ubb.monitorcore.model.County;
import ro.ubb.monitorweb.converter.CountyConverter;
import ro.ubb.monitorweb.dto.CountyDto;
import ro.ubb.server.controller.ServiceController;
import ro.ubb.server.model.CountyServer;
import ro.ubb.server.model.ScannerService;
import ro.ubb.server.repository.CountyRepository;
import ro.ubb.server.tcp.TcpService;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

public class ServiceServer implements TcpService {
    @Autowired
    private ServiceController serviceController;

    @Autowired
    private ExecutorService executorService;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public CompletableFuture<ScannerService> addScanner(ScannerService scannerService) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException exception) {
                exception.printStackTrace();
            }

            return serviceController.addScanner(scannerService);
        });
    }

    public void addCounty(CountyServer countyServer) {
        serviceController.addCounty(countyServer);

        executorService.submit(() -> {
            while(true) {
                System.out.println("County thread: " + countyServer.getName());
                int initial = countyServer.getNr().get();
                Thread.sleep(5000);
                int after = countyServer.getNr().get();

                if (initial < after) {
                    System.out.println(String.format("County '%s': data changed from %d votes to %d votes", countyServer.getName(), initial, after));

                    County county = County.builder()
                            .countyName(countyServer.getName())
                            .candidateA(countyServer.getCandidateA().get())
                            .candidateB(countyServer.getCandidateB().get())
                            .candidateC(countyServer.getCandidateC().get())
                            .nr(countyServer.getNr().get())
                            .build();

                    restTemplate.postForObject(
                            "http://localhost:8081/api/voting",
                            CountyConverter.convertCountServerToCountyDto(county),
                            CountyDto.class
                            );
                }
                else {
                    System.out.println(String.format("County '%s': data not changed", countyServer.getName()));
                }
            }
        });
    }
}
