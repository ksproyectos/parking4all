package com.ksproyectos.parking4all.Services;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.ksproyectos.parking4all.DAO.SQL.AppDatabaseSingleton;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class LicenciaService {

    public static boolean comprobarLicencia(Context context) {

        try {

            String sn = getID(context);
            String licencia;
            String licenciaRegistrada = AppDatabaseSingleton.getInstance().getAppDatabase().customQueriesRoomDAO().getLicencia();

            licencia = generateLicenceKey("42c9e26fdecea63d");

            String licenciaAcomparar = licencia.substring(0,10);
            if(licenciaRegistrada.equals(licenciaAcomparar)){
                return true;
            }


        } catch (Exception e) {
            System.out.println(e);
        }
        return true;
    }

    private static String generateLicenceKey(String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        int iterations = 102;
        char[] chars = password.toCharArray();
        byte[] salt = "ksprojects".getBytes();

        PBEKeySpec spec = new PBEKeySpec(chars, salt, iterations, 64 * 8);
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] hash = skf.generateSecret(spec).getEncoded();
        return toHex(hash);
    }

    private static String toHex(byte[] array) throws NoSuchAlgorithmException {
        BigInteger bi = new BigInteger(1, array);
        String hex = bi.toString(16);
        int paddingLength = (array.length * 2) - hex.length();
        if (paddingLength > 0) {
            return String.format("%0" + paddingLength + "d", 0) + hex;
        } else {
            return hex;
        }
    }

    public static String getUniqueIMEIId(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {

            return "";
        }
        String imei = telephonyManager.getDeviceId();
        if (imei != null && !imei.isEmpty()) {
            return imei;
        } else {
            return android.os.Build.SERIAL;
        }
    }

    public static String getID(Context context){
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }
}
