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
        // TODO Auto-generated method stub
        
    }


    @Override
    public boolean eliminar(T elemento) {
        // TODO Auto-generated method stub
        return false;
    }


    @Override
    public boolean estaVacia() {
        // TODO Auto-generated method stub
        return false;
    }


    @Override
    public int getTamano() {
        // TODO Auto-generated method stub
        return 0;
    }


    @Override
    public T obtener(int indice) {
        // TODO Auto-generated method stub
        return null;
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

