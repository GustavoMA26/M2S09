package tech.devinhouse.vehiclesfines.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.devinhouse.vehiclesfines.model.Usuario;
import tech.devinhouse.vehiclesfines.repository.UsuarioRepository;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    public UsuarioRepository usuarioRepository;

    public Usuario inserir(Usuario usuario) {
        if (usuarioRepository.existsByEmail(usuario.getEmail()))
            throw new RuntimeException("Usuario já existente");
        //TODO: inserir a dependência do Security
        return usuarioRepository.save(usuario);
    }

    public List<Usuario> consultar() {
        return usuarioRepository.findAll();
    }


}
