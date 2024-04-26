package src.com.jorge.ex2.model;

import java.time.LocalDate;
import java.util.List;

public class Concierto {

    private String nombre;
    private LocalDate fecha;
    private Artista artista;
    private Estilo estilo;
    private List<Asistente> asistentes;

    public Concierto(String nombre, LocalDate fecha, Artista artista, Estilo estilo, List<Asistente> asistentes) {
        this.nombre = nombre;
        this.fecha = fecha;
        this.artista = artista;
        this.estilo = estilo;
        this.asistentes = asistentes;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Artista getArtista() {
        return artista;
    }

    public void setArtista(Artista artista) {
        this.artista = artista;
    }

    public Estilo getEstilo() {
        return estilo;
    }

    public void setEstilo(Estilo estilo) {
        this.estilo = estilo;
    }

    public List<Asistente> getAsistentes() {
        return asistentes;
    }

    public void setAsistentes(List<Asistente> asistentes) {
        this.asistentes = asistentes;
    }

}