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


    private String statusCode;
    private String statusDesc;
    private long tipoDocCliente;
    private long ccCliente;
    private boolean activoDepositos;
    private boolean activoRetiros;

}
