package tech.devinhouse.vehiclesfines.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.devinhouse.vehiclesfines.model.Veiculo;

@Repository
public interface VeiculoRepository extends JpaRepository<Veiculo, String> {
}
