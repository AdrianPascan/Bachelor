package ro.ubb.scanner.repository;

import lombok.*;
import ro.ubb.scanner.model.ScannerClient;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ScannerClientRepository {
    private List<ScannerClient> scannerClients = new ArrayList<>();

    public void addScannerClient(ScannerClient scannerClient) {
        scannerClients.forEach(sc -> {
            if (sc.getName().equals(scannerClient.getName())) {
                throw new IllegalArgumentException(
                        String.format("Scanner with name='%s' already exists", scannerClient.getName()));
            }
            if (sc.getCountyId().equals(scannerClient.getCountyId())) {
                throw new IllegalArgumentException(
                        String.format("Scanner with county-id=%d already exists", scannerClient.getCountyId()));
            }
        });
        scannerClients.add(scannerClient);
    }
}
