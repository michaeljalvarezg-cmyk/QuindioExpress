package co.edu.uniquindio.poo;

public interface ContratoMiLista<T> extends Iterable<T> {

    void agregar(T elemento);
    boolean eliminar(T elemento);
    T obtener(int indice);
    int getTamano();
    boolean estaVacia();
}