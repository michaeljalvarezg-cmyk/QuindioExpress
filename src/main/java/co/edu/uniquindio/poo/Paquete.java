package co.edu.uniquindio.poo;

public class Paquete {
    private String codigo;
    private String destino;
    private double peso;
    private String prioridad; 
    private int tiempoEstimado;

    // Constructor
    public Paquete(String codigo, String destino, double peso, String prioridad, int tiempoEstimado) {
        if (peso <= 0) {
            throw new IllegalArgumentException("El peso debe ser mayor que cero.");
        }
        if (tiempoEstimado <= 0) {
            throw new IllegalArgumentException("El tiempo estimado debe ser un valor entero positivo.");
        }

        this.codigo = codigo;
        this.destino = destino;
        this.peso = peso;
        this.prioridad = prioridad;
        this.tiempoEstimado = tiempoEstimado;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        if (peso <= 0) {
            throw new IllegalArgumentException("El peso debe ser mayor que cero.");
        }
        this.peso = peso;
    }

    public String getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(String prioridad) {
        this.prioridad = prioridad;
    }

    public int getTiempoEstimado() {
        return tiempoEstimado;
    }

    public void setTiempoEstimado(int tiempoEstimado) {
        if (tiempoEstimado <= 0) {
            throw new IllegalArgumentException("El tiempo estimado debe ser un valor entero positivo.");
        }
        this.tiempoEstimado = tiempoEstimado;
    }

    @Override
    public String toString() {
        return "Paquete{" +
                "codigo='" + codigo + '\'' +
                ", destino='" + destino + '\'' +
                ", peso=" + peso + " kg" +
                ", prioridad='" + prioridad + '\'' +
                ", tiempoEstimado=" + tiempoEstimado + " min" +
                '}';
    }
}