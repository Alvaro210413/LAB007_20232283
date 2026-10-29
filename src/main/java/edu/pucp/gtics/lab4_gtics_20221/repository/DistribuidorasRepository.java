package edu.pucp.gtics.lab4_gtics_20221.repository;

import edu.pucp.gtics.lab4_gtics_20221.entity.Distribuidoras;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DistribuidorasRepository extends JpaRepository<Distribuidoras, Integer> {

    // = SELECT * FROM distribuidoras ORDER BY nombre DESC
    // (para A-Z sería findAllByOrderByNombreAsc)
    List<Distribuidoras> findAllByOrderByNombreDesc();
}
