package tech.devinhouse.vehiclesfines.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.devinhouse.vehiclesfines.dto.UsuarioResponse;
import tech.devinhouse.vehiclesfines.dto.UsuarioRequest;
import tech.devinhouse.vehiclesfines.model.Usuario;
import tech.devinhouse.vehiclesfines.service.UsuarioService;

import java.net.URI;
import java.util.List;


@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ModelMapper mapper;

    @GetMapping
    public ResponseEntity<List<UsuarioResponse>> consultar() {
        var usuarios = usuarioService.consultar();
        var resp = usuarios.stream().map(u -> mapper.map(u, UsuarioResponse.class)).toList();
        return ResponseEntity.ok(resp);
    }

    @PostMapping
    public ResponseEntity<UsuarioResponse> inserir(@RequestBody @Valid UsuarioRequest request) {
        var usuario = mapper.map(request, Usuario.class);
        usuario = usuarioService.inserir(usuario);
        var resp = mapper.map(usuario, UsuarioResponse.class);
        return ResponseEntity.created(URI.create(usuario.getId().toString())).body(resp);
    }
}
