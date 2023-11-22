package tech.devinhouse.vehiclesfines.controller;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.devinhouse.vehiclesfines.dto.MultaRequest;
import tech.devinhouse.vehiclesfines.dto.MultaResponse;
import tech.devinhouse.vehiclesfines.dto.VeiculoRequest;
import tech.devinhouse.vehiclesfines.dto.VeiculoResponse;
import tech.devinhouse.vehiclesfines.model.Multa;
import tech.devinhouse.vehiclesfines.model.Veiculo;
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
    private ModelMapper mapper;

    @GetMapping
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
    public ResponseEntity<VeiculoResponse> cadastrarVeiculo(@RequestBody @Valid VeiculoRequest request) {
        var veiculo = mapper.map(request, Veiculo.class);
        veiculo = veiculoService.salvar(veiculo);
        var resp = mapper.map(veiculo, VeiculoResponse.class);
        return ResponseEntity.created(URI.create(veiculo.getPlaca())).body(resp);
    }

    @PostMapping("/{placa}/multas")
    public ResponseEntity<MultaResponse> cadastrarMulta(@PathVariable("placa") String placa, @RequestBody @Valid MultaRequest request) {
        var multa = mapper.map(request, Multa.class);
        multa = veiculoService.cadastrarMulta(placa, multa);
        var resp = mapper.map(multa, MultaResponse.class);
        return ResponseEntity.ok(resp);
    }
}
