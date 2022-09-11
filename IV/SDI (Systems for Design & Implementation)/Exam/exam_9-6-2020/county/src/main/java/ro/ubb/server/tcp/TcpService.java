package ro.ubb.server.tcp;

import ro.ubb.server.model.ScannerService;

import java.util.concurrent.CompletableFuture;

public interface TcpService {
    static final String ADD_SCANNER = "addScanner";

    CompletableFuture<ScannerService> addScanner(ScannerService scannerService);
}
