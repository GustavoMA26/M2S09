package tech.devinhouse.vehiclesfines.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "MULTAS")
public class Multa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String local;

    private String motivo;

    private Float valor;

    @ManyToOne
    @JoinColumn(name = "PLACA", referencedColumnName = "PLACA")
    private Veiculo veiculo;

    public Multa(String local, String motivo, Float valor, Veiculo veiculo) {
        this.local = local;
        this.motivo = motivo;
        this.valor = valor;
        this.veiculo = veiculo;
    }
}
