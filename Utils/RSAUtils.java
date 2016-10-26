package com.kim.imageloader.Utils;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Date;

import javax.crypto.Cipher;

/**
 * Created by kim on 16-10-26.
 */
public class RSAUtils {
    static String publicKeyFilename = "home\\kim\\kimpublicKeyFile.txt";
    static String privateKeyFilename = "home\\kim\\privateKeyFile.txt";

    /**
     * 生成公钥和私钥
     */
    public static void createKeys() {
        try {
            SecureRandom secureRandom = new SecureRandom(new Date().toString().getBytes());
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(1024, secureRandom);
            KeyPair keyPair = keyPairGenerator.genKeyPair();
            byte[] publicKeyBytes = keyPair.getPublic().getEncoded();
            //将公钥写入文件
            writeFile(publicKeyBytes,publicKeyFilename);
            /*FileOutputStream outputStream = new FileOutputStream(publicKeyFilename);
            outputStream.write(publicKeyBytes);
            outputStream.close();*/

            byte[] privateKeyBytes = keyPair.getPrivate().getEncoded();
            //将私钥写入文件
            writeFile(privateKeyBytes,privateKeyFilename);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void writeFile(byte[] bytes, String path) {
        File file = new File(path);
        FileOutputStream outputStream = null;
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            outputStream = new FileOutputStream(file);
            outputStream.write(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 读取公钥
     *
     * @param filename 公钥路径
     * @return
     */
    public static PublicKey readPublicKey(String filename) {
        try {
            File f = new File(filename);
            FileInputStream fis = new FileInputStream(f);
            DataInputStream dis = new DataInputStream(fis);
            byte[] keyBytes = new byte[(int) f.length()];
            dis.readFully(keyBytes);
            dis.close();
            X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
            KeyFactory kf = KeyFactory.getInstance("RSA");
            return kf.generatePublic(spec);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 读取私钥
     *
     * @param filename
     * @return
     */
    public static PrivateKey readPrivateKey(String filename) {
        try {
            File f = new File(filename);
            FileInputStream fis = new FileInputStream(f);
            DataInputStream dis = new DataInputStream(fis);
            byte[] keyBytes = new byte[(int) f.length()];
            dis.readFully(keyBytes);
            dis.close();
            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory kf = KeyFactory.getInstance("RSA");
            return kf.generatePrivate(spec);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * RSA加密
     *
     * @param input
     * @return
     */
    public static byte[] encryptRSA(String input) {
        byte[] cipherText = null;
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            RSAPublicKey pubKey = (RSAPublicKey) readPublicKey(publicKeyFilename);
            cipher.init(Cipher.ENCRYPT_MODE, pubKey);
            cipherText = cipher.doFinal(input.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cipherText;
    }

    /**
     * RSA解密
     *
     * @param encryptedFile 需解密的文件
     * @return
     */
    public static byte[] decryptRSA(byte[] encryptedFile) {
        byte[] plainText = null;
        RSAPrivateKey privKey = (RSAPrivateKey) readPrivateKey(privateKeyFilename);
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, privKey);
            plainText = cipher.doFinal(encryptedFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return plainText;
    }
}
