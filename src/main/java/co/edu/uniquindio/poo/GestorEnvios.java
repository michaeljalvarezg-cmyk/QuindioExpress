package co.edu.uniquindio.poo;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.TreeSet;

public class GestorEnvios {
    private Map<String, Repartidor> repartidores;               // LinkedHashMap: orden determinista
    private List<Paquete> paquetesRegistrados;                  // orden exacto de registro, nunca se elimina
    private Map<String, Paquete> paquetesRegistradoscodigo;     // codigo -> Paquete
    private PriorityQueue<Paquete> pendientesDespachoPrioridad; // por despachar, orden de despacho
    private Queue<Paquete> pendientesPorLlegada;                // por despachar, FIFO
    private ContratoMiLista<Paquete> historialDespachos;        // lista propia

    public GestorEnvios() {
       this.repartidores = new HashMap<>();   
        this.paquetesRegistrados = new ArrayList<>();
        this.paquetesRegistradoscodigo = new HashMap<>();
        this.pendientesDespachoPrioridad = new PriorityQueue<>(Comparadores.DESPACHO);
        this.pendientesPorLlegada = new ArrayDeque<>();
        this.historialDespachos = new ListaEnlazada<Paquete>();
    }

    // ---------------- Registro y consulta ----------------

    public void registrarPaquete(Paquete p) {
        if (p == null) {
            throw new IllegalArgumentException("El paquete no puede ser nulo.");
        }
        if (paquetesRegistradoscodigo.containsKey(p.getCodigo())) {   // O(1) esperado
            throw new IllegalArgumentException("Ya existe un paquete con el código " + p.getCodigo());
        }
        paquetesRegistrados.add(p);                       // O(1) amortizado
        paquetesRegistradoscodigo.put(p.getCodigo(), p);  // O(1) esperado
        pendientesDespachoPrioridad.offer(p);             // O(log n)
        pendientesPorLlegada.offer(p);                    // O(1)
    }

    public void registrarRepartidor(Repartidor r) {
        if (r == null) {
            throw new IllegalArgumentException("El repartidor no puede ser nulo.");
        }
        if (r.getIdentificacion() == null || r.getIdentificacion().trim().isEmpty()) {
            throw new IllegalArgumentException("La identificación no puede ser nula ni vacía.");
        }
        if (r.getZona() == null || r.getZona().trim().isEmpty()) {
            throw new IllegalArgumentException("La zona no puede ser nula ni vacía.");
        }
        if (repartidores.containsKey(r.getIdentificacion())) {
            throw new IllegalArgumentException("Ya existe un repartidor con la identificación " + r.getIdentificacion());
        }
        repartidores.put(r.getIdentificacion(), r);
    }

    public Paquete consultarPaquete(String codigo) {
        return paquetesRegistradoscodigo.get(codigo);
    }

    public boolean existePaquete(String codigo) {
        return paquetesRegistradoscodigo.containsKey(codigo);
    }

    public Repartidor consultarRepartidor(String identificacion) {
        return repartidores.get(identificacion);
    }

    // ---------------- Municipios ----------------

    // Sin repetidos. Se arma al consultar: O(n)
    public Set<String> getMunicipios() {
        Set<String> resultado = new HashSet<>();
        for (Paquete p : paquetesRegistrados) {
            resultado.add(p.getDestino());
        }
        return resultado;
    }

    // Sin repetidos y en orden alfabético. O(n log m)
    public Set<String> getMunicipiosOrdenados() {
        Set<String> resultado = new TreeSet<>();
        for (Paquete p : paquetesRegistrados) {
            resultado.add(p.getDestino());
        }
        return resultado;
    }

    public Map<String, List<Paquete>> getPaquetesPorMunicipio() {
        Map<String, List<Paquete>> resultado = new HashMap<>();
        for (Paquete p : paquetesRegistrados) {
            String destino = p.getDestino();
            if (!resultado.containsKey(destino)) {
                resultado.put(destino, new ArrayList<>());
            }
            resultado.get(destino).add(p);
        }
        return resultado;
    }

    public List<Paquete> getPaquetesDeMunicipio(String municipio) {
        List<Paquete> resultado = new ArrayList<>();
        if (municipio == null) {
            return resultado;
        }
        String buscado = municipio.trim();
        for (Paquete p : paquetesRegistrados) {
            if (p.getDestino().equalsIgnoreCase(buscado)) {
                resultado.add(p);
            }
        }
        return resultado;
    }

    // ---------------- Despacho y entrega ----------------

    // disponible = true  -> busca uno libre (para asignar)
    // disponible = false -> busca uno ocupado (para liberar al entregar)
    private Repartidor buscarRepartidorEnZona(String zona, boolean disponible) {
        for (Repartidor r : repartidores.values()) {
            if (r.isDisponible() == disponible && r.getZona().trim().equalsIgnoreCase(zona)) {
                return r;
            }
        }
        return null;
    }

    private boolean ocuparRepartidor(Paquete paquete) {
        Repartidor r = buscarRepartidorEnZona(paquete.getDestino(), true);
        if (r == null) {
            return false;
        }
        r.setDisponible(false);
        return true;
    }

    public Paquete despacharPorPrioridad() {
        Paquete paquete = pendientesDespachoPrioridad.peek();     // O(1)
        if (paquete == null || !ocuparRepartidor(paquete)) {
            return null;
        }
        pendientesDespachoPrioridad.poll();                       // O(log n)
        pendientesPorLlegada.remove(paquete);                     // O(n)
        return paquete;
    }

