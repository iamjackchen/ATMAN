package data;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import data.types.Attendee;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

public class MathUtilities {

    public static String generateQRData(int length) {

        Random random = new Random((long)(Math.random()*10000000));
        return random.ints(48, 123).limit(length).collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();

    }

    public static BufferedImage generateQRCodeImage(String QRData) throws WriterException {
        QRCodeWriter qrGenerator = new QRCodeWriter();
        BitMatrix bitmatrix = qrGenerator.encode(QRData, BarcodeFormat.QR_CODE, 400, 400);

        return MatrixToImageWriter.toBufferedImage(bitmatrix);
    }

    public static Integer tryParseInt(String e) {

        try {
            return Integer.parseInt(e);
        } catch (NumberFormatException ex) {
            return null;
        }

    }


}
