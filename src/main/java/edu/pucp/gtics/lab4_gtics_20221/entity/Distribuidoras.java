package edu.pucp.gtics.lab4_gtics_20221.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.URL;

@Entity
@Table(name = "distribuidoras")
public class Distribuidoras {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer iddistribuidora;

    @Size(min = 3, max = 50, message = "Debe contener entre 3 y 50 caracteres")
    private String nombre;

    @Size(min = 3, max = 198, message = "Debe contener entre 3 y 198 caracteres")
    private String descripcion;

    // @URL revisa que tenga forma de enlace (ej: https://www.nintendo.com)
    @Size(min = 3, max = 198, message = "Debe contener entre 3 y 198 caracteres")
    @URL(message = "Debe ser un URL válido (ej: https://www.ejemplo.com)")
    private String web;

    @NotNull(message = "Fundación no puede estar vacío")
    @Min(value = 1800, message = "Debe ser mayor que o igual a 1800")
    @Max(value = 2100, message = "Debe ser menor que o igual a 2100")
    private Integer fundacion;

    // La sede es un país (columna idsede -> tabla paises)
    @NotNull(message = "Sede no puede estar vacío")
    @ManyToOne
    @JoinColumn(name = "idsede")
    private Paises sede;

    public Integer getIddistribuidora() { return iddistribuidora; }
    public void setIddistribuidora(Integer iddistribuidora) { this.iddistribuidora = iddistribuidora; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getWeb() { return web; }
    public void setWeb(String web) { this.web = web; }

    public Integer getFundacion() { return fundacion; }
    public void setFundacion(Integer fundacion) { this.fundacion = fundacion; }

    public Paises getSede() { return sede; }
    public void setSede(Paises sede) { this.sede = sede; }
}
