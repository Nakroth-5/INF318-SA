package com.evert.proyectos.prologproposicional.Nodo;

import java.util.ArrayList;
import java.util.List;

public class NodoProlog {
    private String meta;
    List<NodoProlog> hijos;

    boolean resuelto;

    public NodoProlog(String meta) {
        this.meta = meta;
        this.hijos = new ArrayList<>();
        this.resuelto = false;
    }

    public List<NodoProlog> getHijos() {
        return hijos;
    }

    public void setHijos(NodoProlog hijo) {
        this.hijos.add(hijo);
    }

    public String getMeta() {
        return meta;
    }

    public void setMeta(String meta) {
        this.meta = meta;
    }

    public boolean isResuelto() {
        return resuelto;
    }

    public void setResuelto(boolean resuelto) {
        this.resuelto = resuelto;
    }
}
