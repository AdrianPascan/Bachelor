package ro.ubb.server.repository;

import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import ro.ubb.server.model.CountyServer;
import ro.ubb.server.model.ScannerService;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ScannerRepository {
    private List<ScannerService> scannerServices = new ArrayList<>();

    public ScannerService addScannerService(ScannerService scannerService) {
        scannerServices.add(scannerService);
        return scannerService;
    }
}
