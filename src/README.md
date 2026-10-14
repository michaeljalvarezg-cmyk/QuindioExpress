# QuindíoExpress – Gestión de operaciones de un centro de distribución

Parcial 1 – Estructuras de Datos (Ingeniería de Sistemas y Computación).

Aplicación en Java que organiza la información de una jornada de trabajo de QuindíoExpress: registro y consulta de paquetes y repartidores, despacho por orden de llegada o por prioridad, historial de entregas y análisis con recursividad, búsqueda binaria y divide y vencerás.

## Integrantes

- Juan Sebastian Mateus Hernandez
- Michael Joel Alvarez Gil


## Requisitos

- Java 21 (compila también con versiones recientes).
- No usa archivos ni bases de datos: los datos iniciales están definidos en `Main`.

## Estructura del proyecto

```
.
├── README.md
└── src/
    └── co/edu/uniquindio/poo/
        ├── Main.java              Demostración de todas las operaciones
        ├── GestorEnvios.java      Lógica del sistema + algoritmos estáticos
        ├── Paquete.java           Comparable<Paquete> (orden natural: código)
        ├── Repartidor.java
        ├── Comparadores.java      Comparator: prioridad, peso, tiempo, despacho
        ├── ContratoMiLista.java   Interfaz genérica (extends Iterable<T>)
        ├── ListaEnlazada.java     Lista simplemente enlazada propia (genérica)
        └── Nodo.java              Nodo genérico (valor + siguiente)
```

## Estructuras de datos usadas

| Necesidad | Estructura | Operación principal |
|---|---|---|
| Paquetes en orden exacto de registro | `ArrayList<Paquete>` | `add` O(1) amortizado |
| Consulta por código / código → Paquete | `HashMap<String, Paquete>` | `get`, `containsKey` O(1) esperado |
| Municipios sin duplicados | `HashSet<String>` | `add` O(1) esperado |
| Municipios ordenados y sin duplicados | `TreeSet<String>` | `add` O(log m) |
| Paquetes por municipio | `HashMap<String, List<Paquete>>` | `get` O(1) esperado |
| Atención por orden de llegada | `Queue<Paquete>` (`ArrayDeque`) | `offer`, `poll` O(1) |
| Despacho prioritario | `PriorityQueue<Paquete>` con `Comparadores.DESPACHO` | `offer`, `poll` O(log n) |
| Repartidores por identificación | `HashMap<String, Repartidor>` | `get`, `put` O(1) esperado |
| Historial de entregas | `ListaEnlazada<Paquete>` (propia) | `agregar` O(1) (usa `cola`) |

La lista propia **no** usa ninguna colección del Java Collections Framework. Implementa `ContratoMiLista<T>` (que extiende `Iterable<T>`) y expone su propio `Iterator<T>`, de modo que el código externo recorre el historial sin conocer los nodos.

## Orden y comparación

- **Orden natural** (`Comparable`): `Paquete.compareTo` ordena por código ascendente.
- **Criterios alternativos** (`Comparator`, en `Comparadores`):
  - `POR_PRIORIDAD`: mayor a menor.
  - `POR_PESO`: mayor a menor.
  - `POR_TIEMPO`: menor a mayor.
  - `DESPACHO`: prioridad (mayor primero), luego tiempo (menor primero), luego código.

## Algoritmos

| Algoritmo | Técnica | Tiempo | Espacio (pila) |
|---|---|---|---|
| `pesoTotalMunicipio` | Recursividad (sin ciclos) | O(n) | O(n) |
| `contarPrioridadMinima` | Recursividad (sin ciclos) | O(n) | O(n) |
| `busquedaBinaria` | Búsqueda binaria sobre lista ordenada por código | O(log n) | O(1) |
| `mayorPeso` | Divide y vencerás (caso base, división, subproblemas izquierdo/derecho, combinación) | O(n) | O(log n) |

Precondición de `busquedaBinaria`: la lista debe estar ordenada por código ascendente (por ejemplo, `getOrdenadoPorCodigo()`).

## Estados de un paquete

1. **Por despachar:** está en las colas (`pendientesPorLlegada` y `pendientesDespachoPrioridad`).
2. **Despachado:** un repartidor de la zona lo lleva; ya salió de las colas, pero aún no está en el historial.
3. **Entregado:** está en el historial (`ListaEnlazada`).

`getPendientes()` devuelve los paquetes **no entregados** (incluye los despachados).

## Reglas validadas

- Código de paquete e identificación de repartidor únicos.
- Peso > 0, prioridad entre 1 y 5, tiempo estimado > 0, destino no nulo ni vacío.
- Un repartidor no disponible no recibe asignaciones.
- La zona del repartidor debe coincidir con el destino del paquete.
- El despacho devuelve `null` (sin modificar nada) si no hay paquetes o no hay repartidor disponible en la zona.
- Solo se puede registrar la entrega de un paquete existente, ya despachado y no entregado antes; al entregar se libera al repartidor.

## Datos iniciales (`Main`)

- 30 paquetes con códigos no consecutivos, prioridades y pesos variados.
- 6 destinos: Armenia, Salento, Calarcá, Montenegro, Circasia y Quimbaya (varios paquetes por municipio).
- 7 repartidores (uno no disponible, para probar la regla de disponibilidad).

## Cómo compilar y ejecutar

Desde la raíz del repositorio:

**Linux / macOS**
```bash
mkdir -p out
javac -d out $(find src -name "*.java")
java -cp out co.edu.uniquindio.poo.Main
```

**Windows (PowerShell)**
```powershell
mkdir out -Force
javac -d out (Get-ChildItem -Recurse src -Filter *.java).FullName
java -cp out co.edu.uniquindio.poo.Main
```

La salida de `Main` está dividida en secciones numeradas: orden de registro, validaciones, consulta por código, municipios, ordenamientos, búsqueda binaria, recursividad, divide y vencerás, atención por orden de llegada, registro de entregas, despacho prioritario, historial y lista enlazada propia.

## Documentación

El documento PDF de entrega contiene el análisis y diseño (Parte A), el análisis algorítmico con pruebas de escritorio y pilas de llamadas (Parte C) y la bitácora de uso de inteligencia artificial (Parte D).