package co.edu.uniquindio.poo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class GestorEnvios {
    private Map<String, Repartidor> repartidores;
    private List<Paquete> paquetesRegistrados;
    private Map<String, Paquete> paquetesRegistradoscodigo;
    private PriorityQueue<Paquete> pendientesDespacho;
    private ContratoMiLista<Paquete> historialDespachos;

    public GestorEnvios(){
        this.repartidores = new HashMap<>();
        this.paquetesRegistrados = new ArrayList<>();
        this.paquetesRegistradoscodigo = new HashMap<>();
        this.pendientesDespacho = new PriorityQueue<>();
        this.historialDespachos = new ListaEnlazada<Paquete>();
    }

}
