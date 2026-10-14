package co.edu.uniquindio.poo;

import java.util.Comparator;

public final class Comparadores {
    private Comparadores() {}

    public static final Comparator<Paquete> POR_PRIORIDAD =
            (a, b) -> Integer.compare(b.getPrioridad(), a.getPrioridad());      // mayor a menor
    public static final Comparator<Paquete> POR_PESO =
            (a, b) -> Double.compare(b.getPeso(), a.getPeso());                 // mayor a menor
    public static final Comparator<Paquete> POR_TIEMPO =
            (a, b) -> Integer.compare(a.getTiempoEstimado(), b.getTiempoEstimado()); // menor a mayor

    // Despacho: prioridad, luego tiempo, luego código
    public static final Comparator<Paquete> DESPACHO = POR_PRIORIDAD.thenComparing(POR_TIEMPO).thenComparing(Comparator.naturalOrder());
}