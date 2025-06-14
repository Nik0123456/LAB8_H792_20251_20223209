package gtics.lab8_20223209.entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Entity
@Table(name = "monitoreo_climatico")
@Getter
@Setter
public class MonitoreoClimatico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idMonitoreo", nullable = false)
    private Integer idMonitoreo;

    @Column(name = "ciudad", nullable = false)
    private String ciudad;

    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;

    @Column(name = "temperaturaPromedio", nullable = false)
    private Double temperaturaPromedio;

    @Column(name = "condicionClimaticaEsperada", nullable = false)
    private String condicionClimaticaEsperada;

    @Column(name = "temperaturaMaxima", nullable = false)
    private Double temperaturaMaxima;

    @Column(name = "temperaturaMinima", nullable = false)
    private Double temperaturaMinima;

}
