package sheba.backend.app.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;

public class QRCodeGenerator {

    public static String generateQRCode(String qrName, String content, String filePath) throws WriterException, IOException {
        String qrCodeName = qrName + "-QRCODE.png";
        var qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, 400, 400);

        try {
            Path path = FileSystems.getDefault().getPath(qrCodeName);
            MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
        } catch (InvalidPathException e) {
            System.out.println("Path not found, saving in the project directory instead.");
            qrCodeName = qrCodeName.substring(Endpoints.QR_LOCATION.length());
            Path path = Paths.get(qrCodeName);
            MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
        }

        return qrCodeName; // Return the file path where the QR code is saved
    }
}