    public Paquete despacharPorOrdenLlegada() {
        Paquete paquete = pendientesPorLlegada.peek();            // O(1)
        if (paquete == null || !ocuparRepartidor(paquete)) {
            return null;
        }
        pendientesPorLlegada.poll();                              // O(1)
        pendientesDespachoPrioridad.remove(paquete);              // O(n)
        return paquete;
    }

    public int getCantidadPorDespachar() {
        return pendientesDespachoPrioridad.size();
    }

    public void registrarEntrega(String codigo) {
        Paquete p = paquetesRegistradoscodigo.get(codigo);
        if (p == null) {
            throw new IllegalArgumentException("No existe el paquete " + codigo);
        }
        if (pendientesDespachoPrioridad.contains(p)) {
            throw new IllegalStateException("El paquete " + codigo + " aún no ha sido despachado.");
        }
        if (estaEntregado(codigo)) {
            throw new IllegalStateException("El paquete " + codigo + " ya fue entregado.");
        }
        Repartidor r = buscarRepartidorEnZona(p.getDestino(), false);
        if (r == null) {
            throw new IllegalStateException("No hay repartidores ocupados en la zona " + p.getDestino());
        }
        historialDespachos.agregar(p);
        r.setDisponible(true);
    }

    private boolean estaEntregado(String codigo) {
        for (Paquete p : historialDespachos) {
            if (p.getCodigo().equals(codigo)) {
                return true;
            }
        }
        return false;
    }

    public ContratoMiLista<Paquete> getHistorial() {
        return historialDespachos;
    }

    // "Pendientes" = no entregados (incluye los ya despachados)
    public List<Paquete> getPendientes() {
        Set<String> entregados = new HashSet<>();
        for (Paquete p : historialDespachos) {
            entregados.add(p.getCodigo());
        }
        List<Paquete> resultado = new ArrayList<>();
        for (Paquete p : paquetesRegistrados) {
            if (!entregados.contains(p.getCodigo())) {
                resultado.add(p);
            }
        }
        return resultado;
    }

    // ---------------- Ordenamientos y acceso ----------------

    public List<Paquete> getOrdenadoPor(Comparator<Paquete> criterio) {
        List<Paquete> copia = new ArrayList<>(paquetesRegistrados);
        copia.sort(criterio);
        return copia;
    }

    public List<Paquete> getOrdenadoPorCodigo() {
        List<Paquete> copia = new ArrayList<>(paquetesRegistrados);
        copia.sort(Comparator.naturalOrder());
        return copia;
    }

    public List<Paquete> getPaquetesRegistrados() {
        return new ArrayList<>(paquetesRegistrados);
    }

    public int getTotalPaquetes() {
        return paquetesRegistrados.size();
    }

    public List<Repartidor> getRepartidores() {
        return new ArrayList<>(repartidores.values());
    }

    // ================= ALGORITMOS =================

    // RA1 - Recursivo: peso total de un municipio. Pila O(n), tiempo O(n)
    public static double pesoTotalMunicipio(List<Paquete> paquetes, String municipio) {
        if (paquetes == null || municipio == null) {
            throw new IllegalArgumentException("Los parámetros no pueden ser nulos.");
        }
        return pesoTotalMunicipio(paquetes, municipio.trim(), 0);
    }

    private static double pesoTotalMunicipio(List<Paquete> paquetes, String municipio, int indice) {
        if (indice == paquetes.size()) {                 // caso base
            return 0;
        }
        Paquete p = paquetes.get(indice);
        double aporte = p.getDestino().equalsIgnoreCase(municipio) ? p.getPeso() : 0;
        return aporte + pesoTotalMunicipio(paquetes, municipio, indice + 1);   // caso recursivo
    }

    // RA2 - Recursivo: cuenta paquetes con prioridad >= minima
    public static int contarPrioridadMinima(List<Paquete> paquetes, int minima) {
        if (paquetes == null) {
            throw new IllegalArgumentException("La lista no puede ser nula.");
        }
        return contarPrioridadMinima(paquetes, minima, 0);
    }

    private static int contarPrioridadMinima(List<Paquete> paquetes, int minima, int indice) {
        if (indice == paquetes.size()) {                 // caso base
            return 0;
        }
        int actual = paquetes.get(indice).getPrioridad() >= minima ? 1 : 0;
        return actual + contarPrioridadMinima(paquetes, minima, indice + 1);   // caso recursivo
    }


    // RA5 - Divide y vencerás: paquete de mayor peso. Tiempo O(n), pila O(log n)
    public static Paquete mayorPeso(List<Paquete> paquetes) {
        if (paquetes == null || paquetes.isEmpty()) {
            throw new IllegalArgumentException("La lista no puede ser nula ni vacía.");
        }
        return mayorPeso(paquetes, 0, paquetes.size() - 1);
    }

    private static Paquete mayorPeso(List<Paquete> paquetes, int inicio, int fin) {
        if (inicio == fin) {                                          // caso base
            return paquetes.get(inicio);
        }
        int medio = inicio + (fin - inicio) / 2;                      // división
        Paquete izquierdo = mayorPeso(paquetes, inicio, medio);       // subproblema izquierdo
        Paquete derecho = mayorPeso(paquetes, medio + 1, fin);        // subproblema derecho
        return izquierdo.getPeso() >= derecho.getPeso() ? izquierdo : derecho;   // combinación
    }
}