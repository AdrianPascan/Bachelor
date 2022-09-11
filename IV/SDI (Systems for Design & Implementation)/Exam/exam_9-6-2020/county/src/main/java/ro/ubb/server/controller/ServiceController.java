package ro.ubb.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import ro.ubb.server.model.CountyServer;
import ro.ubb.server.model.ScannerService;
import ro.ubb.server.repository.CountyRepository;
import ro.ubb.server.repository.ScannerRepository;

public class ServiceController {
    @Autowired
    private ScannerRepository scannerRepository;

    @Autowired
    private CountyRepository countyRepository;

    public ScannerService addScanner(ScannerService scannerService) {
        CountyServer countyServer = countyRepository.findCountyServer(scannerService.getCountyId());
        if (countyServer == null) {
            throw new IllegalArgumentException(
                    String.format("County with id=%d does not exist", scannerService.getCountyId()));
        }
        countyServer.getNr().incrementAndGet();
        countyServer.getCandidateA().addAndGet(scannerService.getCandidateA());
        countyServer.getCandidateB().addAndGet(scannerService.getCandidateB());
        countyServer.getCandidateC().addAndGet(scannerService.getCandidateC());

        return scannerRepository.addScannerService(scannerService);
    }

    public CountyServer addCounty(CountyServer countyServer) {
        return countyRepository.addCountyServer(countyServer);
    }
}
