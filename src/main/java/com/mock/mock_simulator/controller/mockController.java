package com.mock.mock_simulator.controller;

import com.mock.mock_simulator.dto.ErrorResponse;
import com.mock.mock_simulator.dto.RecargaBetPlayRequest;
import com.mock.mock_simulator.dto.ValidaIdentificacionBetPlayRequest;
import com.mock.mock_simulator.dto.ValidaIdentificacionBetPlayResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;

@RestController
@RequestMapping("/alianzasb2b/v1")
public class mockController {

    @PostMapping("/consultaplayer")
    public ResponseEntity<?> ValidarCedula(
            @RequestBody ValidaIdentificacionBetPlayRequest validaIdentificacionBetPlayRequest,
            @RequestHeader String apiKey,
            @RequestHeader String token,
            @RequestHeader String firmaEnviada

    ) {
        String firmaGenerada = generarFirma(validaIdentificacionBetPlayRequest,apiKey,token);
        if(firmaGenerada.equals(firmaEnviada)){

            if (validaIdentificacionBetPlayRequest.getId_b2b() == null) {
                ErrorResponse errorResponse = new ErrorResponse();
                errorResponse.setStatus_code("101");
                errorResponse.setStatus_desc(" Campo ID_B2B vacío o inexistente");
                return ResponseEntity.badRequest().body(errorResponse);
            }

            if (!validaIdentificacionBetPlayRequest.getCc_cliente().equals(79484192L)) {
                ErrorResponse errorResponse = new ErrorResponse();
                errorResponse.setStatus_code("107");
                errorResponse.setStatus_desc("Jugador no registrado");
                return ResponseEntity.badRequest().body(errorResponse);
            }

            Calendar calInicio = Calendar.getInstance();
            calInicio.set(Calendar.HOUR_OF_DAY, 8);
            calInicio.set(Calendar.MINUTE, 0);
            calInicio.set(Calendar.SECOND, 0);

            Calendar calFin = Calendar.getInstance();
            calFin.set(Calendar.HOUR_OF_DAY, 23);
            calFin.set(Calendar.MINUTE, 0);
            calFin.set(Calendar.SECOND, 0);

            if (validaIdentificacionBetPlayRequest.getFecha_transaccion().before(calInicio.getTime()) ||
                    validaIdentificacionBetPlayRequest.getFecha_transaccion().after(calFin.getTime())) {
                ErrorResponse errorResponse = new ErrorResponse();
                errorResponse.setStatus_code("110");
                errorResponse.setStatus_desc(" fechaHoraPeticion por fuera del rango permitido");
                return ResponseEntity.badRequest().body(errorResponse);
            }
            ValidaIdentificacionBetPlayResponse successResponse = new ValidaIdentificacionBetPlayResponse();
            successResponse.setStatusCode("200");
            successResponse.setStatusDesc("Consulta exitosa");
            successResponse.setTipoDocCliente(validaIdentificacionBetPlayRequest.getTipo_doc_cliente());
            successResponse.setCcCliente(validaIdentificacionBetPlayRequest.getCc_cliente());
            successResponse.setActivoDepositos(true);
            successResponse.setActivoRetiros(false);

            return ResponseEntity.ok(successResponse);
        }else{
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.setStatus_code("500");
            errorResponse.setStatus_desc("Firma inválida");
            return ResponseEntity.status(500).body(errorResponse);
        }

    }

    @PostMapping("/depositos")
    public ResponseEntity<?> RecargaBetPlay(
            @RequestBody RecargaBetPlayRequest recargaBetPlayRequest,
            @RequestHeader String apiKey,
            @RequestHeader String token,
            @RequestHeader String firmaEnviada

    ) {
        String firmaGenerada = generarFirma(recargaBetPlayRequest,apiKey,token);
        if(firmaGenerada.equals(firmaEnviada)){

            if (validaIdentificacionBetPlayRequest.getId_b2b() == null) {
                ErrorResponse errorResponse = new ErrorResponse();
                errorResponse.setStatus_code("101");
                errorResponse.setStatus_desc(" Campo ID_B2B vacío o inexistente");
                return ResponseEntity.badRequest().body(errorResponse);
            }

            if (!validaIdentificacionBetPlayRequest.getCc_cliente().equals(79484192L)) {
                ErrorResponse errorResponse = new ErrorResponse();
                errorResponse.setStatus_code("107");
                errorResponse.setStatus_desc("Jugador no registrado");
                return ResponseEntity.badRequest().body(errorResponse);
            }

            Calendar calInicio = Calendar.getInstance();
            calInicio.set(Calendar.HOUR_OF_DAY, 8);
            calInicio.set(Calendar.MINUTE, 0);
            calInicio.set(Calendar.SECOND, 0);

            Calendar calFin = Calendar.getInstance();
            calFin.set(Calendar.HOUR_OF_DAY, 23);
            calFin.set(Calendar.MINUTE, 0);
            calFin.set(Calendar.SECOND, 0);
            System.out.println("carolv " + calInicio);

            if (validaIdentificacionBetPlayRequest.getFecha_transaccion().before(calInicio.getTime()) ||
                    validaIdentificacionBetPlayRequest.getFecha_transaccion().after(calFin.getTime())) {
                System.out.println("Entre al else3 inciial carolv");
                ErrorResponse errorResponse = new ErrorResponse();
                errorResponse.setStatus_code("110");
                errorResponse.setStatus_desc(" fechaHoraPeticion por fuera del rango permitido");
                return ResponseEntity.badRequest().body(errorResponse);
            }
            System.out.println("Entre al exitoso inciial carolv");
            ValidaIdentificacionBetPlayResponse successResponse = new ValidaIdentificacionBetPlayResponse();
            successResponse.setStatusCode("200");
            successResponse.setStatusDesc("Consulta exitosa");
            successResponse.setTipoDocCliente(validaIdentificacionBetPlayRequest.getTipo_doc_cliente());
            successResponse.setCcCliente(validaIdentificacionBetPlayRequest.getCc_cliente());
            successResponse.setActivoDepositos(true);
            successResponse.setActivoRetiros(false);

            return ResponseEntity.ok(successResponse);
        }else{

            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.setStatus_code("500");
            errorResponse.setStatus_desc("Firma inválida");

            return ResponseEntity.status(500).body(errorResponse);
        }

    }




    public String generarFirma(ValidaIdentificacionBetPlayRequest validaIdentificacionBetPlayRequest,
                               String apiKey,
                               String token) {
        String uid_transaccion;
        System.out.println("Entre al generarFirma carolv");
            uid_transaccion = validaIdentificacionBetPlayRequest.getUid_transaccion();
            String resultado = String.format("%s~%s~%s~%s",
                    apiKey,
                    token,
                    validaIdentificacionBetPlayRequest.getId_b2b(),
                    uid_transaccion);
            System.out.println("Detalles de conexión CEM: {}"+ resultado);
            String firma = calcularSHA256(resultado);

            return firma;

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
