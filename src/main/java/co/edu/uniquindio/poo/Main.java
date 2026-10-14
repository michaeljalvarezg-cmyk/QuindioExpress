package co.edu.uniquindio.poo;

import java.util.Iterator;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        GestorEnvios gestor = new GestorEnvios();


        gestor.registrarRepartidor(new Repartidor("R-001", "Carlos Gómez", "Armenia", true));
        gestor.registrarRepartidor(new Repartidor("R-002", "Laura Ríos", "Armenia", true));
        gestor.registrarRepartidor(new Repartidor("R-003", "Andrés Mejía", "Salento", true));
        gestor.registrarRepartidor(new Repartidor("R-004", "Marta Osorio", "Calarcá", true));
        gestor.registrarRepartidor(new Repartidor("R-005", "Julián Parra", "Montenegro", true));
        gestor.registrarRepartidor(new Repartidor("R-006", "Sofía Cano", "Circasia", true));
        gestor.registrarRepartidor(new Repartidor("R-007", "Diego Ruiz", "Quimbaya", false)); // no disponible

    
        gestor.registrarPaquete(new Paquete("PQ731", "Armenia", 6.4, 5, 45));
        gestor.registrarPaquete(new Paquete("PQ105", "Salento", 3.8, 5, 25));
        gestor.registrarPaquete(new Paquete("PQ942", "Armenia", 12.5, 4, 20));
        gestor.registrarPaquete(new Paquete("PQ318", "Calarcá", 8.2, 3, 35));
        gestor.registrarPaquete(new Paquete("PQ567", "Salento", 2.1, 2, 50));
        gestor.registrarPaquete(new Paquete("PQ829", "Armenia", 5.6, 1, 15));
        gestor.registrarPaquete(new Paquete("PQ214", "Montenegro", 9.3, 4, 30));
        gestor.registrarPaquete(new Paquete("PQ421", "Circasia", 4.7, 3, 40));
        gestor.registrarPaquete(new Paquete("PQ842", "Quimbaya", 7.9, 5, 55));
        gestor.registrarPaquete(new Paquete("PQ150", "Armenia", 3.3, 2, 22));
        gestor.registrarPaquete(new Paquete("PQ476", "Calarcá", 10.1, 4, 28));
        gestor.registrarPaquete(new Paquete("PQ603", "Montenegro", 1.9, 1, 60));
        gestor.registrarPaquete(new Paquete("PQ377", "Circasia", 6.0, 5, 18));
        gestor.registrarPaquete(new Paquete("PQ918", "Quimbaya", 11.4, 3, 33));
        gestor.registrarPaquete(new Paquete("PQ259", "Armenia", 14.2, 4, 38));
        gestor.registrarPaquete(new Paquete("PQ684", "Salento", 5.5, 3, 42));
        gestor.registrarPaquete(new Paquete("PQ035", "Calarcá", 2.7, 2, 27));
        gestor.registrarPaquete(new Paquete("PQ791", "Montenegro", 18.7, 5, 48)); // mayor peso
        gestor.registrarPaquete(new Paquete("PQ512", "Armenia", 7.0, 3, 20));
        gestor.registrarPaquete(new Paquete("PQ163", "Circasia", 3.5, 1, 52));
        gestor.registrarPaquete(new Paquete("PQ896", "Quimbaya", 4.2, 2, 36));
        gestor.registrarPaquete(new Paquete("PQ340", "Salento", 9.8, 4, 31));
        gestor.registrarPaquete(new Paquete("PQ725", "Armenia", 1.5, 5, 12));
        gestor.registrarPaquete(new Paquete("PQ098", "Calarcá", 13.6, 1, 44));
        gestor.registrarPaquete(new Paquete("PQ451", "Montenegro", 8.8, 2, 26));
        gestor.registrarPaquete(new Paquete("PQ977", "Circasia", 15.3, 4, 29));
        gestor.registrarPaquete(new Paquete("PQ286", "Armenia", 4.9, 2, 34));
        gestor.registrarPaquete(new Paquete("PQ613", "Quimbaya", 6.8, 5, 18));
        gestor.registrarPaquete(new Paquete("PQ834", "Salento", 11.9, 3, 39));
        gestor.registrarPaquete(new Paquete("PQ172", "Calarcá", 5.2, 4, 23));

        System.out.println("=== 1. ORDEN DE REGISTRO ===");
        System.out.println("Total de paquetes: " + gestor.getTotalPaquetes());
        System.out.println("Total de repartidores: " + gestor.getRepartidores().size());
        System.out.println(codigos(gestor.getPaquetesRegistrados()));
        for (Repartidor r : gestor.getRepartidores()) {
            System.out.println(r);
        }

        // ---------- 2. Validaciones ----------
        System.out.println();
        System.out.println("=== 2. VALIDACIONES ===");

        try {
            gestor.registrarPaquete(new Paquete("PQ731", "Armenia", 1.0, 3, 10));
        } catch (IllegalArgumentException e) {
            System.out.println("Código repetido: " + e.getMessage());
        }

        try {
            gestor.registrarRepartidor(new Repartidor("R-001", "Otro", "Armenia", true));
        } catch (IllegalArgumentException e) {
            System.out.println("Repartidor repetido: " + e.getMessage());
        }

        try {
            new Paquete("PQ001", "Armenia", 0, 3, 10);
        } catch (IllegalArgumentException e) {
            System.out.println("Peso inválido: " + e.getMessage());
        }

        try {
            new Paquete("PQ002", "Armenia", 2.0, 6, 10);
        } catch (IllegalArgumentException e) {
            System.out.println("Prioridad inválida: " + e.getMessage());
        }

        try {
            new Paquete("PQ003", "Armenia", 2.0, 3, 0);
        } catch (IllegalArgumentException e) {
            System.out.println("Tiempo inválido: " + e.getMessage());
        }

        try {
            new Paquete("PQ004", "", 2.0, 3, 10);
        } catch (IllegalArgumentException e) {
            System.out.println("Destino vacío: " + e.getMessage());
        }

        System.out.println();
        System.out.println("=== 3. CONSULTA POR CÓDIGO ===");
        System.out.println("¿Existe PQ731? " + gestor.existePaquete("PQ731"));
        System.out.println(gestor.consultarPaquete("PQ731"));
        System.out.println("¿Existe PQ000? " + gestor.existePaquete("PQ000"));

        
        System.out.println();
        System.out.println("=== 4. MUNICIPIOS ===");
        System.out.println("Sin repetir: " + gestor.getMunicipios());
        System.out.println("Ordenados: " + gestor.getMunicipiosOrdenados());
        for (String municipio : gestor.getMunicipiosOrdenados()) {
            System.out.println(municipio + " -> " + codigos(gestor.getPaquetesDeMunicipio(municipio)));
        }

        
        System.out.println();
        System.out.println("=== 5. ORDENAMIENTOS ===");
        System.out.println("Por código:    " + codigos(gestor.getOrdenadoPorCodigo()));
        System.out.println("Por prioridad: " + codigos(gestor.getOrdenadoPor(Comparadores.POR_PRIORIDAD)));
        System.out.println("Por peso:      " + codigos(gestor.getOrdenadoPor(Comparadores.POR_PESO)));
        System.out.println("Por tiempo:    " + codigos(gestor.getOrdenadoPor(Comparadores.POR_TIEMPO)));



     
        System.out.println();
        System.out.println("=== 7. RECURSIVIDAD ===");
        List<Paquete> todos = gestor.getPaquetesRegistrados();
        System.out.println("Peso total Armenia: " + GestorEnvios.pesoTotalMunicipio(todos, "Armenia") + " kg");
        System.out.println("Peso total Salento: " + GestorEnvios.pesoTotalMunicipio(todos, "Salento") + " kg");
        System.out.println("Paquetes con prioridad >= 4: " + GestorEnvios.contarPrioridadMinima(todos, 4));
        System.out.println("Paquetes con prioridad >= 5: " + GestorEnvios.contarPrioridadMinima(todos, 5));

      
        System.out.println();
        System.out.println("=== 8. DIVIDE Y VENCERÁS ===");
        System.out.println("Paquete de mayor peso: " + GestorEnvios.mayorPeso(todos));

        System.out.println();
        System.out.println("=== 9. ATENCIÓN POR ORDEN DE LLEGADA ===");
        for (int i = 0; i < 30; i++) {
            Paquete p = gestor.despacharPorOrdenLlegada();
            if (p == null) {
                System.out.println("  null -> el repartidor de la zona está ocupado, la cola queda bloqueada");
            } else {
                System.out.println("  atendido " + p.getCodigo() + " (" + p.getDestino() + ")");
            }
        }
        System.out.println("Por despachar: " + gestor.getCantidadPorDespachar());

     
        System.out.println();
        System.out.println("=== 10. REGISTRO DE ENTREGAS ===");
        try {
            gestor.registrarEntrega("PQ567");   // todavía no fue despachado
        } catch (IllegalStateException e) {
            System.out.println("Entregar PQ567: " + e.getMessage());
        }
        gestor.registrarEntrega("PQ731");
        gestor.registrarEntrega("PQ105");
        gestor.registrarEntrega("PQ942");
        gestor.registrarEntrega("PQ318");
        System.out.println("Entregados: PQ731, PQ105, PQ942, PQ318");
        try {
            gestor.registrarEntrega("PQ731");   // ya entregado
        } catch (IllegalStateException e) {
            System.out.println("Entregar PQ731 otra vez: " + e.getMessage());
        }
        try {
            gestor.registrarEntrega("PQ000");   // no existe
        } catch (IllegalArgumentException e) {
            System.out.println("Entregar PQ000: " + e.getMessage());
        }

       
        System.out.println();
        System.out.println("=== 11. DESPACHO PRIORITARIO  ===");
        for (int i = 0; i < 3; i++) {
            Paquete p = gestor.despacharPorPrioridad();
            if (p == null) {
                System.out.println("  null -> el repartidor de Quimbaya no está disponible");
            } else {
                System.out.println("  despachado " + p.getCodigo() + " (" + p.getDestino() + ")");
            }
        }

        System.out.println("Se habilita al repartidor R-007");
        gestor.consultarRepartidor("R-007").setDisponible(true);
        for (int i = 0; i < 2; i++) {
            Paquete p = gestor.despacharPorPrioridad();
            System.out.println("  despachado " + p.getCodigo() + " (" + p.getDestino() + ")");
        }

        gestor.registrarEntrega("PQ725");
        gestor.registrarEntrega("PQ377");
        gestor.registrarEntrega("PQ613");
        gestor.registrarEntrega("PQ791");
        System.out.println("Entregados: PQ725, PQ377, PQ613, PQ791");

        System.out.println();
        System.out.println("=== 12. HISTORIAL DE ENTREGAS ===");
        System.out.println("Por despachar: " + gestor.getCantidadPorDespachar());
        System.out.println("No entregados: " + gestor.getPendientes().size());
        System.out.println("Tamaño del historial: " + gestor.getHistorial().getTamano());

        System.out.println("Historial con Iterator:");
        Iterator<Paquete> n = gestor.getHistorial().iterator();
        while (n.hasNext()) {
            Paquete p = n.next();
            System.out.println("  " + p.getCodigo());
        }

        System.out.println("Historial con for-each:");
        for (Paquete p : gestor.getHistorial()) {
            System.out.println("  " + p.getCodigo());
        }

        System.out.println();
        System.out.println("=== 13. LISTA ENLAZADA PROPIA ===");
        ContratoMiLista<String> lista = new ListaEnlazada<>();

        System.out.println("¿Vacía? " + lista.estaVacia());
        System.out.println("Eliminar en lista vacía: " + lista.eliminar("X"));

        lista.agregar("A");
        System.out.println("Un solo elemento, tamaño: " + lista.getTamano());
        System.out.println("Eliminar A: " + lista.eliminar("A"));
        System.out.println("¿Vacía? " + lista.estaVacia());

        lista.agregar("A");
        lista.agregar("B");
        lista.agregar("C");
        lista.agregar("D");
        System.out.println("Eliminar el primero (A): " + lista.eliminar("A"));
        System.out.println("Eliminar uno intermedio (C): " + lista.eliminar("C"));
        System.out.println("Eliminar el último (D): " + lista.eliminar("D"));
        System.out.println("Eliminar uno que no existe (Z): " + lista.eliminar("Z"));

        lista.agregar("E");
        System.out.print("Contenido final:");
        for (String s : lista) {
            System.out.print(" " + s);
        }
        System.out.println();
        System.out.println("Tamaño final: " + lista.getTamano());

        try {
            lista.obtener(5);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("obtener(5): " + e.getMessage());
        }
    }

    private static String codigos(List<Paquete> lista) {
        String texto = "";
        for (Paquete p : lista) {
            texto = texto + p.getCodigo() + " ";
        }
        return texto.trim();
    }
}