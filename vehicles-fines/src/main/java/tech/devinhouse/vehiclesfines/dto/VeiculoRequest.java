package tech.devinhouse.vehiclesfines.dto;
import lombok.Data;
import tech.devinhouse.vehiclesfines.enums.TipoVeiculo;

@Data
public class VeiculoRequest {

    private String placa;

    private TipoVeiculo tipo;

    private String nome;

    private Integer anoFabricacao;

    private String cor;

}