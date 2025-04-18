package com.evert.proyectos.prologproposicional.Clases;

import com.evert.proyectos.prologproposicional.Nodo.NodoBC;
import com.evert.proyectos.prologproposicional.Nodo.NodoProlog;

import java.util.*;

public class ResolverBC {
    BaseDeConocimiento ptrBC;
    NodoProlog raiz;
    public ResolverBC(BaseDeConocimiento bc) {
        ptrBC = bc;
        this.raiz = null;
    }

    public NodoProlog resolver(List<String> metas) {
        this.raiz = new NodoProlog(metas.toString());
        boolean todasMetasResueltas = true;
        Set<String> metasActivas = new HashSet<>();
        for (String subMetas : metas) {
            NodoProlog hijo = resolver(subMetas, metasActivas);
            raiz.setHijos(hijo);
            if (!hijo.isResuelto())
                todasMetasResueltas = false;
        }

        raiz.setResuelto(todasMetasResueltas);
        return raiz;
    }

    private NodoProlog resolver(String metas, Set<String> metasActivas) {
        if (metasActivas.contains(metas)) {
            NodoProlog nodoCiclo = new NodoProlog(metas);
            nodoCiclo.setResuelto(false);
            return nodoCiclo;
        }

        metasActivas.add(metas);
        NodoProlog nodo = new NodoProlog(metas);

        for (NodoBC nodoAct = ptrBC.inicio(); nodoAct != null; nodoAct = nodoAct.getSiguiente()) {
            if (nodoAct.getCabeza().equals(metas)) {
                List<String> cuerpo = nodoAct.getCuerpo();

                if (ptrBC.esUnEcho(nodoAct)) {
                    nodo.setResuelto(true);
                    metasActivas.remove(metas);
                    return nodo;
                }

                boolean cutActivado = false;
                boolean exito = true;

                for (String subMetas : cuerpo) {
                    if (subMetas.equals("!")) {
                        cutActivado = true;     continue;
                    }

                    if (subMetas.equals("fail")) {
                        exito = false;      break;
                    }

                    NodoProlog hijo = resolver(subMetas, metasActivas);
                    nodo.setHijos(hijo);
                    if (!hijo.isResuelto()) {
                        exito = false;      break;
                    }
                }

                nodo.setResuelto(exito);
                metasActivas.remove(metas);
                if (exito) return nodo;
                if (cutActivado) return nodo;
            }
        }

        nodo.setResuelto(false);
        metasActivas.remove(metas);
        return nodo;
    }

    public void mostrarDerivacion(NodoProlog nodo, String indentacion) {
        System.out.println(indentacion + nodo.getMeta() + " -> " + (nodo.isResuelto() ? "✔️" : "❌"));
        for (NodoProlog hijo : nodo.getHijos()) {
            mostrarDerivacion(hijo, indentacion + "  ");
        }
    }

}
