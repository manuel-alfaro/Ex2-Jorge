package src.com.jorge.ex2;

import src.com.jorge.ex2.model.Estilo;

import java.time.LocalDate;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        // 1. Crear un objeto de GestionConciertos
        GestionConciertos gestion = new GestionConciertos();


        // Cargamos datos de ejemplo
        gestion.cargarDatosDeEjemplo();


        // 2. Dar de alta 3 artistas de los cuales algunos coincidan en estilo de música,
        // y 3 asistentes
        gestion.altaArtista(201, "Sidecars", 6, Estilo.POP, 30000);
        gestion.altaArtista(202, "Los Zigarros", 4, Estilo.POP, 35000);
        gestion.altaArtista(203, "Mägo de Oz", 8, Estilo.METAL, 24000);

        gestion.altaAsistente(301, "Asistente 301", "11223344T",
                LocalDate.of(1990, 5, 5));
        gestion.altaAsistente(302, "Asistente 302", "99887766H",
                LocalDate.of(1993, 8, 19));


        // 3. Listar solo los artistas, y solo los asistentes
        emptyLine();
        System.out.println("  ++ Listar solo artistas");
        gestion.listarArtistas();

        emptyLine();
        System.out.println("  ++ Listar solo asistentes");
        gestion.listarAsistentes();


        // 4. Dar de alta 4 conciertos, algunos repitiendo estilo de música
        emptyLine();
        gestion.altaConcierto("Fiestas de León", LocalDate.of(2024,6,23),
                201, Estilo.POP, new ArrayList<>());
        gestion.altaConcierto("Fiestas de Valladolid", LocalDate.of(2024,9,2),
                203, Estilo.METAL, new ArrayList<>());
        gestion.altaConcierto("Sonorama Ribera", LocalDate.of(2024,8,9),
                202, Estilo.POP, new ArrayList<>());
        gestion.altaConcierto("Fiestas de Ponferrada", LocalDate.of(2024,9,8),
                105, Estilo.ROCK, new ArrayList<>());


        // 5. Asigna asistentes a los conciertos
        emptyLine();
        gestion.asignarAsistenteAConcierto(301, "Fiestas de León");
        gestion.asignarAsistenteAConcierto(301, "Fiestas de Valladolid");
        gestion.asignarAsistenteAConcierto(302, "Páramo Rock");


        // 6. Lista los conciertos de un estilo concreto (metal)
        emptyLine();
        gestion.listarConciertosPorEstilo(Estilo.METAL);


        // 7. Lista los conciertos de un artista concreto (El Último Ke Zierre)
        emptyLine();
        gestion.listarConciertosPorArtista(107);


        // 8. Muestra la cantidad de conciertos de un asistente (Asistente 5)
        emptyLine();
        gestion.cantidadConciertosPorAsistente(5);


        // 9. Guarda los datos en un fichero
        emptyLine();
        gestion.guardarDatosEnFicheros();


        // Extra: carga los datos de los ficheros
        emptyLine();
        gestion.cargarDatosDeFicheros();

    }

    private static void emptyLine() {
        System.out.println();
    }

}