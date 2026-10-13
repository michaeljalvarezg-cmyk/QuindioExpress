package co.edu.uniquindio.poo;

public class Repartidor {
    private String identificacion;
    private String nombre;
    private String zona;
    private boolean disponible;

    // Único constructor normal
    public Repartidor(String identificacion, String nombre, String zona, boolean disponible) {
        this.identificacion = identificacion;
        this.nombre = nombre;
        this.zona = zona;
        this.disponible = disponible;
    }

    // Getters y Setters
    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getZona() {
        return zona;
    }

    public void setZona(String zona) {
        this.zona = zona;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    @Override
    public String toString() {
        return "Repartidor{" +
                "identificacion='" + identificacion + '\'' +
                ", nombre='" + nombre + '\'' +
                ", zona='" + zona + '\'' +
                ", disponible=" + disponible +
                '}';
    }
}