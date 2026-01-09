package io.github.kolbiesch.museumguide.services;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.stereotype.Service;

import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class QRCodeService {

    private static final String QR_CODE_IMAGE_PATH = "./qr_codes/";
    private static final String FRONTEND_URL_BASE = "http://localhost:3000/exhibit/";

    public void generateQRCodeForExhibit(Long exhibitId) throws Exception {
        Path directory = Paths.get(QR_CODE_IMAGE_PATH);
        if (!Files.exists(directory)) {
            Files.createDirectories(directory);
        }

        String content = FRONTEND_URL_BASE + exhibitId;
        String filename = "exhibit_" + exhibitId + ".png";

        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, 350, 350);

        Path path = FileSystems.getDefault().getPath(QR_CODE_IMAGE_PATH + filename);
        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);

        System.out.println("QR Code generated for Exhibit " + exhibitId + " at: " + path.toAbsolutePath());
    }
}
