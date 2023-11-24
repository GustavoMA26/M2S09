package tech.devinhouse.vehiclesfines.dto;


import lombok.Data;
import tech.devinhouse.vehiclesfines.enums.Role;

@Data
public class UsuarioResponse {
    private Integer id;

    private String nome;

    private String email;

    private Role role;
}
