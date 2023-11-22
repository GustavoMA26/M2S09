package tech.devinhouse.vehiclesfines.dto;

import lombok.Data;
import tech.devinhouse.vehiclesfines.enums.TipoVeiculo;

import java.util.List;

@Data
public class VeiculoResponse {

    private String placa;

    private TipoVeiculo tipoVeiculo;

    private String nome;

    private Integer anoFabricacao;

    private String cor;

    private List<MultaResponse> multas;
}
