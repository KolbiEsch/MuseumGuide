package io.github.kolbiesch.museumguide.helper;

import io.github.kolbiesch.museumguide.services.QRCodeService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final QRCodeService qrCodeService;

    public DataInitializer(QRCodeService qrCodeService) {
        this.qrCodeService = qrCodeService;
    }

    @Override
    public void run(String... args) throws Exception {
        for (long i = 1; i <= 8; i++) {
            qrCodeService.generateQRCodeForExhibit(i);
        }
    }
}
