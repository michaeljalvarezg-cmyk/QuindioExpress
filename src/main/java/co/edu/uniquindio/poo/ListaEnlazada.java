package co.edu.uniquindio.poo;
import java.util.Iterator;
import java.util.NoSuchElementException; 

public class ListaEnlazada<T> implements ContratoMiLista<T>  {
    private Nodo<T> cabeza;
    private int tamaño;

    public ListaEnlazada() {
        this.cabeza = null;
        this.tamaño = 0;
    }
    

    @Override
    public void agregar(T elemento) {
        Nodo<T> nuevoNodo = new Nodo<>(elemento);

        if (cabeza==null) {
            cabeza = nuevoNodo;
        } else {
            // Caso 2: Recorrer hasta encontrar el último nodo
            Nodo<T> actual = cabeza;
            while (actual.getSiguiente() != null) {
                actual = actual.getSiguiente();
            }
            // Conectar el último nodo con el nuevo
            actual.setSiguiente(nuevoNodo);
        }

        // Incrementar el contador de elementos
        tamaño++;
    }

    @Override
    public boolean eliminar(T elemento) {
        // Caso 1: Si la lista está vacía, no se puede eliminar nada
        if (estaVacia()) {
            return false;
        }

        // Caso 2: Si el elemento a eliminar está en el primer nodo (cabeza)
        if (cabeza.getValor().equals(elemento)) {
            cabeza = cabeza.getSiguiente();
            tamaño--;
            return true;
        }

        // Caso 3: Buscar en el resto de la lista (medio o final)
        Nodo<T> anterior = cabeza;
        Nodo<T> actual = cabeza.getSiguiente();

        while (actual != null) {
            if (actual.getValor().equals(elemento)) {
                // Hacemos el "puente" ignorando a actual
                anterior.setSiguiente(actual.getSiguiente());
                tamaño--;
                return true;
            }
            // Avanzar ambos punteros
            anterior = actual;
            actual = actual.getSiguiente();
        }

        // Si llegó al final y no lo encontró
        return false;
    }


    @Override
    public boolean estaVacia() {
        if (cabeza == null) {
            return true;
        } else {
            return false;
        }
    }


    @Override
    public int getTamano() {
        return tamaño;
    }


    @Override
    public T obtener(int indice) {
        // Validar que el índice exista en la lista
        if (indice < 0 || indice >= tamaño) {
            throw new IndexOutOfBoundsException("Índice fuera de rango: " + indice);
        }

        Nodo<T> actual = cabeza;
        
        // Avanzamos 'indice' cantidad de posiciones
        for (int i = 0; i < indice; i++) {
            actual = actual.getSiguiente();
        }

        return actual.getValor();
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private Nodo<T> actual = cabeza;

            @Override
            public boolean hasNext() {
                return actual != null;
            }

            @Override
            public T next() {
                if (hasNext()== false) {
                    throw new NoSuchElementException("No hay más elementos en la lista.");
                }
                T valor = actual.getValor();
                actual = actual.getSiguiente();
                return valor;
            }
        }; 
    }
}

