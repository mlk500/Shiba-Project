package sheba.backend.app.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.io.IOException;
import java.nio.file.*;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class QRCodeGenerator {

//    public static String generateQRCode(String qrName, String content, String filePath) throws WriterException, IOException {
//        String qrCodeName = qrName + "-QRCODE.png";
//        var qrCodeWriter = new QRCodeWriter();
//        BitMatrix bitMatrix = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, 400, 400);
//
//        try {
//            Path path = FileSystems.getDefault().getPath(qrCodeName);
//            MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
//        } catch (InvalidPathException e) {
//            System.out.println("Path not found, saving in the project directory instead.");
//            qrCodeName = qrCodeName.substring(filePath.length());
//            Path path = Paths.get(qrCodeName);
//            MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
//        }
//
//        return qrCodeName;
//    }

//    public static String generateQRCode(String qrName, String content, String filePath,
//                                        String logoPath) throws WriterException, IOException {
//        String qrCodeName = qrName + "-QRCODE.png";
//        var qrCodeWriter = new QRCodeWriter();
//        BitMatrix bitMatrix = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, 400, 400);
//
//        Path directoryPath = Paths.get(filePath);
//        if (!Files.exists(directoryPath)) {
//            Files.createDirectories(directoryPath);
//        }
//
//        Path fullPath = directoryPath.resolve(qrCodeName);
//        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", fullPath);
//
//        return fullPath.toString();
//    }

//    public static String generateQRCode(String qrName, String content, String filePath,
//                                        String logoPath) throws WriterException, IOException {
//        String qrCodeName = qrName + "-QRCODE.png";
//        var qrCodeWriter = new QRCodeWriter();
//        BitMatrix bitMatrix = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, 400, 400);
//
//        Path directoryPath = Paths.get(filePath);
//        if (!Files.exists(directoryPath)) {
//            Files.createDirectories(directoryPath);
//        }
//
//        Path fullPath = directoryPath.resolve(qrCodeName);
//        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", fullPath);
//
//        return fullPath.toString();
//    }

    public static String generateQRCode(String qrName, String content, String filePath, String imgPath) throws WriterException, IOException {
        String qrCodeName = qrName + "-QRCODE.png";
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        BitMatrix bitMatrix = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, 400, 400, hints);

        Path directoryPath = Paths.get(filePath);
        if (!Files.exists(directoryPath)) {
            Files.createDirectories(directoryPath);
        }

        Path fullPath = directoryPath.resolve(qrCodeName);

        BufferedImage qrImage = MatrixToImageWriter.toBufferedImage(bitMatrix);

        File logoFile = new File(imgPath);
        if (!logoFile.exists()) {
            throw new IOException("Image file not found at " + logoFile.getAbsolutePath());
        }
        BufferedImage logo = ImageIO.read(logoFile);


        int logoSize = qrImage.getWidth() / 3; // img size relative to qr
        Image scaledLogo = logo.getScaledInstance(logoSize, logoSize, Image.SCALE_SMOOTH);
        BufferedImage logoResized = new BufferedImage(logoSize, logoSize, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = logoResized.createGraphics();
        g2d.drawImage(scaledLogo, 0, 0, null);
        g2d.dispose();

        int deltaHeight = qrImage.getHeight() - logoResized.getHeight();
        int deltaWidth = qrImage.getWidth() - logoResized.getWidth();

        BufferedImage combined = new BufferedImage(qrImage.getHeight(), qrImage.getWidth(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = (Graphics2D) combined.getGraphics();
        g.drawImage(qrImage, 0, 0, null);
        g.drawImage(logoResized, Math.round(deltaWidth / 2), Math.round(deltaHeight / 2), null);

        ImageIO.write(combined, "PNG", fullPath.toFile());

        return fullPath.toString();
    }
}
