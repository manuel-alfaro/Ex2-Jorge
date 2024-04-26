package src.com.jorge.ex2.model;

import java.time.LocalDate;

public class Asistente extends Participante {

    private String dni;

    private LocalDate fechaNacimiento;

    public Asistente(int codigo, String nombre, String dni, LocalDate fechaNacimiento) {
        super(codigo, nombre);
        this.dni = dni;
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

}