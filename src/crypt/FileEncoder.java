package crypt;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class FileEncoder {

    // Encryption function
    // function 1
    public static void encryptEcb(String filenamePlain,
                                  String filenameEnc,
                                  byte[] key)
            throws IOException, NoSuchAlgorithmException,
            NoSuchPaddingException, InvalidKeyException,
            IllegalBlockSizeException,
            BadPaddingException {

        // Creating cipher instance OF AES encryption
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");

        // Specifying the algorithm
        SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);

        // Try block to check for exceptions
        try (FileInputStream fis = new FileInputStream(filenamePlain);

             // Creating objects of BufferedInputStream,
             // FileOutputStream and BufferedOutputStream
             BufferedInputStream inputstream = new BufferedInputStream(fis);
             FileOutputStream outputstream = new FileOutputStream(filenameEnc);
             BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputstream)) {

            // Defining the buffer
            byte[] ibufffer = new byte[1024];

            int length;

            // Reading while read buffer has data
            while ((length = inputstream.read(ibufffer))
                    != -1) {

                // Creating cipher with buffer
                byte[] obuffer
                        = cipher.update(ibufffer, 0, length);

                if (obuffer != null)

                    // Writing encrypted text to buffer
                    bufferedOutputStream.write(obuffer);
            }

            byte[] obuffer = cipher.doFinal();

            if (obuffer != null)
                bufferedOutputStream.write(obuffer);
        }
    }

    // Method 3
    // Decryption method
    public static void decryptEcb(String filenameEnc,
                                  String filenameDec,
                                  byte[] key)
            throws IOException, NoSuchAlgorithmException,
            NoSuchPaddingException, InvalidKeyException,
            IllegalBlockSizeException,
            BadPaddingException {

        // Try block to check for exceptions
        try (FileInputStream inputStream = new FileInputStream(filenameEnc);
             FileOutputStream outputStream = new FileOutputStream(filenameDec)) {
            // Defining buffer
            byte[] ibuffer = new byte[1024];
            int length;

            // Creating cipher instance OF AES decryption
            Cipher cipher = Cipher.getInstance(
                    "AES/ECB/PKCS5PADDING");
            SecretKeySpec secretKeySpec
                    = new SecretKeySpec(key, "AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);

            // While input stream not empty
            while ((length = inputStream.read(ibuffer))
                    != -1) {

                // Reading into the buffer
                byte[] obuffer
                        = cipher.update(ibuffer, 0, length);
                if (obuffer != null)

                    // Now writing to output buffer
                    outputStream.write(obuffer);
            }

            byte[] obuffer = cipher.doFinal();
            if (obuffer != null)
                outputStream.write(obuffer);
        }
    }
}
