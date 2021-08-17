/*
 *  Creado por Kevin Suaza Atehortua
 *  KSProyectos 2019
 *  Todos los derechos reservados.
 */
package com.ksproyectos.parking4all.Services;

import com.ksproyectos.parking4all.DAO.Mysql.EntityManagerSingleton;
import com.ksproyectos.parking4all.Services.Utils.Hardware4Mac;
import com.ksproyectos.parking4all.Services.Utils.Hardware4Win;
import com.ksproyectos.parking4all.DAO.Mysql.Entities.Configuraciones;
import java.math.BigInteger;
import java.net.InetAddress;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;
import javax.crypto.SecretKeyFactory; 
import javax.crypto.spec.PBEKeySpec;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.apache.log4j.Logger;

/**
 *
 * @author kevin
 */
public class LicenciaService {
    
    private final static Logger LOGGER = Logger.getLogger(LicenciaService.class.getName());
 
    private static EntityManager entityManager = EntityManagerSingleton.getInstance().getEntityManager();
    
    public static boolean comprobarLicencia() {
        InetAddress ip;

        try {

            String snMac = null;
            String snWindows = null; 
            String licenciaMac = null;
            String licenciaWindows = null;
            try{
                snMac = Hardware4Mac.getSerialNumber();
                licenciaMac = generateLicenceKey(snMac);
            }catch(Exception e){
                System.err.println(e);
                
            }
            
            try{
               snWindows = Hardware4Win.getSerialNumber();
               licenciaWindows = generateLicenceKey(snWindows);
            }catch(Exception e){
                System.err.println(e);
            }
            
                        
            System.out.println("Windows " + snWindows);
            System.out.println("Mac " + snMac);
            
            Query query;
            query = entityManager.createQuery("SELECT c FROM Configuraciones c WHERE c.nombre LIKE 'Licencia%'");
            
            List<Configuraciones> licencias = query.getResultList();
            
            for(Configuraciones licencia : licencias){
                if (licencia.getValor().equals(licenciaMac) || licencia.getValor().equals(licenciaWindows)) {
                    return true;
                }
            }
            

            

        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }

    public static boolean comprobarLicenciaSoporte() {
        InetAddress ip;

        try {

            String snMac = null;
            String snWindows = null;
            String licenciaMac = null;
            String licenciaWindows = null;
            try{
                snMac = Hardware4Mac.getSerialNumber();
                licenciaMac = generateLicenceKey(snMac);
            }catch(Exception e){
                System.err.println(e);

            }

            try{
                snWindows = Hardware4Win.getSerialNumber();
                licenciaWindows = generateLicenceKey( "soporte-1");
            }catch(Exception e){
                System.err.println(e);
            }


            System.out.println("Windows " + snWindows);
            System.out.println("Mac " + snMac);

            Query query;
            query = entityManager.createQuery("SELECT c FROM Configuraciones c WHERE c.nombre LIKE 'Licencia-Soporte%'");

            List<Configuraciones> licencias = query.getResultList();

            for(Configuraciones licencia : licencias){
                if (licencia.getValor().equals(licenciaMac) || licencia.getValor().equals(licenciaWindows)) {
                    return true;
                }
            }




        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
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
}
