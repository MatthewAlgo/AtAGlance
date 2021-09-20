package app.matthewsgalaxy.ataglance.AdditionalClasses;


import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class EncryptUtils {

    private Cipher MyChiper;
    private PublicKey PubKey;
    private KeyPair MyKeyPair;

    public EncryptUtils(){
        try {
            KeyPairGenerator kpairGen = KeyPairGenerator.getInstance("DSA"); // Generates NoSuchAlgorithmException
            kpairGen.initialize(2048);
            MyKeyPair = kpairGen.generateKeyPair();
            PubKey = MyKeyPair.getPublic();

            MyChiper= Cipher.getInstance("RSA/ECB/PKCS1Padding"); // Generates NoSuchPaddingException
            // MyChiper.init(Cipher.ENCRYPT_MODE, publicKey); // Generates InvalidKeyException

        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            e.printStackTrace();
        }

    }
    public byte[] EncryptByteArray(byte[] arr) throws BadPaddingException, IllegalBlockSizeException, InvalidKeyException {
        MyChiper.init(Cipher.ENCRYPT_MODE, PubKey); // Generates InvalidKeyException
        MyChiper.update(arr);
        byte[] NewArr = MyChiper.doFinal();
        return NewArr;
    }
    public byte[] DecryptByteArray(byte[] arr) throws BadPaddingException, IllegalBlockSizeException, InvalidKeyException {
        MyChiper.init(Cipher.DECRYPT_MODE, MyKeyPair.getPrivate()); // Generates InvalidKeyException
        MyChiper.update(arr);
        byte[] NewArr = MyChiper.doFinal();
        return NewArr;
    }

}