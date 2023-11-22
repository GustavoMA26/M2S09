package tech.devinhouse.vehiclesfines.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.devinhouse.vehiclesfines.model.Multa;
import tech.devinhouse.vehiclesfines.model.Veiculo;
import tech.devinhouse.vehiclesfines.repository.MultaRepository;
import tech.devinhouse.vehiclesfines.repository.VeiculoRepository;

import java.util.List;

@Service
public class VeiculoService {

    @Autowired
    private VeiculoRepository veiculoRepository;

    @Autowired
    private MultaRepository multaRepository;

    @Autowired
    private ModelMapper mapper;

    public List<Veiculo> consultar() {
        return veiculoRepository.findAll();
    }

    public Veiculo consultar(String placa) {
        return veiculoRepository.findById(placa)
                .orElseThrow(() -> new RuntimeException("Placa não encontrada"));
    }

    public Veiculo salvar(Veiculo veiculo) {
        boolean existe = veiculoRepository.existsById(veiculo.getPlaca());
        if (existe)
            throw new RuntimeException("Veículo já está registrado");
        veiculo = veiculoRepository.save(veiculo);
        return veiculo;
    }

    public Multa cadastrarMulta(String placa, Multa multa) {
        Veiculo veiculo = this.consultar(placa);
        multa.setVeiculo(veiculo);
        multa = multaRepository.save(multa);
        return multa;
    }

}
