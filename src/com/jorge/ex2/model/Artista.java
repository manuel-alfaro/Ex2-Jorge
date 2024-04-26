package src.com.jorge.ex2.model;

public class Artista extends Participante {

    private int integrantes;
    private Estilo estilo;
    private long cache;

    public Artista(int codigo, String nombre, int integrantes, Estilo estilo, long cache) {
        super(codigo, nombre);
        this.integrantes = integrantes;
        this.estilo = estilo;
        this.cache = cache;
    }

    public int getIntegrantes() {
        return integrantes;
    }

    public void setIntegrantes(int integrantes) {
        this.integrantes = integrantes;
    }

    public Estilo getEstilo() {
        return estilo;
    }

    public void setEstilo(Estilo estilo) {
        this.estilo = estilo;
    }

    public long getCache() {
        return cache;
    }

    public void setCache(long cache) {
        this.cache = cache;
    }

}