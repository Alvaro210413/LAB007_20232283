package edu.pucp.gtics.lab4_gtics_20221.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "paises")
public class Paises {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idpais;

    private String iso;
    private String nombre;

    public Integer getIdpais() { return idpais; }
    public void setIdpais(Integer idpais) { this.idpais = idpais; }

    public String getIso() { return iso; }
    public void setIso(String iso) { this.iso = iso; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
}
