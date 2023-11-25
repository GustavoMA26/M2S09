package tech.devinhouse.vehiclesfines.dto;

import lombok.Data;

@Data
public class AutenticacaoRequest {

    private String email;

    private String senha;
}
