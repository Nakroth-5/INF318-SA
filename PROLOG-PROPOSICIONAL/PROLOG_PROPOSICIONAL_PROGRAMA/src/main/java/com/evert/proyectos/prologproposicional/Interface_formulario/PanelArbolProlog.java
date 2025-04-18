package com.evert.proyectos.prologproposicional.Interface_formulario;

import com.evert.proyectos.prologproposicional.Nodo.NodoProlog;

import javax.swing.*;
import java.awt.*;

public class PanelArbolProlog extends JPanel {
    private NodoProlog raiz;
    public PanelArbolProlog(NodoProlog raiz) {
        this.raiz = raiz;
    }

    public void setArbol(NodoProlog raiz) {
        this.raiz = raiz;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (raiz != null) {
            dibujarArbol(g, raiz, getWidth() / 2, 40, (getWidth() + 100) / 4);
        }
    }

    private void dibujarArbol(Graphics g, NodoProlog nodo, int x, int y, int espacio) {
        int ancho = 60, alto = 30;
        boolean exito = nodo.isResuelto();
        g.setColor(exito ? Color.GREEN : Color.RED);
        g.fillRoundRect(x - ancho / 2, y, ancho, alto, 10, 10);
        g.setColor(Color.BLACK);
        g.drawString(nodo.getMeta() + (nodo.isResuelto() ? " ->✔️" : " ->❌"), x - ancho / 2 + 5, y + 20);

        int nroHijos = nodo.getHijos().size();
        if (nroHijos == 0) return;

        int nuevoEspacio = Math.max(espacio / 2, 70); // que no baje de 60 píxeles
        int xInicio = x - (nroHijos - 1) * nuevoEspacio / 2;

        for (int i = 0; i < nroHijos; i++) {
            NodoProlog hijo = nodo.getHijos().get(i);
            int xHijo = xInicio + i * nuevoEspacio;
            int yHijo = y + 80;

            g.setColor(Color.BLACK);
            g.drawLine(x, y + alto, xHijo, yHijo);

            dibujarArbol(g, hijo, xHijo, yHijo, nuevoEspacio);
        }
    }

}