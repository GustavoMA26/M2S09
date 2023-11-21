package tech.devinhouse.vehiclesfines.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.devinhouse.vehiclesfines.model.Multa;

@Repository
public interface MultaRepository extends JpaRepository<Multa, Integer> {
}
