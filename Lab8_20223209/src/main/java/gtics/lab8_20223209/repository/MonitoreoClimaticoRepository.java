package gtics.lab8_20223209.repository;
import gtics.lab8_20223209.entity.MonitoreoClimatico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MonitoreoClimaticoRepository extends JpaRepository<MonitoreoClimatico, Integer> {
}
