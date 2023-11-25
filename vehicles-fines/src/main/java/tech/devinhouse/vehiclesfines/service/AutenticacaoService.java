package tech.devinhouse.vehiclesfines.service;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import tech.devinhouse.vehiclesfines.enums.Role;
import tech.devinhouse.vehiclesfines.model.Usuario;


@Service
public class AutenticacaoService {


    @Autowired
    private TokenService tokenService;

    public String autenticar(String email, String password) {
        var usuario = new Usuario("Ciclano", "ciclano@almeida.com", "456789", Role.ADMIN);
        return tokenService.gerarToken(usuario);
    }

}