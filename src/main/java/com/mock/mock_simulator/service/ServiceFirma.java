package com.mock.mock_simulator.service;

import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
public class ServiceFirma implements IserviceFirma{

    @Override
    public String generarFirma(String uid_transaccion, String id_b2b, String apiKey, String token) {


        System.out.println("Entre al generarFirma carolv :"+ uid_transaccion +" " + id_b2b
                +" " + apiKey + " " + token);
        String resultado = String.format("%s~%s~%s~%s",
                apiKey,
                token,
                id_b2b,
                uid_transaccion);
        System.out.println("Detalles de conexión CEM: {}"+ resultado);

        return calcularSHA256(resultado);
    }

    private String calcularSHA256(String datosHeader) {
        System.out.println("Entre al calcularSHA256 carolv");
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(datosHeader.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            String resultadoHash = hexString.toString().toUpperCase();
            System.out.println("Hash SHA-256 calculado CEM: {}"+ resultadoHash);
            return resultadoHash;
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Error al calcular el hash SHA-256" + e);
            return "error";
        }
    }
}
