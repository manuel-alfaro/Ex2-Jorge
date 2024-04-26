package src.com.jorge.ex2.model;

public enum Estilo {

    ROCK("rock"),
    POP("pop"),
    METAL("metal"),
    PUNK("punk");

    public final String nombre;

    Estilo(String name) {
        this.nombre = name;
    }

    public String getNombre() {
        return nombre;
    }

}