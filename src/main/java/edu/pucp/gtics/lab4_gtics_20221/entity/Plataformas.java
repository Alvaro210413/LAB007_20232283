package edu.pucp.gtics.lab4_gtics_20221.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "plataformas")
public class Plataformas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idplataforma;

    private String nombre;
    private String descripcion;

    public Integer getIdplataforma() { return idplataforma; }
    public void setIdplataforma(Integer idplataforma) { this.idplataforma = idplataforma; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
}
