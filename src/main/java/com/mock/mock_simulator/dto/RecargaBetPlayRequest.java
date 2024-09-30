package com.mock.mock_simulator.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/*
 * @class ValidaFraccionesLoteriasRequest
 * @description Clase que almacena la información del request para la solicitud de las fracciones disponibles
 * @author Carol Velez.
 * @version 1.0 23/08/2024 Documentación y creación de la clase.
 */
@Data
public class RecargaBetPlayRequest {
    private Long id_b2b;
    private String uid_transaccion;
    private Long id_terminal;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy:HH:mm:ss.SSS")
    private Date fecha_transaccion;

    private Long tipo_doc_cliente;
    private Long cc_cliente;
    private long monto;
    private long dane;



}
