package edu.pucp.gtics.lab4_gtics_20221.repository;

import edu.pucp.gtics.lab4_gtics_20221.entity.Juegos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

// JpaRepository<Entidad, TipoDelId> ya trae hechos: findAll, findById, save, deleteById, existsById...
@Repository
public interface JuegosRepository extends JpaRepository<Juegos, Integer> {

    // Query method: Spring arma el SQL solo leyendo el nombre del método.
    // = SELECT * FROM juegos ORDER BY precio DESC
    // (si quisieras ascendente: findAllByOrderByPrecioAsc; el nombre "Precio" debe ser igual al atributo de la entidad)
    List<Juegos> findAllByOrderByPrecioDesc();

    // Ejemplos de query methods (Spring arma el SQL solo con el nombre)
    // List<Juegos> findAllByOrderByPrecioAsc();            -> precio de menor a mayor
    // List<Juegos> findAllByOrderByNombreAsc();            -> nombre de la A a la Z
    // List<Juegos> findAllByOrderByNombreDesc();           -> nombre de la Z a la A
    // List<Juegos> findByNombre(String nombre);            -> juegos con ese nombre exacto
    // List<Juegos> findByNombreContaining(String texto);   -> nombre que contiene ese texto
    // List<Juegos> findByPrecioGreaterThan(Double precio); -> precio mayor que ese valor
    // List<Juegos> findByPrecioBetween(Double min, Double max); -> precio entre min y max
    // List<Juegos> findByGenero_Idgenero(Integer id);      -> juegos de un género
}
