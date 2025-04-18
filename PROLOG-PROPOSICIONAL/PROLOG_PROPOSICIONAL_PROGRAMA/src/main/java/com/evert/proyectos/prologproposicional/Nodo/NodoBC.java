package com.evert.proyectos.prologproposicional.Nodo;

import java.util.ArrayList;
import java.util.List;

public class NodoBC {
    private String cabeza;
    private List<String> cuerpo;
    private NodoBC siguiente;
    public NodoBC(String cabeza, List<String> cuerpo) {
        this.cabeza = cabeza;
        this.cuerpo = cuerpo;
        this.siguiente = null;
    }

    public String getCabeza() {
        return cabeza;
    }

    public void setCabeza(String cabeza) {
        this.cabeza = cabeza;
    }

    public List<String> getCuerpo() {
        return cuerpo;
    }

    public void setCuerpo(List<String> cuerpo) {
        this.cuerpo = cuerpo;
    }

    public NodoBC getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(NodoBC siguiente) {
        this.siguiente = siguiente;
    }
}
