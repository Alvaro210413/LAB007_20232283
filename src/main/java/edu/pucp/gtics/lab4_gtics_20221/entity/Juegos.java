package edu.pucp.gtics.lab4_gtics_20221.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "juegos")
public class Juegos {

    // IDENTITY = el id lo genera MySQL (AUTO_INCREMENT).
    // Si el id es null -> save() hace INSERT; si tiene valor -> hace UPDATE.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idjuego;

    // Los "message" son los textos que se ven en rojo debajo de cada campo del formulario
    @Size(min = 3, max = 45, message = "Debe contener entre 3 y 45 caracteres")
    private String nombre;

    @Size(min = 3, max = 400, message = "Debe contener entre 3 y 400 caracteres")
    private String descripcion;

    // Si se escriben letras, el mensaje "Debe ser un número" sale de messages.properties
    @NotNull(message = "Precio no puede estar vacío")
    @Min(value = 10, message = "Valor mínimo 10")
    @Max(value = 500, message = "Valor máximo 500")
    private Double precio;

    private String image;

    // Relaciones: en el <select> se manda solo el id (ej: genero=2) y Spring Data
    // busca solo el objeto Generos con ese id. Si eligen "- Seleccione -" llega null
    // y salta el @NotNull.
    @NotNull(message = "Género no puede estar vacío")
    @ManyToOne
    @JoinColumn(name = "idgenero")
    private Generos genero;

    @NotNull(message = "Plataforma no puede estar vacío")
    @ManyToOne
    @JoinColumn(name = "idplataforma")
    private Plataformas plataforma;

    @NotNull(message = "Distribuidora no puede estar vacío")
    @ManyToOne
    @JoinColumn(name = "iddistribuidora")
    private Distribuidoras distribuidora;

    // (ideditora no se mapea porque no hay entidad Editoras en este lab; en la BD es NULL)

    public Integer getIdjuego() { return idjuego; }
    public void setIdjuego(Integer idjuego) { this.idjuego = idjuego; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public Double getPrecio() { return precio; }
    public void setPrecio(Double precio) { this.precio = precio; }

    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }

    public Generos getGenero() { return genero; }
    public void setGenero(Generos genero) { this.genero = genero; }

    public Plataformas getPlataforma() { return plataforma; }
    public void setPlataforma(Plataformas plataforma) { this.plataforma = plataforma; }

    public Distribuidoras getDistribuidora() { return distribuidora; }
    public void setDistribuidora(Distribuidoras distribuidora) { this.distribuidora = distribuidora; }
}
