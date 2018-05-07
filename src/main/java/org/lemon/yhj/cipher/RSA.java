package org.lemon.yhj.cipher;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class RSA {

    private RSA() {
    }

    private static Logger log = LoggerFactory.getLogger(RSA.class);

    private static String CHAR_SET = "UTF-8";
    private static final String ALGORITHM = "RSA";
    private static final String SIGN_ALGORITHMS = "SHA1WithRSA";

    private static String TUIA_PRIVATEKEY = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCA"
            + "mEwggJdAgEAAoGBAKz2L7n22zSCfo3QxBNS6xMyF2cThFX4snPFTWEYs7jwjQGG"
            + "Xi9LjfKRViKmL7+mdbwR+Q+sxKtDvs2erOmpaS0m8sAiHvHXZLk+xRTe3vGM8EK"
            + "a0Z2nvQFtUlipYtH42BldMg8LVsFpSdw4Fi+xHTnrAmrAGSwbJq0ShL0zWaShAg"
            + "MBAAECgYEAntOPLhCuaICwX9/mPex1q3HjJac5bYzrqf3zOYdqehiDE3RlBr81w"
            + "RqO41CCJlZgZOyILkmza6XRD5Z3KDaGOgnT2ojHSf8ewEs/9/ZqiZsBYgWRJVbA"
            + "kzlk2vHFz2K7a3s/J6o/kdnBviEpy/QUwR6X7d8lvfHPXzna4vDvZcECQQDhpYC"
            + "4gRiJwszVWxfCtzewIIwpKe8vzlvkVyi0Wckq94F41VwNP2mv3OYFx+v9f17Z1Y"
            + "03l2j3LQV5UeUueIDZAkEAxDplvb29pKD12dzuP+xtb5PEfHAROdbgVOsOK6jqA"
            + "Q9ub4LFeUbnhQDLsmSrPY+rGFnsm8YuOE6kUm+DS9PlCQJAaWX5HjWO1KpdKzuF"
            + "ebpDFM3fKksgImBto9bieHuH7730iBCBEt8P6slv2DtuC9a242FQuSrMKMiqyeR"
            + "q/K3k4QJBAKyP8mLu1jRNfIavvDEo2IBTwWnxH6Tuqu7imt6YN9jg2ixkIwbo7L"
            + "bRIVDrKJAFpN/QTLwchw7rUaRQ/nSTjgkCQCuAwm8Nl6Cyqyital8JdYp8B02Fi"
            + "li3XzB7IjXnHmYo4sEfK5MSgPUWEK19j3zZPcPS8C95EzFXjdB9c5H7amY=";

    private static String TUIA_PUBLICKEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQ"
            + "KBgQCs9i+59ts0gn6N0MQTUusTMhdnE4RV+LJzxU1hGLO48I0Bhl4vS43ykVYip"
            + "i+/pnW8EfkPrMSrQ77NnqzpqWktJvLAIh7x12S5PsUU3t7xjPBCmtGdp70BbVJY"
            + "qWLR+NgZXTIPC1bBaUncOBYvsR056wJqwBksGyatEoS9M1mkoQIDAQAB";


    /**
     * 数据签名
     *
     * @param content 签名内容
     * 私钥
     * @return 返回签名数据
     */
    public static String signByTuia(String content) {
        try {
            PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(Base64.decodeBase64(TUIA_PRIVATEKEY));
            KeyFactory keyf = KeyFactory.getInstance(ALGORITHM);
            PrivateKey priKey = keyf.generatePrivate(priPKCS8);

            Signature signature = Signature.getInstance(SIGN_ALGORITHMS);

            signature.initSign(priKey);
            signature.update(content.getBytes(CHAR_SET));

            byte[] signed = signature.sign();

            return Base64.encodeBase64String(signed);
        } catch (Exception e) {
            log.info(e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 签名验证
     *
     * @param content
     * @param sign
     * @return
     */
    public static boolean verify(String content, String sign) {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            byte[] encodedKey = Base64.decodeBase64(TUIA_PUBLICKEY);
            PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));

            Signature signature = Signature.getInstance(SIGN_ALGORITHMS);

            signature.initVerify(pubKey);
            signature.update(content.getBytes(CHAR_SET));

            boolean bverify = signature.verify(Base64.decodeBase64(sign));
            return bverify;

        } catch (Exception e) {
            log.info(e.getMessage());
            e.printStackTrace();
        }

        return false;
    }

    /**
     * 通过公钥解密
     *
     * @param content 待解密数据
     * @param pk 公钥
     * @return 返回 解密后的数据
     */
    private static byte[] decryptByPublicKey(String content, PublicKey pk) {

        try(ByteArrayOutputStream writer = new ByteArrayOutputStream()){
            Cipher ch = Cipher.getInstance(ALGORITHM);
            ch.init(Cipher.DECRYPT_MODE, pk);
            InputStream ins = new ByteArrayInputStream(Base64.decodeBase64(content));
            // rsa解密的字节大小最多是128，将需要解密的内容，按128位拆开解密
            byte[] buf = new byte[128];
            int bufl;
            while ((bufl = ins.read(buf)) != -1) {
                byte[] block = null;

                if (buf.length == bufl) {
                    block = buf;
                } else {
                    block = new byte[bufl];
                    for (int i = 0; i < bufl; i++) {
                        block[i] = buf[i];
                    }
                }

                writer.write(ch.doFinal(block));
            }

            byte[] bytes = writer.toByteArray();
            return bytes;

        } catch (IOException e) {
            log.info(e.getMessage());
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            log.info(e.getMessage());
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            log.info(e.getMessage());
            e.printStackTrace();
        } catch (BadPaddingException e) {
            log.info(e.getMessage());
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            log.info(e.getMessage());
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            log.info(e.getMessage());
            e.printStackTrace();
        }
        return null;

    }

    /**
     * 通过私钥解密
     *
     * @param content 待解密数据
     * @param pk 公钥
     * @return 返回 解密后的数据
     */
    private static byte[] decryptByPrivateKey(String content, PrivateKey pk) {

        try(ByteArrayOutputStream writer = new ByteArrayOutputStream()) {
            Cipher ch = Cipher.getInstance(ALGORITHM);
            ch.init(Cipher.DECRYPT_MODE, pk);
            InputStream ins = new ByteArrayInputStream(Base64.decodeBase64(content));

            // rsa解密的字节大小最多是128，将需要解密的内容，按128位拆开解密
            byte[] buf = new byte[128];
            int bufl;
            while ((bufl = ins.read(buf)) != -1) {
                byte[] block = null;

                if (buf.length == bufl) {
                    block = buf;
                } else {
                    block = new byte[bufl];
                    for (int i = 0; i < bufl; i++) {
                        block[i] = buf[i];
                    }
                }

                writer.write(ch.doFinal(block));
            }
            byte[] bytes = writer.toByteArray();

            return bytes;

        } catch (Exception e) {
            log.info(e.getMessage());
            e.printStackTrace();
        }
        return null;

    }

    /**
     * 通过私钥加密
     *
     * @param content
     * @param pk
     * @return,加密数据，未进行base64进行加密
     */
    protected static byte[] encryptByPrivateKey(String content, PrivateKey pk) {

        try {
            Cipher ch = Cipher.getInstance(ALGORITHM);
            ch.init(Cipher.ENCRYPT_MODE, pk);
            return ch.doFinal(content.getBytes(CHAR_SET));
        } catch (Exception e) {
            log.info(e.getMessage());
            e.printStackTrace();
        }
        return null;

    }

    /**
     * 通过公钥加密
     *
     * @param content
     * @param pk
     * @return,加密数据，未进行base64进行加密
     */
    protected static byte[] encryptByPublicKey(String content, PublicKey pk) {

        try {
            Cipher ch = Cipher.getInstance(ALGORITHM);
            ch.init(Cipher.ENCRYPT_MODE, pk);
            return ch.doFinal(content.getBytes(CHAR_SET));
        } catch (Exception e) {
            log.info(e.getMessage());
            e.printStackTrace();
        }
        return null;

    }

    /**
     * 解密数据，接收端接收到数据直接解密
     *
     * @param content
     * @return
     */
    public static String decryptByPublicKey(String content) {
        PublicKey pk = getPublicKey(TUIA_PUBLICKEY);
        byte[] data = decryptByPublicKey(content, pk);
        String res = null;
        try {
            res = new String(data, CHAR_SET);
        } catch (UnsupportedEncodingException e) {
            log.info(e.getMessage());
            e.printStackTrace();
        }
        return res;
    }

    /**
     * 解密数据，接收端接收到数据直接解密,解密内容
     *
     * @param content
     * @param key
     * @return
     */
    public static String decryptByPrivateKey(String content, String key) {
        if (null == key || "".equals(key)) {
            return null;
        }
        PrivateKey pk = getPrivateKey(key);
        byte[] data = decryptByPrivateKey(content, pk);
        String res = null;
        try {
            res = new String(data, CHAR_SET);
        } catch (UnsupportedEncodingException e) {
            log.info(e.getMessage());
            e.printStackTrace();
        }
        return res;
    }

    /**
     * 对内容进行加密
     *
     * @param content
     * @return
     */
    public static String encryptByPrivateKey(String content) {
        PrivateKey pk = getPrivateKey(TUIA_PRIVATEKEY);
        byte[] data = encryptByPrivateKey(content, pk);
        String res = null;
        try {
            res = Base64.encodeBase64String(data);
        } catch (Exception e) {
            log.info(e.getMessage());
            e.printStackTrace();
        }
        return res;

    }

    /**
     * 对内容进行加密
     *
     * @param content
     * @param privateKey 公钥
     * @return
     */
    public static String encryptByPublicKey(String content, String privateKey) {
        PublicKey pk = getPublicKey(privateKey);
        byte[] data = encryptByPublicKey(content, pk);
        try {
            return org.lemon.yhj.cipher.Base64.encode(data);
        } catch (Exception e) {
            log.info(e.getMessage());
            return null;
        }

    }

    /**
     * 得到私钥对象
     *
     * @param privateKey 密钥字符串（经过base64编码的秘钥字节）
     * @throws Exception
     */
    public static PrivateKey getPrivateKey(String privateKey) {
        try {
            byte[] keyBytes;

            keyBytes = Base64.decodeBase64(privateKey);

            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);

            KeyFactory keyFactory = KeyFactory.getInstance("RSA");

            PrivateKey privatekey = keyFactory.generatePrivate(keySpec);

            return privatekey;
        } catch (Exception e) {
            log.info(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取公钥对象
     *
     * @param publicKey 密钥字符串（经过base64编码秘钥字节）
     * @throws Exception
     */
    public static PublicKey getPublicKey(String publicKey) {

        try {

            byte[] keyBytes;

//			keyBytes = Base64.decodeBase64(publicKey);
            keyBytes = org.lemon.yhj.cipher.Base64.decode(publicKey);

            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);

            KeyFactory keyFactory = KeyFactory.getInstance("RSA");

            PublicKey publickey = keyFactory.generatePublic(keySpec);

            return publickey;
        } catch (Exception e) {
            log.info(e.getMessage());
            e.printStackTrace();
        }
        return null;

    }

}
