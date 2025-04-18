package com.evert.proyectos.prologproposicional.Clases;

import com.evert.proyectos.prologproposicional.Nodo.NodoBC;

import java.util.List;

public class BaseDeConocimiento {
    protected NodoBC ptrClausulas;

    public BaseDeConocimiento() {
        ptrClausulas = null;
    }

    public boolean esUnEcho(NodoBC nodoAct) {
        return nodoAct.getCuerpo().isEmpty();
    }

    private NodoBC fin() {
        for (NodoBC x = ptrClausulas; x != null; x = x.getSiguiente())
            if (x.getSiguiente() == null)
                return x;
        return null;
    }

    public NodoBC inicio() {
        return ptrClausulas;
    }

    public void insertarFinal(String cabeza, List<String> cuerpo) {
        NodoBC nvaClausula = new NodoBC(cabeza, cuerpo);
        if (ptrClausulas == null)
            ptrClausulas = nvaClausula;
        else
            fin().setSiguiente(nvaClausula);
    }


}