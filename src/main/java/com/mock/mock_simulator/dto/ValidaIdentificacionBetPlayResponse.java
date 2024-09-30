package com.mock.mock_simulator.dto;

import lombok.Data;


/*
 * @class ValidaFraccionesLoteriasResponse
 * @description Clase que almacena la información de la respuesta de las fracciones disponibles.
 * @author Carol Velez.
 * @version 1.0 23/08/2024 Documentación y creación de la clase.
 */
@Data
public class ValidaIdentificacionBetPlayResponse {


    private String status_code;
    private String status_desc;
    private long tipo_doc_cliente;
    private long cc_cliente;
    private boolean activo_depositos;
    private boolean activo_retiros;

}
