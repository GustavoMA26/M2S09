package tech.devinhouse.vehiclesfines.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tech.devinhouse.vehiclesfines.model.Usuario;
import tech.devinhouse.vehiclesfines.repository.UsuarioRepository;

import java.util.List;

@Service
public class UsuarioService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Usuario inserir(Usuario usuario) {
        if (usuarioRepository.existsByEmail(usuario.getEmail()))
            throw new RuntimeException("Usuario já existente");
        String senhaCriptograda = passwordEncoder.encode(usuario.getEmail());
        usuario.setSenha(senhaCriptograda);
        return usuarioRepository.save(usuario);
    }

    public List<Usuario> consultar() {
        return usuarioRepository.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
    }
}
