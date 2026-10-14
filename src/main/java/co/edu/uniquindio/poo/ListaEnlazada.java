package co.edu.uniquindio.poo;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ListaEnlazada<T> implements ContratoMiLista<T> {
    private Nodo<T> cabeza;
    private Nodo<T> cola;
    private int tamano;

    public ListaEnlazada() {
        this.cabeza = null;
        this.cola = null;
        this.tamano = 0;
    }

    // O(1): se enlaza directamente después de la cola
    @Override
    public void agregar(T elemento) {
        Nodo<T> nuevo = new Nodo<>(elemento);
        if (cabeza == null) {
            cabeza = nuevo;
        } else {
            cola.setSiguiente(nuevo);
        }
        cola = nuevo;
        tamano++;
    }

    // O(n)
    @Override
    public boolean eliminar(T elemento) {
        if (estaVacia()) {
            return false;
        }
        // Primer elemento
        if (cabeza.getValor().equals(elemento)) {
            cabeza = cabeza.getSiguiente();
            if (cabeza == null) {
                cola = null;            // la lista quedó vacía
            }
            tamano--;
            return true;
        }
        // Intermedio o último
        Nodo<T> anterior = cabeza;
        Nodo<T> actual = cabeza.getSiguiente();
        while (actual != null) {
            if (actual.getValor().equals(elemento)) {
                anterior.setSiguiente(actual.getSiguiente());
                if (actual == cola) {
                    cola = anterior;    // se eliminó el último
                }
                tamano--;
                return true;
            }
            anterior = actual;
            actual = actual.getSiguiente();
        }
        return false;                   // elemento inexistente
    }

    @Override
    public boolean estaVacia() {
        return tamano == 0;
    }

    @Override
    public int getTamano() {
        return tamano;
    }

    // O(n)
    @Override
    public T obtener(int indice) {
        if (indice < 0 || indice >= tamano) {
            throw new IndexOutOfBoundsException("Índice fuera de rango: " + indice);
        }
        Nodo<T> actual = cabeza;
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
                if (!hasNext()) {
                    throw new NoSuchElementException("No hay más elementos en la lista.");
                }
                T valor = actual.getValor();
                actual = actual.getSiguiente();
                return valor;
            }
        };
    }
}