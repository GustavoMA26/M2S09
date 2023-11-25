package tech.devinhouse.vehiclesfines.controller;

import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.devinhouse.vehiclesfines.dto.MultaRequest;
import tech.devinhouse.vehiclesfines.dto.MultaResponse;
import tech.devinhouse.vehiclesfines.dto.VeiculoRequest;
import tech.devinhouse.vehiclesfines.dto.VeiculoResponse;
import tech.devinhouse.vehiclesfines.enums.Role;
import tech.devinhouse.vehiclesfines.enums.TipoVeiculo;
import tech.devinhouse.vehiclesfines.model.Multa;
import tech.devinhouse.vehiclesfines.model.Usuario;
import tech.devinhouse.vehiclesfines.model.Veiculo;
import tech.devinhouse.vehiclesfines.service.UsuarioService;
import tech.devinhouse.vehiclesfines.service.VeiculoService;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/veiculos")
public class VeiculosController {

    @Autowired
    private VeiculoService veiculoService;

    @Autowired
    private UsuarioService usuarioService;


    @Autowired
    private ModelMapper mapper;

    @GetMapping
    @RolesAllowed( {"ADMIN","USUARIO"} )
    public ResponseEntity<List<VeiculoResponse>> consultar() {
        var veiculos = veiculoService.consultar();
        var resp = new ArrayList<VeiculoResponse>();
        for(Veiculo veiculo : veiculos) {
            var veiculoDTO = mapper.map(veiculo, VeiculoResponse.class);
            if(veiculo.temMultas()) {
                var multasDTO = veiculo.getMultas().stream()
                        .map(m -> mapper.map(m, MultaResponse.class)).toList();
                veiculoDTO.setMultas(multasDTO);
            }
            resp.add(veiculoDTO);
        }
        return ResponseEntity.ok(resp);
    }

    @GetMapping("/{placa}")
    @RolesAllowed( {"ADMIN","USUARIO"} )
    public ResponseEntity<VeiculoResponse> consultar(@PathVariable("placa") String placa) {
        var veiculo = veiculoService.consultar(placa);
        var resp = mapper.map(veiculo, VeiculoResponse.class);
        if (veiculo.temMultas()) {
            var multasDTO = veiculo.getMultas().stream()
                    .map(m -> mapper.map(m, MultaResponse.class)).toList();
            resp.setMultas(multasDTO);
        }
        return ResponseEntity.ok(resp);
    }

    @PostMapping
    @RolesAllowed( {"ADMIN"} )
    public ResponseEntity<VeiculoResponse> cadastrarVeiculo(@RequestBody @Valid VeiculoRequest request) {
        var veiculo = mapper.map(request, Veiculo.class);
        veiculo = veiculoService.salvar(veiculo);
        var resp = mapper.map(veiculo, VeiculoResponse.class);
        return ResponseEntity.created(URI.create(veiculo.getPlaca())).body(resp);
    }

    @PostMapping("/{placa}/multas")
    @RolesAllowed( {"ADMIN"} )
    public ResponseEntity<MultaResponse> cadastrarMulta(@PathVariable("placa") String placa, @RequestBody @Valid MultaRequest request) {
        var multa = mapper.map(request, Multa.class);
        multa = veiculoService.cadastrarMulta(placa, multa);
        var resp = mapper.map(multa, MultaResponse.class);
        return ResponseEntity.ok(resp);
    }
    @PostMapping("/dados")
    public ResponseEntity<?> carregarDados() {
        var veiculos = veiculoService.consultar();
        if (veiculos.isEmpty()) {
            Veiculo veiculo1 = new Veiculo("ABC-1234", TipoVeiculo.AUTOMOVEL, "Company1", 2012, "preto");
            Veiculo veiculo2 = new Veiculo("BCA-4321", TipoVeiculo.ONIBUS, "Company2", 1950, "prata");
            veiculoService.salvar(veiculo1);
            veiculoService.salvar(veiculo2);
            Multa multa1Veic1 = new Multa("Florianopolis", "Excesso de velocidade", 200F, veiculo1);
            Multa multa2Veic1 = new Multa("Sao Paulo", "Dirigir sem CNH", 200F, veiculo1);
            Multa multa1Veic2 = new Multa("Rio", "Farol apagado", 200F, veiculo2);
            veiculoService.cadastrarMulta(veiculo1.getPlaca(), multa1Veic1);
            veiculoService.cadastrarMulta(veiculo1.getPlaca(), multa2Veic1);
            veiculoService.cadastrarMulta(veiculo2.getPlaca(), multa1Veic2);
        }
        var usuarios = usuarioService.consultar();
        if (usuarios.isEmpty()) {
            usuarioService.inserir(new Usuario("Gustavo Almeida", "gustavo@almeida.com", "123456", Role.ADMIN));
            usuarioService.inserir(new Usuario("Fulano de Beltrano", "fulano@almeida.com", "654321", Role.USUARIO));
        }
        return ResponseEntity.ok().build();

    }
}
