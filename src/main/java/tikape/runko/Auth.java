package tikape.runko;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64.Decoder;
import tikape.runko.domain.User;

/**
 * Auth -luokkaa käytetään salasanojen tarkistamiseen, sekä käyttäjän
 * luokituksen todentamiseen. Admin -tunnuksen taso on aina nollaa suurempi
 * luku.
 */
public class Auth {

    /**
     * Tarkistaa, täsmäävätkö salasanat
     *
     * @param password Salasana selkokielellä
     * @param hashedPasswordBase64 Salasana tietokannasta, Base64 -enkoodattu
     * @param saltBase64 Suola tietokannasta, Base64 -enkoodattu
     * @return Täsmäävätkö salasanat vai ei
     * @throws NoSuchAlgorithmException
     */
    public static boolean passwordMatches(String password, String hashedPasswordBase64, String saltBase64) throws NoSuchAlgorithmException {
        Decoder en = java.util.Base64.getDecoder();
        try {
            byte[] passwordBytes = password.getBytes("UTF-8");
            //Muutetaan suola bytearrayksi
            byte[] salt = en.decode(saltBase64);
            //Viedään tietokannan salasana bytearrayksi
            byte[] hashedPassword = en.decode(hashedPasswordBase64);
            //Yhdistetään suola ja salasana
            byte[] passwordWithSalt = Auth.combineTwoByteArrays(salt, passwordBytes);
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            //Salataan käyttäjän syöttämä salasana
            byte[] hashedPasswordWithSalt = digest.digest(passwordWithSalt);
            return Arrays.equals(hashedPasswordWithSalt, hashedPassword);
        } catch (UnsupportedEncodingException ex) {
        }
        return false;
    }

    /**
     * Yhdistää kaksi ByteArraytä
     *
     * @param bytes1 Ensimmäinen ByteArray
     * @param bytes2 Toinen ByteArray
     * @return Yhdistetty ByteArray bytes1+bytes2
     */
    public static byte[] combineTwoByteArrays(byte[] bytes1, byte[] bytes2) {
        byte[] byteArray = new byte[bytes1.length + bytes2.length];
        for (int i = 0; i < byteArray.length; ++i) {
            byteArray[i] = i < bytes1.length ? bytes1[i] : bytes2[i - bytes1.length];
        }
        return byteArray;
    }

    /**
     * Palauttaa, onko käyttäjä pääkäyttäjä vai ei
     *
     * @param u Käyttäjä -olio
     * @return true tai false
     */
    public static boolean isAdmin(User u) {
        return (u != null && u.getUserLevel() > 0);
    }

    /**
     * Palauttaa, onko kirjauduttu sisään vai ei
     *
     * @param u Käyttäjä -olio
     * @return true tai false
     */
    public static boolean isLoggedIn(User u) {
        return (u != null);
    }
}