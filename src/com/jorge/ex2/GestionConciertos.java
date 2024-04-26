package src.com.jorge.ex2;

import src.com.jorge.ex2.model.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class GestionConciertos {

    private List<Concierto> conciertos = new ArrayList<>();
    private List<Participante> participantes = new ArrayList<>();

    private List<Asistente> asistentesPop = new ArrayList<>();
    private List<Asistente> asistentesRock = new ArrayList<>();
    private List<Asistente> asistentesMetal = new ArrayList<>();
    private List<Asistente> asistentesPunk = new ArrayList<>();


    /* LISTADOS */

    public void listarConciertos() {
        for (Concierto c : conciertos) {
            imprimirConcierto(c);
        }
    }

    public void imprimirConcierto(Concierto c) {
        System.out.println(c.getFecha() + ": " + c.getNombre() + " - " + c.getArtista().getCodigo() + " "
                + c.getArtista().getNombre() + " - " + c.getEstilo() + " - "
                + c.getAsistentes().stream().map(Participante::getNombre).toList());
    }

    public void listarAsistentes() {
        for (Participante p : participantes) {
            if (p instanceof Asistente) {
                imprimirAsistente((Asistente) p);
            }
        }
    }

    private void imprimirAsistente(Asistente a) {
        System.out.println(a.getCodigo() + ": " + a.getNombre() + " - "
                + a.getDni() + " - " + a.getFechaNacimiento());
    }

    public void listarArtistas() {
        for (Participante p : participantes) {
            if (p instanceof Artista) {
                imprimirArtista((Artista) p);
            }
        }
    }

    private void imprimirArtista(Artista a) {
        System.out.println(a.getCodigo() + ": " + a.getNombre() + " - "
                + a.getIntegrantes() + " - " + a.getEstilo()
                + " - " + a.getCache());
    }

    public void listarConciertosPorEstilo(Estilo estilo) {
        System.out.println("Conciertos de " + estilo.getNombre() + ":");
        for (Concierto c : conciertos) {
            if (c.getEstilo() == estilo) {
                imprimirConcierto(c);
            }
        }
    }

    public void listarConciertosPorArtista(int codigoArtista) {
        Artista a = getArtistaByCodigo(codigoArtista);
        if (a != null) {
            System.out.println("Conciertos de " + a.getNombre());
            for (Concierto c : conciertos) {
                if (c.getArtista().getCodigo() == codigoArtista) {
                    imprimirConcierto(c);
                }
            }
        } else {
            System.out.println("No se encuentra el artista indicado.");
        }
    }

    public void listarConciertosPorAsistente(int codigoAsistente) {
        for (Concierto c : conciertos) {
            if (c.getAsistentes().stream().anyMatch(x -> x.getCodigo() == codigoAsistente)) {
                System.out.printf("  ");
                imprimirConcierto(c);
            }
        }
    }

    public void cantidadConciertosPorAsistente(int codigoAsistente) {
        int cantidad = 0;
        for (Concierto c : conciertos) {
            if (c.getAsistentes().stream().anyMatch(x -> x.getCodigo() == codigoAsistente)) {
                cantidad++;
            }
        }
        System.out.println(getAsistenteByCodigo(codigoAsistente).getNombre() + " ha asistido a " + cantidad + " conciertos:");
        listarConciertosPorAsistente(codigoAsistente);
    }


    /* ALTAS */

    public void altaAsistente(int codigo, String nombre, String dni, LocalDate fechaNacimiento) {
        Asistente a = new Asistente(codigo, nombre, dni, fechaNacimiento);
        participantes.add(a);
        System.out.printf("Creado asistente: \n  ");
        imprimirAsistente(a);
        ordenarParticipantes();
    }

    public void altaArtista(int codigo, String nombre, int integrantes, Estilo estilo, long cache) {
        Artista a = new Artista(codigo, nombre, integrantes, estilo, cache);
        participantes.add(a);
        System.out.printf("Creado artista: \n  ");
        imprimirArtista(a);
        ordenarParticipantes();
    }

    public void altaConcierto(String nombre, LocalDate fecha, int codigoArtista, Estilo estilo, List<Asistente> asistentes) {
        Artista a = getArtistaByCodigo(codigoArtista);
        if (a != null && estilo == a.getEstilo()) {
            Concierto c = new Concierto(nombre, fecha, a, estilo, asistentes);
            conciertos.add(c);
            System.out.printf("Creado concierto: \n  ");
            imprimirConcierto(c);
            ordenarConciertos();
        } else {
            System.out.println("No se ha podido dar de alta el concierto.");
        }
    }


    /* TRABAJO CON FICHEROS */

    public void guardarDatosEnFicheros() {
        try {
            FileWriter conciertosFile = new FileWriter("conciertos.csv");
            FileWriter asistentesFile = new FileWriter("asistentes.csv");

            for (Concierto c : conciertos) {
                conciertosFile.write(c.getNombre() + ";"
                + c.getFecha() + ";"
                + c.getArtista().getCodigo() + ";"
                + c.getEstilo().nombre + "\n");
            }

            for (Participante p : participantes) {
                if (p instanceof Asistente) {
                    asistentesFile.write(p.getCodigo() + ";"
                    + p.getNombre() + ";"
                    + ((Asistente) p).getDni() + ";"
                    + ((Asistente) p).getFechaNacimiento() + "\n");
                }
            }

            conciertosFile.close();
            asistentesFile.close();
            System.out.println("¡Ficheros guardados!");
        } catch (IOException ioe) {
            System.out.println("Error guardando datos.");
        }

    }

    public void cargarDatosDeFicheros() {
        List<Concierto> conciertosCargados = new ArrayList<>();
        List<Asistente> asistentesCargados = new ArrayList<>();

        String line;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            BufferedReader brC = new BufferedReader(new FileReader("conciertos.csv"));
            while ((line = brC.readLine()) != null) {
                String[] valores = line.split(";");
                conciertosCargados.add(new Concierto(
                        valores[0],
                        LocalDate.parse(valores[1], formatter),
                        getArtistaByCodigo(Integer.parseInt(valores[2])),
                        Estilo.valueOf(valores[3].toUpperCase()),
                        new ArrayList<>()));
            }

            BufferedReader brA = new BufferedReader(new FileReader("asistentes.csv"));
            while ((line = brA.readLine()) != null) {
                String[] valores = line.split(";");
                asistentesCargados.add(new Asistente(
                        Integer.parseInt(valores[0]),
                        valores[1],
                        valores[2],
                        LocalDate.parse(valores[3], formatter)
                ));
            }

            brC.close();
            brA.close();

            System.out.println("Datos de conciertos cargados:");
            for (Concierto c : conciertosCargados) {
                System.out.printf("  ");
                imprimirConcierto(c);
            }
            System.out.println("");
            for (Asistente a : asistentesCargados) {
                System.out.printf("  ");
                imprimirAsistente(a);
            }

        } catch (IOException ioe) {
            System.out.println("Error cargando datos.");
        }
    }


    /* OTROS MÉTODOS */

    private void ordenarConciertos() {
        conciertos.sort(Comparator.comparing(Concierto::getFecha));
    }

    private void ordenarParticipantes() {
        participantes.sort(Comparator.comparing(Participante::getCodigo));
    }

    private Artista getArtistaByCodigo(int codigo) {
        return (Artista) participantes.stream().filter(x -> x.getCodigo() == codigo).findFirst().orElse(null);
    }

    private Asistente getAsistenteByCodigo(int codigo) {
        return (Asistente) participantes.stream().filter(x -> x.getCodigo() == codigo).findFirst().orElse(null);
    }

    private Concierto getConciertoByNombre(String nombre) {
        return conciertos.stream().filter(c -> c.getNombre().equals(nombre)).findFirst().orElse(null);
    }

    public List<Asistente> getAsistentes(Estilo estilo) {
        switch (estilo) {
            case POP -> { return asistentesPop; }
            case ROCK -> { return asistentesRock; }
            case METAL -> { return asistentesMetal; }
            case PUNK -> { return asistentesPunk; }
            default -> { return null; }
        }
    }

    public void asignarAsistenteAConcierto(int codigoAsistente, String nombreConcierto) {
        Concierto c = getConciertoByNombre(nombreConcierto);
        Asistente a = getAsistenteByCodigo(codigoAsistente);
        if (c != null && a != null) {
            c.getAsistentes().add(a);
            System.out.println("Asignado " + a.getNombre() + " al concierto " + c.getNombre() + ".");
        } else {
            System.out.println("No se ha podido encontrar el concierto o el asistente.");
        }
    }



    public void cargarDatosDeEjemplo() {
        Asistente as1 = new Asistente(1, "Asistente 1", "11111111A", LocalDate.of(1990, 1, 1));
        Asistente as2 = new Asistente(5, "Asistente 5", "55555555G", LocalDate.of(1991, 8, 16));
        Asistente as3 = new Asistente(3, "Asistente 3", "33333333F", LocalDate.of(1992, 5, 31));
        Asistente as4 = new Asistente(2, "Asistente 2", "22222222T", LocalDate.of(1993, 4, 21));
        Asistente as5 = new Asistente(4, "Asistente 4", "44444444P", LocalDate.of(1994, 2, 12));
        Asistente as6 = new Asistente(9, "Asistente 9", "99999999R", LocalDate.of(1995, 11, 23));
        Asistente as7 = new Asistente(10, "Asistente 10", "10101010S", LocalDate.of(1996, 9, 30));
        Asistente as8 = new Asistente(7, "Asistente 7", "77777777Q", LocalDate.of(1997, 6, 27));
        Asistente as9 = new Asistente(8, "Asistente 8", "88888888L", LocalDate.of(1998, 3, 19));
        Asistente as10 = new Asistente(6, "Asistente 6", "66666666V", LocalDate.of(1999, 12, 24));

        asistentesPop.add(as1);
        asistentesPop.add(as2);
        asistentesPop.add(as3);
        asistentesRock.add(as4);
        asistentesRock.add(as5);
        asistentesRock.add(as6);
        asistentesMetal.add(as7);
        asistentesMetal.add(as8);
        asistentesPunk.add(as9);
        asistentesPunk.add(as10);

        Artista ar1 = new Artista(110, "Vetusta Morla", 5, Estilo.POP, 40000);
        Artista ar2 = new Artista(109, "Scorpions", 5, Estilo.ROCK, 54000);
        Artista ar3 = new Artista(108, "Warcry", 5, Estilo.METAL, 22000);
        Artista ar4 = new Artista(107, "El Último Ke Zierre", 5, Estilo.PUNK, 16000);
        Artista ar5 = new Artista(106, "Viva Suecia", 5, Estilo.POP, 12000);
        Artista ar6 = new Artista(105, "Marea", 4, Estilo.ROCK, 44000);
        Artista ar7 = new Artista(104, "Avalanch", 5, Estilo.METAL, 23500);
        Artista ar8 = new Artista(103, "Saratoga", 4, Estilo.METAL, 9000);
        Artista ar9 = new Artista(102, "La M.O.D.A.", 6, Estilo.POP, 40000);
        Artista ar10 = new Artista(101, "Shinova", 5, Estilo.POP, 26000);

        Concierto c1 = new Concierto("Planeta Sound", LocalDate.of(2024, 7, 13), ar10, Estilo.POP, asistentesPop);
        Concierto c2 = new Concierto("Tsunami Xixón", LocalDate.of(2024, 7, 21), ar2, Estilo.ROCK, asistentesRock);
        Concierto c3 = new Concierto("Luarca Metal Days", LocalDate.of(2024, 7, 12), ar7, Estilo.METAL, asistentesMetal);
        Concierto c4 = new Concierto("Nits de Vivers", LocalDate.of(2024, 8, 1), ar3, Estilo.METAL, asistentesMetal);
        Concierto c5 = new Concierto("Páramo Rock", LocalDate.of(2024, 8, 3), ar4, Estilo.PUNK, asistentesPunk);
        Concierto c6 = new Concierto("Sevilla Fest", LocalDate.of(2024, 6, 26), ar9, Estilo.POP, asistentesPop);

        participantes.addAll(List.of(as1, as2, as3, as4, as5, as6, as7, as8, as9, as10));
        participantes.addAll(List.of(ar1, ar2, ar3, ar4, ar5, ar6, ar7, ar8, ar9, ar10));
        ordenarParticipantes();
        conciertos.addAll(List.of(c1, c2, c3, c4, c5, c6));
        ordenarConciertos();
    }

}