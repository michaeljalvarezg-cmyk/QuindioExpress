package co.edu.uniquindio.poo;

import java.util.Objects;

public class Paquete implements Comparable<Paquete> {
    private final String codigo;
    private String destino;
    private double peso;
    private int prioridad; // 1 (baja) a 5 (alta), se cambia a int para permitir comparaciones más fáciles
    private int tiempoEstimado;

    public Paquete(String codigo, String destino, double peso, int prioridad, int tiempoEstimado) {
        if (codigo == null || codigo.trim().isEmpty())
            throw new IllegalArgumentException("El código no puede ser nulo ni vacío.");
        if (destino == null || destino.trim().isEmpty())
            throw new IllegalArgumentException("El destino no puede ser nulo ni vacío.");
        if (peso <= 0)
            throw new IllegalArgumentException("El peso debe ser mayor que cero.");
        if (prioridad < 1 || prioridad > 5)
            throw new IllegalArgumentException("La prioridad debe estar entre 1 y 5.");
        if (tiempoEstimado <= 0)
            throw new IllegalArgumentException("El tiempo estimado debe ser positivo.");

        this.codigo = codigo.trim();
        this.destino = destino.trim();
        this.peso = peso;
        this.prioridad = prioridad;
        this.tiempoEstimado = tiempoEstimado;
    }

    public String getCodigo() { return codigo; }
    public String getDestino() { return destino; }
    public double getPeso() { return peso; }
    public int getPrioridad() { return prioridad; }
    public int getTiempoEstimado() { return tiempoEstimado; }

    @Override
    public int compareTo(Paquete otro) {      // orden natural: código ascendente
        return this.codigo.compareTo(otro.codigo);
    }


    // Se verifica si el objeto es una instacia de paquete, si no lo es, se sabe que no son iguales
    // Si es un objeto paquete, se hace el casteo, y se verifica el codigo con .quals
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Paquete)) return false;
        return codigo.equals(((Paquete) o).codigo);
    }

    @Override
    public int hashCode() { return Objects.hash(codigo); }

    @Override
    public String toString() {
        return "Paquete{codigo='" + codigo + "', destino='" + destino + "', peso=" + peso
                + " kg, prioridad=" + prioridad + ", tiempoEstimado=" + tiempoEstimado + " min}";
    }
}