package sheba.backend.app.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.DataFormatException;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.Inflater;

public class ImageActions {

    public static byte[] compress(byte[] data) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try (DeflaterOutputStream deflater = new DeflaterOutputStream(outputStream)) {
            deflater.write(data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return outputStream.toByteArray();
    }

    public static byte[] decompress(byte[] data) throws IOException, DataFormatException {
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[65536];

        try {
            while (!inflater.finished()) {
                int count = inflater.inflate(buffer);
                outputStream.write(buffer, 0, count);
            }
        } finally {
            outputStream.close();
        }
        return outputStream.toByteArray();
    }
}
